package com.sixmmelie.wine.member.dto;

public class MemberRoleDTO {	// 회원 별 권한 DTO
	private int memberNo;
	
	/* 권한부분이며 이 부분을 잘 알고 있어야 한다. 복합키를 만드는 용도로 속성을 설정함. */
	private int authorityCode; // 엔티티를 작성하고 복합키 설정에 용이하기 위함이자 MemberRole Insert나 Update에서 필수 이다
	
	private AuthorityDTO authority;	// 이부분을 잘 알고있어야한다는데 이게 뭐 내용이더라

	
	public MemberRoleDTO() {}

	public MemberRoleDTO(int memberNo, int authorityCode, AuthorityDTO authority) {
		super();
		this.memberNo = memberNo;
		this.authorityCode = authorityCode;
		this.authority = authority;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public int getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(int authorityCode) {
		this.authorityCode = authorityCode;
	}

	public AuthorityDTO getAuthority() {
		return authority;
	}

	public void setAuthority(AuthorityDTO authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "MemberRoleDTO [memberNo=" + memberNo + ", authorityCode=" + authorityCode + ", authority=" + authority
				+ "]";
	}

	
}
