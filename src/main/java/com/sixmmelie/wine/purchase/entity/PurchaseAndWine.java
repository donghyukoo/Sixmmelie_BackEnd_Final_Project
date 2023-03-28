package com.sixmmelie.wine.purchase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sixmmelie.wine.winecellar.entity.WineEntity;

@Entity
@Table(name = "PURCHASE")
public class PurchaseAndWine {

	@Id
	@Column(name = "PURCHASE_CODE")
	private int purchaseCode;
	
	@ManyToOne
	@JoinColumn(name = "WINE_CODE")
	private WineEntity wine;
	
	@Column(name = "ORDER_MEMBER")
	private int orderMember;
	
	@Column(name = "ORDER_PHONE")
	private String orderPhone;
	
	@Column(name = "ORDER_ADDRESS")
	private String orderAddress;
	
	@Column(name = "ORDER_AMOUNT")
	private String orderAmount;
	
	@Column(name = "ORDER_DATE")
	private String orderDate;

	public PurchaseAndWine() {
	}

	public PurchaseAndWine(int purchaseCode, WineEntity wine, int orderMember, String orderPhone, String orderAddress,
			String orderAmount, String orderDate) {
		super();
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

	public WineEntity getWine() {
		return wine;
	}

	public void setWine(WineEntity wine) {
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

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
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
		return "PurchaseAndWine [purchaseCode=" + purchaseCode + ", wine=" + wine + ", orderMember=" + orderMember
				+ ", orderPhone=" + orderPhone + ", orderAddress=" + orderAddress + ", orderAmount=" + orderAmount
				+ ", orderDate=" + orderDate + "]";
	}

}
