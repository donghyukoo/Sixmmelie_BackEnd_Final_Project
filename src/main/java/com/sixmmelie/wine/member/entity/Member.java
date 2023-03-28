package com.sixmmelie.wine.member.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "MEMBER")
@SequenceGenerator(
	name = "MEMBER_SEQ_GENERATOR",
	sequenceName = "SEQ_MEMBER_NO",
	initialValue = 1, allocationSize = 1
)
public class Member {	// AuthService에서 Member로 넘어온 엔티티를 받아내는 클래스

	@Id
	@Column(name = "MEMBER_NO")
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "MEMBER_SEQ_GENERATOR"
	)
	private int memberNo;
	
	@Column(name = "MEMBER_NAME")
	private String memberName;
	
	@Column(name = "MEMBER_ID")
	private String memberId;
	
	@Column(name = "MEMBER_PW")
	private String memberPw;
	
	@Column(name = "MEMBER_BIRTHDATE")
	private String memberBirthDate;
	
	@Column(name = "MEMBER_ADDRESS")
	private String memberAddress;
	
	@Column(name = "MEMBER_PHONE")
	private String memberPhone;
	
	@Column(name = "MEMBER_EMAIL")
	private String memberEmail;
	
	@Column(name = "MEMBER_LEVEL")
	private String memberLevel;
	
	@OneToMany
	@JoinColumn(name = "MEMBER_NO")
	private List<MemberRole> memberRole;

	public Member() {
		super();
	}

	public Member(int memberNo, String memberName, String memberId, String memberPw, String memberBirthDate,
			String memberAddress, String memberPhone, String memberEmail, String memberLevel,
			List<MemberRole> memberRole) {
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
		this.memberRole = memberRole;
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

	public List<MemberRole> getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(List<MemberRole> memberRole) {
		this.memberRole = memberRole;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberName=" + memberName + ", memberId=" + memberId + ", memberPw="
				+ memberPw + ", memberBirthDate=" + memberBirthDate + ", memberAddress=" + memberAddress
				+ ", memberPhone=" + memberPhone + ", memberEmail=" + memberEmail + ", memberLevel=" + memberLevel
				+ ", memberRole=" + memberRole + "]";
	}

}
