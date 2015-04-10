package com.vd.service;

import java.util.List;
import java.util.Map;

import com.vd.dao.ResourceDAO;
import com.vd.entity.Cloth;
import com.vd.entity.Resource;
import com.vd.util.Page;

public interface ResourceService extends BaseService<ResourceDAO>{
	public Long save(Resource resource);
	public Resource getResById(Long id);
	public Resource getRes(Map<String, Object> params);
	public List<Resource> getResList(Map<String, Object> params);
	public void delete(Resource resource);
	public void deleteFromCloth(Resource resource);
	public void update(Resource resource);
	public Page getByPrams(Map<String, Object> prams, Map<String, Object> sortPram, Map<String, Object> searchPram, Integer pageSize, Integer pageNo);
	public List<Resource> getUpdateList(Long time);
}
