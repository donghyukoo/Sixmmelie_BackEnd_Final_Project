package com.sixmmelie.wine.winecellar.dto;

public class CategoryDTO {
	private int catecoryCode;
	private String wineCategory;
	
	public CategoryDTO() {
		super();
	}
	
	public CategoryDTO(int catecoryCode, String wineCategory) {
		super();
		this.catecoryCode = catecoryCode;
		this.wineCategory = wineCategory;
	}

	public int getCatecoryCode() {
		return catecoryCode;
	}

	public void setCatecoryCode(int catecoryCode) {
		this.catecoryCode = catecoryCode;
	}

	public String getWineCategory() {
		return wineCategory;
	}

	public void setWineCategory(String wineCategory) {
		this.wineCategory = wineCategory;
	}

	@Override
	public String toString() {
		return "Category [catecoryCode=" + catecoryCode + ", wineCategory=" + wineCategory + "]";
	}
	
	
}
