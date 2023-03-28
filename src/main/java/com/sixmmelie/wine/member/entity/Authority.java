package com.sixmmelie.wine.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHORITY")
public class Authority {

	@Id
	@Column(name = "AUTHORITY_CODE")
	private int authorityCode;
	
	@Column(name = "AUTHORITY_NAME")
	private String authorityName;

	public Authority() {
		super();
	}

	public Authority(int authorityCode, String authorityName) {
		super();
		this.authorityCode = authorityCode;
		this.authorityName = authorityName;
	}

	public int getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(int authorityCode) {
		this.authorityCode = authorityCode;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	@Override
	public String toString() {
		return "Authority [authorityCode=" + authorityCode + ", authorityName=" + authorityName + "]";
	}
	
	
}
