 package com.sixmmelie.wine.member.dto;

public class AuthorityDTO {

	private int code;
	private String name;
	
	public AuthorityDTO() {}
	
	public AuthorityDTO(int code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "AuthorityDTO [code=" + code + ", name=" + name + "]";
	}

	
}
