package com.vd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vd_key")
public class Key {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name="vd_licence")
	private String licence;
	@Column(name="vd_pc_info")
	private String pcInfo;
	@Column(name="vd_generate_time")
	private String generateTime;
	@Column(name="vd_client_info")
	private String clientInfo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPcInfo() {
		return pcInfo;
	}
	public void setPcInfo(String pcInfo) {
		this.pcInfo = pcInfo;
	}
	public String getGenerateTime() {
		return generateTime;
	}
	public void setGenerateTime(String generateTime) {
		this.generateTime = generateTime;
	}
	public String getClientInfo() {
		return clientInfo;
	}
	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}
	public String getLicence() {
		return licence;
	}
	public void setLicence(String licence) {
		this.licence = licence;
	}
	
}
