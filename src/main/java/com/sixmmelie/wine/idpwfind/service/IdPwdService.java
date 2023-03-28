package com.sixmmelie.wine.idpwfind.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sixmmelie.wine.idpwfind.dto.MemberFindDTO;
import com.sixmmelie.wine.idpwfind.entity.MemberFind;
import com.sixmmelie.wine.idpwfind.repository.MemberFindRepository;
import com.sixmmelie.wine.member.dto.MemberDTO;
import com.sixmmelie.wine.member.entity.Member;
import com.sixmmelie.wine.member.repository.MemberRepository;

@Service
public class IdPwdService {

	private final MemberFindRepository memberFindRepository;
	private final ModelMapper modelMapper;
	
	@Autowired
	public IdPwdService (MemberFindRepository memberFindRepository, ModelMapper modelMapper) {
		this.memberFindRepository = memberFindRepository;
		this.modelMapper = modelMapper;
	}

	/* 아이디 찾기 */
	public MemberFindDTO findByMemberNameAndMemberEmail(String memberName, String memberEmail) {
		MemberFind member = memberFindRepository.findByMemberNameAndMemberEmail(memberName, memberEmail);
		return member != null ? modelMapper.map(member, MemberFindDTO.class) : null;
	}
	
	/* 비밀번호 찾기 */ 
	public MemberFindDTO findByMemberIdAndMemberEmail(String memberId, String memberEmail) {
		MemberFind member = memberFindRepository.findByMemberIdAndMemberEmail(memberId, memberEmail);
		
		return member != null ? modelMapper.map(member, MemberFindDTO.class) : null;
	}
	
	@Transactional
	public void updatePw(MemberFindDTO newPw) {
		
		memberFindRepository.save(modelMapper.map(newPw, MemberFind.class));
		
	}
		
		
		
	}
