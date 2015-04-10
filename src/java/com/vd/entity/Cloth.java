package com.vd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cloth")
public class Cloth {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name="vd_cloth_id")
	private String clothId;
	@Column(name="vd_color")
	private String color;
	@Column(name="vd_brand")
	private String brand;
	@Column(name="vd_size")
	private String size;
	@Column(name="vd_designer")
	private String designer;
	@Column(name="vd_season_year")
	private String seasonAndYear;
	@Column(name="vd_selected_count")
	private Long selectedCount;
	@Column(name="vd_location")
	private String location;
	@Column(name="vd_last_update_time")
	private String lastUpdateTime;
	@Column(name="vd_last_selected_time")
	private String lastSelectedTime;
	@Column(name="vd_first_pic_path")
	private String firstPicPath;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getDesigner() {
		return designer;
	}
	public void setDesigner(String designer) {
		this.designer = designer;
	}
	public String getSeasonAndYear() {
		return seasonAndYear;
	}
	public void setSeasonAndYear(String seasonAndYear) {
		this.seasonAndYear = seasonAndYear;
	}
	public Long getSelectedCount() {
		return selectedCount;
	}
	public void setSelectedCount(Long selectedCount) {
		this.selectedCount = selectedCount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getFirstPicPath() {
		return firstPicPath;
	}
	public void setFirstPicPath(String firstPicPath) {
		this.firstPicPath = firstPicPath;
	}
	public String getClothId() {
		return clothId;
	}
	public void setClothId(String clothId) {
		this.clothId = clothId;
	}
	public String getLastSelectedTime() {
		return lastSelectedTime;
	}
	public void setLastSelectedTime(String lastSelectedTime) {
		this.lastSelectedTime = lastSelectedTime;
	}
	
}
