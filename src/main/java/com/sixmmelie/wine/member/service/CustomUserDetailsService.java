package com.sixmmelie.wine.member.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sixmmelie.wine.member.dto.MemberDTO;
import com.sixmmelie.wine.member.entity.Member;
import com.sixmmelie.wine.member.entity.MemberRole;
import com.sixmmelie.wine.member.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;
	
	@Autowired
	public CustomUserDetailsService(MemberRepository memberRepository, ModelMapper modelMapper) {
		this.memberRepository = memberRepository;
		this.modelMapper = modelMapper;		// ModelMapper 설정파일(BeanConfiguration클래스)에 Bean 등록할것!
	}

	/*
	 * org.hibernate.LazyInitializationException 에러가 발생한다면...
	 * 조회라도 @Transaction 어노테이션을 달자!
	 * 해당 에러는 영속성 컨텍스트가 도중에 종료되어 발생하는 오류이다. 트랜잭셔널어노테이션을 달아주면 메소드가 끝날때 까지 영속성 컨텍스트가 인지하고 있음!
	 * @Transactional을 달면 해당 메소드가 끝날 때 까지 하나의 영속성 컨텍스트가 유지되어 뒤늦게 연관관계에 있는
	 * 엔티티를 활용함에 있어서 문제되지 않는다.
	 */
	@Transactional
	@Override	
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {	// 여기 보고 있었음
		Member member = memberRepository.findByMemberId(memberId);
		
		/* MemberDTO는 엔티티를 옮겨 담는 DTO이자 UserDetails이다.*/
		MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
		
		/* 엔티티로는 MemberDTO에 추가한 Collection<GrantedAuthority> authorities 속성이 옮겨담아지지 않는다. */
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(MemberRole memberRole : member.getMemberRole()) {
			String authorityName = memberRole.getAuthority().getAuthorityName();
			authorities.add(new SimpleGrantedAuthority(authorityName));		// TokenProvider 에서는 SimpleGrantedAuthority를 클레임에서 뽑아냈다면
																			// 지금은 화면단에서 문자열일 넘어왔을때 권한을 추출한다.(id, password)
		}
		
		memberDTO.setAuthorities(authorities);
		
		return memberDTO;	//memberDTO는  UserDetails이기도 하다 DTO에  UserDetails를 상속받아 메소드를 정의함
	}
}
