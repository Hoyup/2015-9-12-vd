package com.vd.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vd.domain.ResourceList;
import com.vd.entity.Cloth;
import com.vd.entity.Key;
import com.vd.entity.Resource;
import com.vd.service.ClothService;
import com.vd.service.KeyService;
import com.vd.service.ResourceService;
import com.vd.util.JsonInfo;
import com.vd.util.MyJson;
import com.vd.util.RemoteMAC;

@Controller
@RequestMapping("api")
public class RemoteController {
	@Autowired
	private KeyService keyService;
	@Autowired
	private ClothService clothService;
	@Autowired
	private ResourceService resourceService;
	@RequestMapping("getUpdateList")
	public String getUpdateClothList(ModelMap modelMap,@RequestParam("licence")String licence,@RequestParam("pcInfo")String pcInfo,
			@RequestParam("timestamp")String timestamp,@RequestParam("lastUpdateTime")String lastUpdateTime,@RequestParam("mac")String mac) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		JsonInfo jsonInfo = new JsonInfo();
		Long requestTime = (format.parse(timestamp)).getTime();
		Long nowTime = (new Date()).getTime();
		if(Math.abs(nowTime-requestTime)>60000) { //judge if the timestamp is expired
			jsonInfo.setStatus("error");
			jsonInfo.setInfo("expired timestamp");
			modelMap.put("info", MyJson.toJson(jsonInfo));
			return "json";
		}
		String MAC = RemoteMAC.getMAC(licence, pcInfo, timestamp);
		if(!MAC.equals(mac)) {
			jsonInfo.setStatus("error");
			jsonInfo.setInfo("invalid mac");
		}
		else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("licence", licence);
			params.put("pcInfo", pcInfo);
			Key key = keyService.getKey(params);
			if(key==null) {
				jsonInfo.setStatus("error");
				jsonInfo.setInfo("invalid pair of licence and pcInfo");
			}
			else {
				Long time = format.parse(lastUpdateTime).getTime();
				List<Resource> updateList = resourceService.getUpdateList(time);   
				ResourceList resourceList = new ResourceList();  
				resourceList.setResList(updateList);
				resourceList.setClothIdList(clothService.getAllIdList());
				jsonInfo.setStatus("ok");  
				jsonInfo.setInfo(MyJson.toJson(resourceList));
			}
		}
		modelMap.put("info", MyJson.toJson(jsonInfo));
		return "json";
	}
	@RequestMapping("getClothList")
	public String getClothList(ModelMap modelMap) {
		List<Cloth> clothList = clothService.getAllCloths();
		JsonInfo jsonInfo = new JsonInfo();
		jsonInfo.setStatus("ok");
		jsonInfo.setInfo(MyJson.toJson(clothList));
		modelMap.put("info", MyJson.toJson(jsonInfo));
		return "json";
	}
	@RequestMapping("updateClothInfo")
	public String updateClothInfo(ModelMap modelMap,String location,Long clothId) {
		JsonInfo jsonInfo = new JsonInfo();
		Cloth cloth = clothService.getClothById(clothId);
		if(cloth==null) {
			jsonInfo.setStatus("error");
			jsonInfo.setInfo("cloth not  exists");
		} else {
			jsonInfo.setStatus("ok");
			if(cloth.getSelectedCount()==null)cloth.setSelectedCount(1L);
			else cloth.setSelectedCount(cloth.getSelectedCount()+1);
			cloth.setLocation(location);
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			cloth.setLastSelectedTime(format.format(date));
			clothService.update(cloth);
		}
		modelMap.put("info", MyJson.toJson(jsonInfo));
		return "json";
	}
}
