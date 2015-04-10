package com.vd.service;

import java.util.List;
import java.util.Map;

import com.vd.dao.ClothDAO;
import com.vd.entity.Cloth;
import com.vd.util.Page;

public interface ClothService extends BaseService<ClothDAO>{
	public Long save(Cloth cloth);
	public Cloth getClothById(Long id);
	public Cloth getCloth(Map<String, Object>params);
	public List<Cloth> getCloths(Map<String, Object>params);
	public void delete(Cloth cloth);
	public void update(Cloth cloth);
	public List<Cloth> getAllCloths();
	public Page getByPrams(Map<String, Object> prams, Map<String, Object> sortPram, Map<String, Object> searchPram, Integer pageSize, Integer pageNo);
	public List<Long> getAllIdList();
}
