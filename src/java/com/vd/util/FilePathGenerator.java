package com.vd.util;

import java.io.File;
import java.util.Date;


public class FilePathGenerator {
	private String slash ;
	private String basePath ;
	private String baseUrl;
	
	public String getFilePath(Long id,String fileName) {
		String ret = "upload"+this.slash+id+this.slash; 
		String fileType = getTypeName(fileName);
		Date date = new Date();
		Long time = date.getTime();
		ret+=MD5.getMD5(time.toString().getBytes())+fileType;
		return ret;
	}
	public String getTypeName(String s){
		  String s1=s.substring(s.indexOf(".")+1,s.length());
		  if(s1.indexOf(".")>=0){
		   s=s1;
		   s=getTypeName(s);
		   }
		  return s.substring(s.indexOf("."),s.length());
	}
	public String getSlash() {
		return slash;
	}
	public void setSlash(String slash) {
		this.slash = slash;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
}
