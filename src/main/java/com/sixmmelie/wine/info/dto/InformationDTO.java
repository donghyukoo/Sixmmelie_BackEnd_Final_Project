package com.sixmmelie.wine.info.dto;

import java.util.Date;

public class InformationDTO {

	private int infoNo;
	private String infoTitle;
	private String infoDetail;
	private String infoImg;
	private Date infoDate;
	private int memberNo;
	
	
	
	public InformationDTO() {
		super();
	}

	public InformationDTO(int infoNo, String infoTitle, String infoDetail, String infoImg, Date infoDate,
			int memberNo) {
		super();
		this.infoNo = infoNo;
		this.infoTitle = infoTitle;
		this.infoDetail = infoDetail;
		this.infoImg = infoImg;
		this.infoDate = infoDate;
		this.memberNo = memberNo;
	}

	public int getInfoNo() {
		return infoNo;
	}

	public void setInfoNo(int infoNo) {
		this.infoNo = infoNo;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoDetail() {
		return infoDetail;
	}

	public void setInfoDetail(String infoDetail) {
		this.infoDetail = infoDetail;
	}

	public String getInfoImg() {
		return infoImg;
	}

	public void setInfoImg(String infoImg) {
		this.infoImg = infoImg;
	}

	public Date getInfoDate() {
		return infoDate;
	}

	public void setInfoDate(Date infoDate) {
		this.infoDate = infoDate;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	@Override
	public String toString() {
		return "InformationDTO [infoNo=" + infoNo + ", infoTitle=" + infoTitle + ", infoDetail=" + infoDetail
				+ ", infoImg=" + infoImg + ", infoDate=" + infoDate + ", memberNo=" + memberNo + "]";
	}
	
	
	
	
}
