package com.sixmmelie.wine.info.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sixmmelie.wine.common.Criteria;
import com.sixmmelie.wine.common.PageDTO;
import com.sixmmelie.wine.common.PagingResponseDTO;
import com.sixmmelie.wine.common.ResponseDTO;
import com.sixmmelie.wine.info.dto.InformationDTO;
import com.sixmmelie.wine.info.service.InfoService;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class InfoController {

	
	private static final Logger log = LoggerFactory.getLogger(InfoController.class);
	
	private final InfoService infoService;

	private String search;
	
	@Autowired
	public InfoController(InfoService infoService) {
		this.infoService = infoService;
	}
	
	@Operation(summary = "전체 리스트 조회 요청", description = "상품 조회 및 페이징 처리가 진행됩니다.", tags = { "InfoController" })
	@GetMapping("/informations")
	public ResponseEntity<ResponseDTO> selectInfoListWithPaging(
			@RequestParam(name = "offset", defaultValue = "1") String offset) {

		/* common 패키지에 Criteria, PageDTO, PagingResponseDTO 추가 */
		log.info("[InfoController] selectInfoListWithPaging : " + offset);

		int total = infoService.selectInfoTotal();
		
		Criteria cri = new Criteria(Integer.valueOf(offset),10);
		PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
		
		/* 1. offset의 번호에 맞는 페이지에 뿌릴 Product들 */
		pagingResponseDTO.setData(infoService.selectInfoListWithPaging(cri));
		
		/* 2. PageDTO(Criteria(보고싶은 페이지, 한페이지에 뿌릴 개수), 전체 상품 수) : 화면에서 페이징 처리에 필요한 개념들을 더 계산해서 추출함 */
		pagingResponseDTO.setPageInfo(new PageDTO(cri, total));

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
	}
	
	@Operation(summary = "상세 조회 요청", description = "상세 페이지 처리가 진행됩니다.", tags = { "InfoController" })
	@GetMapping("/information/{infoNo}")
    public ResponseEntity<ResponseDTO> selectInfoDetail(@PathVariable int infoNo) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상세정보 조회 성공",  infoService.selectInfoDetail(infoNo)));
    }

	@Operation(summary = " 리스트 검색 요청", description = "검색어에 해당되는  리스트가 조회됩니다.", tags = { "InfoController" })
	@GetMapping("/information/search")
    public ResponseEntity<ResponseDTO> selectSearchInfoList(@RequestParam(name="title", defaultValue="all") String search,
    		@RequestParam(name = "search", defaultValue = "1")String offset) {
		
		int total = infoService.selectSearchInfoList(search);
		
		Criteria cri = new Criteria(Integer.valueOf(offset),10);
		PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
		
		pagingResponseDTO.setData(infoService.selectSearchInfoListWithPaging(cri, search));
		pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }
	
	@Operation(summary = "상품 등록 요청", description = "해당 상품 등록이 진행됩니다.", tags = { "InfoController" })
    @PostMapping(value = "/information")
	public ResponseEntity<ResponseDTO> insertInfo(@ModelAttribute InformationDTO informationDTO, MultipartFile infoImage) {
		log.info("infoImage : " + infoImage);
		log.info("insertInfo : " + informationDTO);
    	return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상품 입력 성공",  infoService.insertInfo(informationDTO, infoImage)));
    }
    
	@Operation(summary = "상품 수정 요청", description = "해당 상품 수정이 진행됩니다.", tags = { "InfoController" })
    @PutMapping(value = "/information")
    public ResponseEntity<ResponseDTO> updateInfo(@ModelAttribute InformationDTO informationDTO, MultipartFile infoImage) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상품 수정 성공",  infoService.updateInfo(informationDTO, infoImage)));
    }
}
