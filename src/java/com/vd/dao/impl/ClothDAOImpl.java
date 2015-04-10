package com.vd.dao.impl;

import org.springframework.stereotype.Repository;

import com.vd.dao.BaseDAO;
import com.vd.dao.ClothDAO;
import com.vd.entity.Cloth;

@Repository
public class ClothDAOImpl extends BaseDAOImpl<Cloth> implements ClothDAO{

	@Override
	protected Class<Cloth> getEntityClass() {
		// TODO Auto-generated method stub
		return Cloth.class;
	}
	
}
