package com.vd.dao.impl;

import org.springframework.stereotype.Repository;

import com.vd.dao.ResourceDAO;
import com.vd.entity.Resource;

@Repository
public class ResourceDAOImpl extends BaseDAOImpl<Resource> implements ResourceDAO{

	@Override
	protected Class<Resource> getEntityClass() {
		// TODO Auto-generated method stub
		return Resource.class;
	}
	
}
