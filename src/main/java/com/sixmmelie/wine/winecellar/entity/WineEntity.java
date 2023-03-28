package com.sixmmelie.wine.winecellar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "WINE")
@SequenceGenerator( name = "WINE_SEQ_GENERATOR",
					sequenceName = "SEQ_WINE_CODE",
					initialValue = 1, allocationSize = 1
)
public class WineEntity {
	
	@Id
	@Column(name = "WINE_CODE")
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "WINE_SEQ_GENERATOR"
	)
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
	
	@Column(name = "CATEGORY_CODE")
	private int categoryCode;
	
	@Column(name = "NATION_CODE")
	private int nationCode;

	public WineEntity() {
		super();
	}

	public WineEntity(int wineCode, String wineNameKo, String wineNameEn, String wineArea, String wineContent,
			String wineImg, int winePrice, int wineStock, int wineSales, double alcoholLevel, int categoryCode,
			int nationCode) {
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
		this.categoryCode = categoryCode;
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

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	public int getNationCode() {
		return nationCode;
	}

	public void setNationCode(int nationCode) {
		this.nationCode = nationCode;
	}

	@Override
	public String toString() {
		return "WineEntity [wineCode=" + wineCode + ", wineNameKo=" + wineNameKo + ", wineNameEn=" + wineNameEn
				+ ", wineArea=" + wineArea + ", wineContent=" + wineContent + ", wineImg=" + wineImg + ", winePrice="
				+ winePrice + ", wineStock=" + wineStock + ", wineSales=" + wineSales + ", alcoholLevel=" + alcoholLevel
				+ ", categoryCode=" + categoryCode + ", nationCode=" + nationCode + "]";
	}


}
