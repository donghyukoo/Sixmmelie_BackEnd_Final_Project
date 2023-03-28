package com.sixmmelie.wine.idpwfind.entity;

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
@DynamicInsert
@DynamicUpdate
@SequenceGenerator(
		name = "MEMBER_SEQ_GENERATOR",
		sequenceName = "SEQ_MEMBER_NO",
		initialValue = 1, allocationSize = 1
		)
public class MemberFind {

		@Id
		@Column(name = "MEMBER_NO")
		@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "MEMBER_SEQ_GENERATOR"
		)
		private int memberNo;
		
		@Column(name = "MEMBER_NAME", insertable = true, updatable = false)
		private String memberName;
		
		@Column(name = "MEMBER_ID", insertable = true, updatable = false)
		private String memberId;
		
		@Column(name = "MEMBER_PW")
		private String memberPw;
		
		@Column(name = "MEMBER_BIRTHDATE", insertable = true, updatable = false)
		private String memberBirthDate;
		
		@Column(name = "MEMBER_ADDRESS", insertable = true, updatable = false)
		private String memberAddress;
		
		@Column(name = "MEMBER_PHONE", insertable = true, updatable = false)
		private String memberPhone;
		
		@Column(name = "MEMBER_EMAIL", insertable = true, updatable = false)
		private String memberEmail;
		
		@Column(name = "MEMBER_LEVEL", insertable = true, updatable = false)
		private String memberLevel;

		public MemberFind() {
			super();
		}

		public MemberFind(int memberNo, String memberName, String memberId, String memberPw, String memberBirthDate,
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
			return "Memberfind [memberNo=" + memberNo + ", memberName=" + memberName + ", memberId=" + memberId
					+ ", memberPw=" + memberPw + ", memberBirthDate=" + memberBirthDate + ", memberAddress="
					+ memberAddress + ", memberPhone=" + memberPhone + ", memberEmail=" + memberEmail + ", memberLevel="
					+ memberLevel + "]";
		}
		
		

		

}
