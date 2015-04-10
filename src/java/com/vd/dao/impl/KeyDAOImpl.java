package com.vd.dao.impl;

import org.springframework.stereotype.Repository;

import com.vd.dao.KeyDAO;
import com.vd.entity.Key;

@Repository
public class KeyDAOImpl extends BaseDAOImpl<Key> implements KeyDAO{

	@Override
	protected Class<Key> getEntityClass() {
		// TODO Auto-generated method stub
		return Key.class;
	}
	
}
