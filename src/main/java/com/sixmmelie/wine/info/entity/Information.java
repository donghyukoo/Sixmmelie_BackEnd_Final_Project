package com.sixmmelie.wine.info.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "INFORMATION")
@DynamicInsert
@DynamicUpdate
@SequenceGenerator(
		name = "INFORMATION_SEQ_GENERATOR",
		sequenceName = "SEQ_INFO_NO",
		initialValue = 1, allocationSize = 1
)		
public class Information {

	@Id
	@Column(name = "INFO_NO")
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "INFORMATION_SEQ_GENERATOR"
	)
	private int infoNo;
	
	@Column(name = "INFO_TITLE")
	private String infoTitle;
	
	@Column(name = "INFO_DETAIL")
	private String infoDetail;
	
	@Column(name = "INFO_IMG")
	private String infoImg;
	
	@Column(name = "INFO_DATE")
	private Date infoDate;
	
	@Column(name = "MEMBER_NO")
	private int memberNo;

	public Information() {
	}

	public Information(int infoNo, String infoTitle, String infoDetail, String infoImg, Date infoDate, int memberNo) {
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
		return "Information [infoNo=" + infoNo + ", infoTitle=" + infoTitle + ", infoDetail=" + infoDetail
				+ ", infoImg=" + infoImg + ", infoDate=" + infoDate + ", memberNo=" + memberNo + "]";
	}
 
	
}
