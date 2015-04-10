package com.vd.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vd.entity.Cloth;
import com.vd.entity.Key;
import com.vd.entity.Resource;
import com.vd.service.ClothService;
import com.vd.service.KeyService;
import com.vd.service.ResourceService;
import com.vd.util.FilePathGenerator;
import com.vd.util.LicenceKeyGenerator;
import com.vd.util.Page;

@Controller
@RequestMapping("admin")
public class ViewController {
	
	@Autowired
	private ClothService clothService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private KeyService keyService;
	@Autowired
	private FilePathGenerator filePathGenerator;
	@RequestMapping("manage")
	public String manage(ModelMap modelMap,Integer pageSize,Integer pageNo,String searchText) {
		if(pageSize==null)pageSize = 5;
		if(pageNo==null||pageNo<1)pageNo = 1;
		Page page = null;
		if(searchText==null||searchText.length()<1) {
			page = clothService.getByPrams(null, null, null, pageSize, pageNo);
		} else{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clothId", searchText);
			params.put("designer", searchText);
			page = clothService.getByPrams(null, null, params, pageSize, pageNo);
		}
		Integer pageCnt = (page.getTotalCount()+pageSize-1)/pageSize;
		if(pageCnt==0)pageCnt=1;
		modelMap.put("page", page);
		Integer pageStart = pageNo-3>0?pageNo-3:1;
		Integer pageEnd = pageNo+3<=pageCnt?pageNo+3:pageCnt;
		modelMap.put("pageStart", pageStart);
		modelMap.put("pageEnd", pageEnd);
		modelMap.put("pageNo", pageNo);
		modelMap.put("pageCnt", pageCnt);
		modelMap.put("searchText", searchText);
		modelMap.put("baseUrl", filePathGenerator.getBaseUrl());
		return "manage";
	}
	@RequestMapping("index")
	public String index(ModelMap modelMap) {
		return "redirect:manage.html";
	}
	@RequestMapping("addNewCloth")
	public String upload(ModelMap modelMap) {
		return "upload";
	}
	@RequestMapping("addNewClothSubmit")
	public String addNewClothSubmit(ModelMap modelMap,Cloth cloth,HttpSession httpSession) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/dd/MM HH:mm:ss");
		Date date = new Date();
		cloth.setLastUpdateTime(format.format(date));
		clothService.save(cloth);
		modelMap.put("info", cloth.getId());
		httpSession.setAttribute("uploadingClothId", cloth.getId());
		return "json";
	}
	@RequestMapping("editCloth")
	public String editCloth(ModelMap modelMap,Long clothId,HttpServletRequest request) {
		Cloth cloth = clothService.getClothById(clothId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refId", clothId);
		params.put("type", "pic");
		List<Resource> picList = resourceService.getResList(params);
		params.put("type", "file");
		Resource uploadFile = resourceService.getRes(params);
		modelMap.put("cloth", cloth);
		modelMap.put("picList", picList);
		modelMap.put("baseUrl", filePathGenerator.getBaseUrl());
		if(uploadFile!=null)modelMap.put("uploadFileName", uploadFile.getFileName());
		request.getSession().setAttribute("uploadingClothId", cloth.getId());
		return "detail";
	}
	@RequestMapping("editClothSubmit")
	public String editClothSubmit(ModelMap modelMap,Cloth cloth) {
		Cloth oldCloth = clothService.getClothById(cloth.getId());
		cloth.setFirstPicPath(oldCloth.getFirstPicPath());
		cloth.setLastSelectedTime(oldCloth.getLastSelectedTime());
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		cloth.setLastUpdateTime(format.format(new Date()));
		cloth.setLocation(oldCloth.getLocation());
		cloth.setSelectedCount(oldCloth.getSelectedCount());
		clothService.update(cloth);
		modelMap.put("info", "1");
		return "json";
	}
	@RequestMapping("deleteCloth")
	public String deleteCloth(ModelMap modelMap,Long clothId) {
		Cloth cloth = clothService.getClothById(clothId);
		clothService.delete(cloth);
		return "redirect:/admin/manage.html";
	}
	@RequestMapping("deletePic")
	public String deletePic(ModelMap modelMap,Long id) {
		Resource pic = resourceService.getResById(id);
		resourceService.delete(pic);
		modelMap.put("info", "1");
		return "json";
	}
	@RequestMapping("keymanage")
	public String keymanage(ModelMap modelMap,Integer pageSize,Integer pageNo,String searchText) {
		if(pageSize==null)pageSize = 5;
		if(pageNo==null||pageNo<1)pageNo = 1;
		Page page = null;
		if(searchText==null||searchText.length()<1) {
			page = keyService.getByPrams(null, null, null, pageSize, pageNo);
		} else{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientInfo", searchText);
			page = keyService.getByPrams(null, null, params, pageSize, pageNo);
		}
		Integer pageCnt = (page.getTotalCount()+pageSize-1)/pageSize;
		if(pageCnt==0)pageCnt=1;
		modelMap.put("page", page);
		Integer pageStart = pageNo-3>0?pageNo-3:1;
		Integer pageEnd = pageNo+3<=pageCnt?pageNo+3:pageCnt;
		modelMap.put("pageStart", pageStart);
		modelMap.put("pageEnd", pageEnd);
		modelMap.put("pageNo", pageNo);
		modelMap.put("pageCnt", pageCnt);
		modelMap.put("searchText", searchText);
		return "keymanage";
	}
	@RequestMapping("addKey")
	public String addKey(ModelMap modelMap) {
		return "addkey";
	}
	@RequestMapping("generateNewKey")
	public String generateNewKey(ModelMap modelMap) {
		String licence = LicenceKeyGenerator.getLinsenceKey();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("licence", licence);
		while(keyService.getKey(params)!=null) {
			licence = LicenceKeyGenerator.getLinsenceKey();
			params.put("licence", licence);
		}
		modelMap.put("info", licence);
		return "json";
	}
	@RequestMapping("addKeySubmit")
	public String addKeySubmit(ModelMap modelMap,Key key) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		key.setGenerateTime(format.format(date));
		keyService.save(key);
		return "redirect:/admin/keymanage.html";
	}
	@RequestMapping("editKey")
	public String editKey(ModelMap modelMap,Long keyId) {
		Key key = keyService.getKeyById(keyId);
		modelMap.put("key", key);
		return "editkey";
	}
	@RequestMapping("editKeySubmit")
	public String editKeySubmit(ModelMap modelMap,Key key) {
		Key oldKey = keyService.getKeyById(key.getId());
		key.setGenerateTime(oldKey.getGenerateTime());
		key.setLicence(oldKey.getLicence());
		keyService.update(key);
		return "redirect:/admin/keymanage.html";
	}
	@RequestMapping("deleteKey")
	public String deleteKey(ModelMap modelMap,Long keyId) {
		Key key = keyService.getKeyById(keyId);
		keyService.delete(key);
		return "redirect:/admin/keymanage.html";
	}
}
