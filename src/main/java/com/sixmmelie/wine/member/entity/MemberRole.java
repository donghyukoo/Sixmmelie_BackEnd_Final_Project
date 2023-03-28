package com.sixmmelie.wine.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER_ROLE")
@IdClass(MemberRolePk.class)	// 복합키용 어노테이션이고 레파지토리를 따로 만들어줘야한다.
public class MemberRole {

	/* 복합키용 insert 작업시에 사용한다. */
	@Id
	@Column(name = "MEMBER_NO")
	private int memberNo;
	
	/* 복합키용 insert 작업시에 사용한다. */
	@Id
	@Column(name = "AUTHORITY_CODE")
	private int authorityCode;
	
	/* 복합키 조회 작업시에 사용하는 컬럼 */
	@ManyToOne
	@JoinColumn(name = "AUTHORITY_CODE",insertable = false, updatable = false)
	private Authority authority;

	public MemberRole() {
	}

	/* insert를 할 때 Authority는 필요가 없으므로 생성자를 생성할때 authority는 체크를 해제하고 만들었다.*/
	public MemberRole(int memberNo, int authorityCode) {
		super();
		this.memberNo = memberNo;
		this.authorityCode = authorityCode;
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

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "MemberRole [memberNo=" + memberNo + ", authorityCode=" + authorityCode + ", authority=" + authority
				+ "]";
	}
	
}
