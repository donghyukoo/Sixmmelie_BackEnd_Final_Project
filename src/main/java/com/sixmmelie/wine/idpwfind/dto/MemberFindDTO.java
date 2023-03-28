package com.sixmmelie.wine.idpwfind.dto;

public class MemberFindDTO {
	private int memberNo;
	private String memberName;
	private String memberId;
	private String memberPw;
	private String memberBirthDate;
	private String memberAddress;
	private String memberPhone;
	private String memberEmail;                                                                                                                       
	private String memberLevel;
	
	public MemberFindDTO() {
		super();
	}

	public MemberFindDTO(int memberNo, String memberName, String memberId, String memberPw, String memberBirthDate,
			String memberAddress, String memberPhone, String memberEmail, String memberLevel) {
		super();
		this.memberNo = memberNo;
		this.memberName = memberName;
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberBirthDate = memberBirthDate;
		this.memberAddress = memberAddress;
		this.memberPhone = memberPhone;
		this.memberEmail = memberEmail;
		this.memberLevel = memberLevel;
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

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberBirthDate() {
		return memberBirthDate;
	}

	public void setMemberBirthDate(String memberBirthDate) {
		this.memberBirthDate = memberBirthDate;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	@Override
	public String toString() {
		return "MemberFindDTO [memberNo=" + memberNo + ", memberName=" + memberName + ", memberId=" + memberId
				+ ", memberPw=" + memberPw + ", memberBirthDate=" + memberBirthDate + ", memberAddress=" + memberAddress
				+ ", memberPhone=" + memberPhone + ", memberEmail=" + memberEmail + ", memberLevel=" + memberLevel
				+ "]";
	}

}
