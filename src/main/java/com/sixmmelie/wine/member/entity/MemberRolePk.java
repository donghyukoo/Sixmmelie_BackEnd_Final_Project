package com.sixmmelie.wine.member.entity;

import java.io.Serializable;

/* 복합키 타입을 정의할 때는 Serializable을 반드시 구현해야 한다.*/
public class MemberRolePk implements Serializable{ // 복합키용 타입 클래스 반드시 직렬화를 해야한다.

	private int memberNo;
	private int authorityCode;
	public MemberRolePk() {
		super();
	}
	public MemberRolePk(int memberNo, int authorityCode) {
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
	@Override
	public String toString() {
		return "MemberRolePk [memberNo=" + memberNo + ", authorityCode=" + authorityCode + "]";
	}
	
}
