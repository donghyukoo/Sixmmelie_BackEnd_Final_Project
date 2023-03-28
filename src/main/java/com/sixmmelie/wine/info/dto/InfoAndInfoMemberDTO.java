package com.sixmmelie.wine.info.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

		
public class InfoAndInfoMemberDTO {

	private int infoNo;
	private String infoTitle;
	private String infoDetail;
	private String infoImg;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")	//GET메소드용
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")	//POST메소드용
	private Date infoDate;
	private InfoMemberDTO infoMember;
	
	
	public InfoAndInfoMemberDTO() {
		super();
	}
	public InfoAndInfoMemberDTO(int infoNo, String infoTitle, String infoDetail, String infoImg, Date infoDate,
			InfoMemberDTO infoMember) {
		super();
		this.infoNo = infoNo;
		this.infoTitle = infoTitle;
		this.infoDetail = infoDetail;
		this.infoImg = infoImg;
		this.infoDate = infoDate;
		this.infoMember = infoMember;
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
	public InfoMemberDTO getInfoMember() {
		return infoMember;
	}
	public void setInfoMember(InfoMemberDTO infoMember) {
		this.infoMember = infoMember;
	}
	@Override
	public String toString() {
		return "InfoAndInfoMemberDTO [infoNo=" + infoNo + ", infoTitle=" + infoTitle + ", infoDetail=" + infoDetail
				+ ", infoImg=" + infoImg + ", infoDate=" + infoDate + ", infoMember=" + infoMember + "]";
	}

}
