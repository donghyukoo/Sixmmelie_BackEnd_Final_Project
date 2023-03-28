package com.sixmmelie.wine.winecellar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WINE")
public class WineAndCategoryEntity {
	@Id
	@Column(name = "WINE_CODE")
	private int wineCode;
	
	@Column(name = "WINE_NAME_KO")
	private String wineNameKo;
	
	@Column(name = "WINE_NAME_EN")
	private String wineNameEn;
	
	@Column(name = "WINE_AREA")
	private String wineArea;
	
	@Column(name = "WINE_CONTENT")
	private String wineContent;
	
	@Column(name = "WINE_IMG")
	private String wineImg;
	
	@Column(name = "WINE_PRICE")
	private int winePrice;
	
	@Column(name = "WINE_STOCK")
	private int wineStock;
	
	@Column(name = "WINE_SALES")
	private int wineSales;
	
	@Column(name = "ALCOHOL_LEVEL")
	private double alcoholLevel;
	
	@ManyToOne  
	@JoinColumn(name = "CATEGORY_CODE")
	private CategoryEntity category;
	
	@Column(name = "NATION_CODE")
	private int nationCode;

	public WineAndCategoryEntity() {
		super();
	}

	public WineAndCategoryEntity(int wineCode, String wineNameKo, String wineNameEn, String wineArea,
			String wineContent, String wineImg, int winePrice, int wineStock, int wineSales, double alcoholLevel,
			CategoryEntity category, int nationCode) {
		super();
		this.wineCode = wineCode;
		this.wineNameKo = wineNameKo;
		this.wineNameEn = wineNameEn;
		this.wineArea = wineArea;
		this.wineContent = wineContent;
		this.wineImg = wineImg;
		this.winePrice = winePrice;
		this.wineStock = wineStock;
		this.wineSales = wineSales;
		this.alcoholLevel = alcoholLevel;
		this.category = category;
		this.nationCode = nationCode;
	}

	public int getWineCode() {
		return wineCode;
	}

	public void setWineCode(int wineCode) {
		this.wineCode = wineCode;
	}

	public String getWineNameKo() {
		return wineNameKo;
	}

	public void setWineNameKo(String wineNameKo) {
		this.wineNameKo = wineNameKo;
	}

	public String getWineNameEn() {
		return wineNameEn;
	}

	public void setWineNameEn(String wineNameEn) {
		this.wineNameEn = wineNameEn;
	}

	public String getWineArea() {
		return wineArea;
	}

	public void setWineArea(String wineArea) {
		this.wineArea = wineArea;
	}

	public String getWineContent() {
		return wineContent;
	}

	public void setWineContent(String wineContent) {
		this.wineContent = wineContent;
	}

	public String getWineImg() {
		return wineImg;
	}

	public void setWineImg(String wineImg) {
		this.wineImg = wineImg;
	}

	public int getWinePrice() {
		return winePrice;
	}

	public void setWinePrice(int winePrice) {
		this.winePrice = winePrice;
	}

	public int getWineStock() {
		return wineStock;
	}

	public void setWineStock(int wineStock) {
		this.wineStock = wineStock;
	}

	public int getWineSales() {
		return wineSales;
	}

	public void setWineSales(int wineSales) {
		this.wineSales = wineSales;
	}

	public double getAlcoholLevel() {
		return alcoholLevel;
	}

	public void setAlcoholLevel(double alcoholLevel) {
		this.alcoholLevel = alcoholLevel;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public int getNationCode() {
		return nationCode;
	}

	public void setNationCode(int nationCode) {
		this.nationCode = nationCode;
	}

	@Override
	public String toString() {
		return "WineAndCategoryEntity [wineCode=" + wineCode + ", wineNameKo=" + wineNameKo + ", wineNameEn="
				+ wineNameEn + ", wineArea=" + wineArea + ", wineContent=" + wineContent + ", wineImg=" + wineImg
				+ ", winePrice=" + winePrice + ", wineStock=" + wineStock + ", wineSales=" + wineSales
				+ ", alcoholLevel=" + alcoholLevel + ", category=" + category + ", nationCode=" + nationCode + "]";
	}


}
