package com.sixmmelie.wine.member.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sixmmelie.wine.common.ResponseDTO;
import com.sixmmelie.wine.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")		// 경로는 우리껄로 수정해야함 
public class MemberController {

	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
//	@Operation(summary = "회원 조회 요청", description = "회원 한명이 조회됩니다.", tags = { "MemberController" }) // 스웨거
	@GetMapping("/members/{memberId}")
	public ResponseEntity<ResponseDTO> selectMyMemberInfo(@PathVariable String memberId) {
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", memberService.selectMyInfo(memberId)));
	}
	
//	@Operation(summary = "회원 등급 수정 요청", description = "회원 등급 수정이 진행됩니다.", tags = {"MemberController"})
//	@PutMapping(value = "/memberUpdate")
////	public ResponseEntity<ResponseDTO> updateLevel(@ModelAttribute MemberDTO memberDTO) {
//	public ResponseEntity<ResponseDTO> updateLevel(@RequestBody Map map) {
//		System.out.println("controller: " + map);
//		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원 등급 수정 성공",  memberService.updateLevel(map)));
////		return null;
//	}
	
}
