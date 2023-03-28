package com.sixmmelie.wine.purchase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PURCHASE")
@SequenceGenerator(
		name = "PURCHASE_SEQ_GENERATOR",
		sequenceName = "SEQ_PURCHASE_CODE",
		initialValue = 1, allocationSize = 1
)
public class Purchase {

	@Id
	@Column(name = "PURCHASE_CODE")
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "PURCHASE_SEQ_GENERATOR"
	)
	private int purchaseCode;
	
	@Column(name = "WINE_CODE")
	private int wineCode;
	
	@Column(name = "ORDER_MEMBER")
	private int orderMember;
	
	@Column(name = "ORDER_PHONE")
	private String orderPhone;
	
	@Column(name = "ORDER_ADDRESS")
	private String orderAddress;
	
	@Column(name = "ORDER_AMOUNT")
	private int orderAmount;
	
	@Column(name = "ORDER_DATE")
	private String orderDate;

	public Purchase() {
	}

	public Purchase(int purchaseCode, int wineCode, int orderMember, String orderPhone, String orderAddress,
			int orderAmount, String orderDate) {
		this.purchaseCode = purchaseCode;
		this.wineCode = wineCode;
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
		return "Purchase [purchaseCode=" + purchaseCode + ", wineCode=" + wineCode + ", orderMember=" + orderMember
				+ ", orderPhone=" + orderPhone + ", orderAddress=" + orderAddress + ", orderAmount=" + orderAmount
				+ ", orderDate=" + orderDate + "]";
	}

}
