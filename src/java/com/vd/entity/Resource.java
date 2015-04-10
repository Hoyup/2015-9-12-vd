package com.vd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resource")
public class Resource {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name="vd_save_path")
	private String savePath;
	@Column(name="vd_type")
	private String type;
	@Column(name="vd_ref_id")
	private Long refId;
	@Column(name="vd_file_name")
	private String fileName;
	@Column(name="vd_upload_time")
	private Long uploadTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Long uploadTime) {
		this.uploadTime = uploadTime;
	}
	
}
