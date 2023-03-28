package com.sixmmelie.wine.info.dto;

public class InfoMemberDTO {

	private int memberNo;
	private String memberName;
	private String memberId;
	
	public InfoMemberDTO() {
		super();
	}
	public InfoMemberDTO(int memberNo, String memberName, String memberId) {
		super();
		this.memberNo = memberNo;
		this.memberName = memberName;
		this.memberId = memberId;
	}
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	@Override
	public String toString() {
		return "InfoMemberDTO [memberNo=" + memberNo + ", memberName=" + memberName + ", memberId=" + memberId + "]";
	}
	
	
}
