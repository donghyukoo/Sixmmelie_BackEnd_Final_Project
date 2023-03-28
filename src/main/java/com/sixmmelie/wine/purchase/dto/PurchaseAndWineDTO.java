package com.sixmmelie.wine.purchase.dto;

import com.sixmmelie.wine.winecellar.dto.WineDTO;

public class PurchaseAndWineDTO {
	private int purchaseCode;
	private WineDTO wine;
	private int orderMember;
	private String orderPhone;
	private String orderAddress;
	private int orderAmount;
	private String orderDate;
	
	public PurchaseAndWineDTO() {
	}

	public PurchaseAndWineDTO(int purchaseCode, WineDTO wine, int orderMember, String orderPhone, String orderAddress,
			int orderAmount, String orderDate) {
		this.purchaseCode = purchaseCode;
		this.wine = wine;
		this.orderMember = orderMember;
		this.orderPhone = orderPhone;
		this.orderAddress = orderAddress;
		this.orderAmount = orderAmount;
		this.orderDate = orderDate;
	}

	public int getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(int purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	public WineDTO getWine() {
		return wine;
	}

	public void setWine(WineDTO wine) {
		this.wine = wine;
	}

	public int getOrderMember() {
		return orderMember;
	}

	public void setOrderMember(int orderMember) {
		this.orderMember = orderMember;
	}

	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "PurchaseAndWineDTO [purchaseCode=" + purchaseCode + ", orderMember=" + orderMember + ", orderPhone="
				+ orderPhone + ", orderAddress=" + orderAddress + ", orderAmount=" + orderAmount + ", orderDate="
				+ orderDate + "]";
	}
	
}
