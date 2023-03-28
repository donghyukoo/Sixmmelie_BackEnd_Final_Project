package com.sixmmelie.wine.purchase.dto;

public class PurchaseDTO {
	private String memberId;
	private int purchaseCode;
	private int wineCode;
	private int orderMember;
	private String orderPhone;
	private String orderAddress;
	private int orderAmount;
	private String orderDate;

	public PurchaseDTO() {
	}

	public PurchaseDTO(String memberId, int purchaseCode, int wineCode, int orderMember, String orderPhone,
			String orderAddress, int orderAmount, String orderDate) {
		this.memberId = memberId;
		this.purchaseCode = purchaseCode;
		this.wineCode = wineCode;
		this.orderMember = orderMember;
		this.orderPhone = orderPhone;
		this.orderAddress = orderAddress;
		this.orderAmount = orderAmount;
		this.orderDate = orderDate;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(int purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	public int getWineCode() {
		return wineCode;
	}

	public void setWineCode(int wineCode) {
		this.wineCode = wineCode;
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
		return "PurchaseDTO [memberId=" + memberId + ", purchaseCode=" + purchaseCode + ", wineCode=" + wineCode
				+ ", orderMember=" + orderMember + ", orderPhone=" + orderPhone + ", orderAddress=" + orderAddress
				+ ", orderAmount=" + orderAmount + ", orderDate=" + orderDate + "]";
	}

	
}
