package com.vd.service;

import java.util.List;
import java.util.Map;

import com.vd.dao.KeyDAO;
import com.vd.entity.Key;
import com.vd.util.Page;

public interface KeyService extends BaseService<KeyDAO>{
	public Long save(Key key);
	public void update(Key key);
	public void delete(Key key);
	public Key getKeyById(Long id);
	public Key getKey(Map<String, Object> params);
	public List<Key> getKeys(Map<String, Object> params);
	public Page getByPrams(Map<String, Object> prams, Map<String, Object> sortPram, Map<String, Object> searchPram, Integer pageSize, Integer pageNo);
}
