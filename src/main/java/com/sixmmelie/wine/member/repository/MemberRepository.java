package com.sixmmelie.wine.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sixmmelie.wine.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{

	Member findByMemberId(String memberId);	// 쿼리 메소드를 물려받는 JpaRepository를 상속받음 

	/* 중복값,유효성검사용 */
	Member findByMemberEmail(String Email);	// 회원가입시 이메일이 중복되는지,DB에 중복 이메일이 있는지 확인한다. 

	/*  회원가입용 jpql과 @Query를 활용한 구문 (회원가입시 DB에 있는 마지막 회원번호 추출을 하기 위해 사용)*/
	@Query("SELECT MAX(a.memberNo) FROM Member a")	// jqpl에서 엔티티 이름은 반드시 대소문자까지 완벽히 일치할 것 (Member)
	int maxMemberNo();

	@Query("SELECT a.memberNo FROM Member a WHERE a.memberId = ?1")
	int findMemberNoByMemberId(String memberId);

//	@Query("SELECT a(a.memberCode) FROM Member a")	// 여러가지를 뽑을땐 object로 받아낼 수도 있지만 이렇게 전체적으로 뽑아낼수도있다. 참고
//	Member maxMemberCode();
	
	

}
