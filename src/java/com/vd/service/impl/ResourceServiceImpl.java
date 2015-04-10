package com.vd.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vd.dao.BaseDAO;
import com.vd.dao.ResourceDAO;
import com.vd.entity.Cloth;
import com.vd.entity.Resource;
import com.vd.service.ClothService;
import com.vd.service.ResourceService;
import com.vd.util.FilePathGenerator;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService{

	private ResourceDAO resourceDAO;
	@Autowired
	private ClothService clothService;
	@Autowired
	private FilePathGenerator filePathGenerator;
	@Override
	public Resource getResById(Long id) {
		// TODO Auto-generated method stub
		return resourceDAO.get(id);
	}

	@Override
	public Resource getRes(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return resourceDAO.getObject(params);
	}

	@Override
	public List<Resource> getResList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return resourceDAO.getByPrams(params, null, null);
	}

	@Override
	public void delete(Resource resource) {
		// TODO Auto-generated method stub
		Cloth cloth = clothService.getClothById(resource.getRefId());
		if(resource.getType().equals("pic")&&cloth.getFirstPicPath().equals(resource.getSavePath()))cloth.setFirstPicPath(null);
		resourceDAO.del(resource);
		File file = new File(filePathGenerator.getBasePath()+resource.getSavePath());
		if(file.exists()&&file.isFile()) {
			file.delete();
		}
		if(cloth.getFirstPicPath()==null||cloth.getFirstPicPath().length()<1) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("refId", cloth.getId());
			params.put("type", "pic");
			Resource newPic = resourceDAO.getObject(params);
			if(newPic!=null)cloth.setFirstPicPath(resource.getSavePath());
			clothService.update(cloth);
		}
	}
	
	@Override
	public Long save(Resource resource) {
		Cloth cloth = clothService.getClothById(resource.getRefId());
		if(resource.getType().equals("pic")&&(cloth.getFirstPicPath()==null||cloth.getFirstPicPath().length()<1)) {
			cloth.setFirstPicPath(resource.getSavePath());
			clothService.update(cloth);
		}
		return resourceDAO.save(resource);
	}

	@Override
	@Autowired
	public void setBaseServiceDAO(ResourceDAO dao) {
		// TODO Auto-generated method stub
		super.setBaseDAO(dao);
		this.resourceDAO = dao;
	}

	@Override
	public void deleteFromCloth(Resource resource) {
		// TODO Auto-generated method stub
		resourceDAO.del(resource.getId());
		File file = new File(filePathGenerator.getBasePath()+resource.getSavePath());
		if(file.exists()&&file.isFile()) {
			file.delete();
		}
	}

	@Override
	public List<Resource> getUpdateList(Long time) {
		// TODO Auto-generated method stub
		String hql = "from Resource where uploadTime>:time";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("time", time);
		return resourceDAO.getByHQL(hql, params);
	}
	
}
