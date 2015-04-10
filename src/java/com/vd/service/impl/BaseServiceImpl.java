package com.vd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.vd.dao.BaseDAO;
import com.vd.dao.impl.BaseDAOImpl;
import com.vd.util.Page;

public class BaseServiceImpl <T>{
	
	private BaseDAO<T> baseDAO;
	
	public BaseDAO<T> getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO<T> baseDAO) {
		this.baseDAO = baseDAO;
	}

	public Long save(T t) {
		return baseDAO.save(t);
	}

	public T get(Long id) {
		return baseDAO.get(id);
	}

	public void saveOrUpdate(T t) {
		baseDAO.saveOrUpdate(t);
	}

	public void update(T t) {
		baseDAO.update(t);
	}

	public void del(T t) {
		baseDAO.del(t);
	}

	public List<T> getAll() {
		return baseDAO.getObjectList(null);
	}

	/**
	 * searchPram最多2个
	 */
	public List<T> getByPrams(Map<String, Object> prams, Map<String, Object> sortPram,
			Map<String, Object> searchPram) {
		return baseDAO.getByPrams(prams, sortPram, searchPram);
	}

	public Page getByPrams(Map<String, Object> prams, Map<String, Object> sortPram, Map<String, Object> searchPram, Integer pageSize, Integer pageNo) {
		Integer iDisplayStart = 0;
		if (pageNo == null) {
			iDisplayStart = 0;
			pageSize = 15;
		} else {
			iDisplayStart = (pageNo - 1) * pageSize;
		}
		return baseDAO.getByPrams(prams, sortPram, searchPram, iDisplayStart, pageSize);
	}

	public List<T> getByHQL(String hql, Map<String, Object> prams) {
		return baseDAO.getByHQL(hql, prams);
	}
}
