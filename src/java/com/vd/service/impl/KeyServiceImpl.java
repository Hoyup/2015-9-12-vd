package com.vd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vd.dao.KeyDAO;
import com.vd.entity.Key;
import com.vd.service.KeyService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class KeyServiceImpl extends BaseServiceImpl<Key> implements KeyService{

	private KeyDAO keyDAO;
	
	@Override
	public void delete(Key key) {
		// TODO Auto-generated method stub
		keyDAO.del(key);
	}

	@Override
	public Key getKeyById(Long id) {
		// TODO Auto-generated method stub
		return keyDAO.get(id);
	}

	@Override
	public Key getKey(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return keyDAO.getObject(params);
	}

	@Override
	public List<Key> getKeys(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return keyDAO.getObjectList(params);
	}

	@Override
	@Autowired
	public void setBaseServiceDAO(KeyDAO dao) {
		// TODO Auto-generated method stub
		super.setBaseDAO(dao);
		this.keyDAO = dao;
	}

}
