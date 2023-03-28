package com.sixmmelie.wine.member.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MemberDTO implements UserDetails{	// memberDTO는 회원별 권한을 보기위해 모델링을 참고하며 작성해야 한다.
	
	private int memberNo;
	private String memberName;
	private String memberId;
	private String memberPw;
	private String memberBirthDate;
	private String memberAddress;
	private String memberPhone;
	private String memberEmail;                                                                                                                       
	private String memberLevel;
	private List<MemberRoleDTO> memberRole; // 회원한명이 여러 권한을 가질수 있으니 리스트형태로RoleDTO를 객체로 갖고잇는다.

	public MemberDTO() {}
	
	public MemberDTO(int memberNo, String memberName, String memberId, String memberPw, String memberBirthDate,
			String memberAddress, String memberPhone, String memberEmail, String memberLevel,
			List<MemberRoleDTO> memberRole, Collection<GrantedAuthority> authorities) {
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
		this.authorities = authorities;
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
	public List<MemberRoleDTO> getMemberRole() {
		return memberRole;
	}
	public void setMemberRole(List<MemberRoleDTO> memberRole) {
		this.memberRole = memberRole;
	}

	@Override
	public String toString() {
		return "MemberDTO [memberNo=" + memberNo + ", memberName=" + memberName + ", memberId=" + memberId
				+ ", memberPw=" + memberPw + ", memberBirthDate=" + memberBirthDate + ", memberAddress=" + memberAddress
				+ ", memberPhone=" + memberPhone + ", memberEmail=" + memberEmail + ", memberLevel=" + memberLevel
				+ ", memberRole=" + memberRole + ", authorities=" + authorities + "]";
	}

	/* 이 아래 코드들은 UserDetailes로부터 물려받는 추상메소드들을 오버라이딩 한 것이다.(필요한것만 작성하자) */
	/* MemberDTO는 Member와 매핑 될 DTO이자 UserDetails로써 속성을 추가로 가진다. */
	private Collection<GrantedAuthority> authorities;
	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	@Override
	public String getPassword() {
		return memberPw;
	}
	@Override
	public String getUsername() {
		return memberId;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
