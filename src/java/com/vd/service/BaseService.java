package com.vd.service;

import com.vd.dao.BaseDAO;
import com.vd.dao.impl.BaseDAOImpl;

public interface BaseService <T>{
	public void setBaseServiceDAO(T dao);
}
