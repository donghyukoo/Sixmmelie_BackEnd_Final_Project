package com.sixmmelie.wine.info.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER")
public class InfoMember {

	@Id
	@Column(name = "MEMBER_NO")
	private int memberNo;
	@Column(name = "MEMBER_ID")
	private String memberId;
	@Column(name = "MEMBER_NAME")
	private String memberName;
	
	public InfoMember() {
		super();
	}

	public InfoMember(int memberNo, String memberId, String memberName) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberName = memberName;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Override
	public String toString() {
		return "InfoMember [memberNo=" + memberNo + ", memberId=" + memberId + ", memberName=" + memberName + "]";
	}
	
	
	
}
