package com.vd.domain;

import java.util.List;

import com.vd.entity.Resource;

public class ResourceList {
	private List<Resource> resList ;
	private List<Long> clothIdList;
	public List<Resource> getResList() {
		return resList;
	}

	public void setResList(List<Resource> resList) {
		this.resList = resList;
	}

	public List<Long> getClothIdList() {
		return clothIdList;
	}

	public void setClothIdList(List<Long> clothIdList) {
		this.clothIdList = clothIdList;
	}
	
}
