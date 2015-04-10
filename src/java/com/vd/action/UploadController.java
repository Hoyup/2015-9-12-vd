package com.vd.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.vd.entity.Cloth;
import com.vd.entity.Resource;
import com.vd.service.ClothService;
import com.vd.service.ResourceService;
import com.vd.util.FilePathGenerator;
import com.vd.util.MyJson;

@Controller
@RequestMapping("admin/upload")
@SessionAttributes("percent")
public class UploadController {
	
	@Autowired
	private ClothService clothService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private FilePathGenerator filePathGenerator;
	@RequestMapping("uploadPicSubmit")
	public String uploadPicSubmit(ModelMap modelMap,
			@RequestParam("file")MultipartFile file,HttpSession session) throws IOException {
		Long clothId = Long.parseLong(session.getAttribute("uploadingClothId").toString());
		String relativePath = filePathGenerator.getFilePath(clothId,file.getOriginalFilename());
		String savaPath = filePathGenerator.getBasePath()+relativePath;
		Resource resource = new Resource();
		resource.setFileName(file.getOriginalFilename());
		resource.setRefId(clothId);
		resource.setType("pic");
		resource.setSavePath(relativePath);
		resourceService.save(resource);
		File toFile = new File(savaPath);
		if(!toFile.exists()) {
			toFile.mkdirs();
		}
		file.transferTo(toFile);
		Thumbnails.of(toFile).size(150, 150).toFile(toFile);
		//return 1 means the upload is successful
		modelMap.put("info", "1");
		return "jsonInfo";
	}
	@RequestMapping("uploadFileSubmit")
	public String uploadFileSubmit(ModelMap modelMap,
			@RequestParam("file1")MultipartFile file,HttpSession session) throws IOException {
		Long clothId = Long.parseLong(session.getAttribute("uploadingClothId").toString());
		String relativePath = filePathGenerator.getFilePath(clothId,file.getOriginalFilename());
		String savaPath = filePathGenerator.getBasePath()+relativePath;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refId", clothId);
		params.put("type", "file");
		Resource oldResource = resourceService.getRes(params);
		if(oldResource!=null)resourceService.delete(oldResource);
		SimpleDateFormat format = new SimpleDateFormat("yyyy/dd/MM HH:mm:ss");
		Date date = new Date();
		Resource resource = new Resource();
		resource.setRefId(clothId);
		resource.setType("file");
		resource.setFileName(file.getOriginalFilename());
		resource.setSavePath(relativePath);
		resource.setUploadTime(date.getTime());
		resourceService.save(resource);
		File toFile = new File(savaPath);
		if(!toFile.exists()) {
			toFile.mkdirs();
		}
		file.transferTo(toFile);
		Cloth cloth = clothService.getClothById(clothId);
		cloth.setLastUpdateTime(format.format(date));
		clothService.update(cloth);
		//return 1 means the upload is successful
		modelMap.put("info", "1");
		return "jsonInfo";
	}
	
	@RequestMapping(value = "uploadProgress", method = RequestMethod.GET )
	@ResponseBody
	public void initCreateInfo(Map<String, Object> model,HttpServletRequest request,HttpServletResponse response) {
//		int percent = (Integer)model.get("percent");
		HttpSession  session = request.getSession(true);
		Integer percent = (Integer)session.getAttribute("percent");
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.print(MyJson.toJson(percent));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
