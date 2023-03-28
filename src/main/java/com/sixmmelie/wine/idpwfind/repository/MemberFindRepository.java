package com.sixmmelie.wine.idpwfind.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sixmmelie.wine.idpwfind.entity.MemberFind;

public interface MemberFindRepository extends JpaRepository<MemberFind, Integer>{
	
/* 아이디 비밀번호 찾기 */
	
	MemberFind findByMemberNameAndMemberEmail(String memberName, String memberEmail);
	
	MemberFind findByMemberIdAndMemberEmail(String memberId, String memberEmail);
}
