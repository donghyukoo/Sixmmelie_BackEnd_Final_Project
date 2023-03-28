package com.sixmmelie.wine.winecellar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WINE_CATEGORY")
public class CategoryEntity {
	
	@Id
	@Column(name = "CATEGORY_CODE")
	private int categoryCode;
	
	@Column(name = "WINE_CATEGORY")
	private String wineCategory;

	public CategoryEntity() {
		super();
	}

	public CategoryEntity(int categoryCode, String wineCategory) {
		super();
		this.categoryCode = categoryCode;
		this.wineCategory = wineCategory;
	}

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getWineCategory() {
		return wineCategory;
	}

	public void setWineCategory(String wineCategory) {
		this.wineCategory = wineCategory;
	}

	@Override
	public String toString() {
		return "CategoryEntity [categoryCode=" + categoryCode + ", wineCategory=" + wineCategory + "]";
	}
	
	
}
