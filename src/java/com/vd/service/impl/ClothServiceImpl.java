package com.vd.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Isolation;

import com.vd.dao.BaseDAO;
import com.vd.dao.ClothDAO;
import com.vd.entity.Cloth;
import com.vd.entity.Resource;
import com.vd.service.ClothService;
import com.vd.service.ResourceService;
import com.vd.util.CommonUtil;
import com.vd.util.FilePathGenerator;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ClothServiceImpl extends BaseServiceImpl<Cloth> implements ClothService{
	private ClothDAO clothDAO;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private FilePathGenerator filePathGenerator;
	public Cloth getClothById(Long id) {
		// TODO Auto-generated method stub
		return clothDAO.get(id);
	}

	public Cloth getCloth(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return clothDAO.getObject(params);
	}

	public List<Cloth> getCloths(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return clothDAO.getByPrams(params, null, null);
	}

	public void delete(Cloth cloth) {
		// TODO Auto-generated method stub
		String clothBasePath = filePathGenerator.getBasePath()+"upload"+File.separator+cloth.getId();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("refId", cloth.getId());
			List<Resource> resList = resourceService.getResList(params);
			for(Resource resource:resList) {
				resourceService.deleteFromCloth(resource);
			}
			clothDAO.del(cloth);
			CommonUtil.delFile(clothBasePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Cloth> getAllCloths() {
		// TODO Auto-generated method stub
		List<Cloth> retList = clothDAO.getObjectList(new HashMap<String, Object>());
		return retList;
	}

	@Override
	@Autowired
	public void setBaseServiceDAO(ClothDAO dao) {
		// TODO Auto-generated method stub
		super.setBaseDAO(dao);
		this.clothDAO = dao;
	}

	@Override
	public List<Long> getAllIdList() {
		// TODO Auto-generated method stub
		List list = clothDAO.getByHQL("select cloth.id from Cloth cloth", new HashMap<String, Object>());
		List<Long> retList = new ArrayList<Long>();
		for(int i=0;i<list.size();++i) {
			Object obj = list.get(i);
			retList.add((Long)obj);
		}
		return retList;
	}

}
