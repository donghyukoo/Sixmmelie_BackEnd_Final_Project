package com.sixmmelie.wine.winecellar.controller;

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
import com.sixmmelie.wine.winecellar.dto.WineDTO;
import com.sixmmelie.wine.winecellar.service.WinesService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class WinesController {
	
	
	private static final Logger log = LoggerFactory.getLogger(WinesController.class);

	private final WinesService winesService;
	
	@Autowired
	public WinesController(WinesService winesService) {
		this.winesService = winesService;
	}
	
	@Operation(summary = "와인 전체 리스트 조회 요청", description = "와인 전체 조회 및 페이징 처리가 진행됩니다.", tags = {"WineCotroller"})
	@GetMapping("/wines")
	public ResponseEntity<ResponseDTO> selectWineListWithPaging(
			@RequestParam(name = "offset", defaultValue = "1") String offset) {
		
//		log.info("[WineController] selectWineListWithPaging: {}", offset);		// [WineController] selectWineListWithPaging: 1
		
		int total = winesService.selectWineTotal();
//		log.info("total값: {}", total);											// total값: 367
		Criteria cri = new Criteria(Integer.valueOf(offset), 20);
		PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
		
		/* 1. offset의 번호에 맞는 페이지에 뿌릴 Product들 */
		pagingResponseDTO.setData(winesService.selectWineListWithPaging(cri));
//		log.info("☆☆☆[WineController] pagingResponseDTO의 data getter☆☆☆: {}", pagingResponseDTO.getData());
		
		/* 2. PageDTO(Criteria(보고싶은 페이지, 한페이지에 뿌릴 개수), 전체 상품 수) : 화면에서 페이징 처리에 필요한 개념들을 더 계산해서 추출함 */
		pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
//		log.info("[WineController] pagingResponseDTO의 pageInfo getter: {}", pagingResponseDTO.getPageInfo());
		
//		log.info("[WineController] selectWineListWithPaging(cri): {}", cri);	// Criteria{pageNum=1, amount=20, searchValue='null'}
//		log.info("☆☆☆[WineController] selectWineListWithPaging(pagingResponseDTO)☆☆☆: {}", pagingResponseDTO);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
	}
	
	@Operation(summary = "와인 상세 조회 요청", description = "와인의 상세 페이지 처리가 진행됩니다.", tags = { "WineController" })
	@GetMapping("/wines/{wineCode}")
	public ResponseEntity<ResponseDTO> selectWineDetail(@PathVariable int wineCode) {
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "와인 상세정보 조회 성공", winesService.selectWine(wineCode)));
	}
	
	@Operation(summary = "레드와인 리스트 조회 요청", description = "레드 와인에 해당하는 와인 리스트 조회가 진행됩니다.", tags = { "WineController" })
	@GetMapping("/wines/red")
	public ResponseEntity<ResponseDTO> selectWineListAboutRedListWithPaging(
			@RequestParam(name = "offset", defaultValue = "1") String offset) {
		int redtotal = winesService.selectRedWineTotal();
//		log.info("total값: {}", redtotal);
		Criteria cri = new Criteria(Integer.valueOf(offset), 20);
		PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
		
		/* 1. offset의 번호에 맞는 페이지에 뿌릴 Product들 */
		pagingResponseDTO.setData(winesService.selectWineListAboutRed(cri));
//		log.info("☆☆☆[WineController] pagingResponseDTO의 data getter☆☆☆: {}", pagingResponseDTO.getData());
		
		/* 2. PageDTO(Criteria(보고싶은 페이지, 한페이지에 뿌릴 개수), 전체 상품 수) : 화면에서 페이징 처리에 필요한 개념들을 더 계산해서 추출함 */
		pagingResponseDTO.setPageInfo(new PageDTO(cri, redtotal));
//		log.info("[WineController] pagingResponseDTO의 pageInfo getter: {}", pagingResponseDTO.getPageInfo());
		
//		log.info("[WineController] selectWineListWithPaging(cri): {}", cri);	// Criteria{pageNum=1, amount=20, searchValue='null'}
//		log.info("☆☆☆[WineController] selectWineListWithPaging(pagingResponseDTO)☆☆☆: {}", pagingResponseDTO);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
	}
	
	@Operation(summary = "화이트와인 리스트 조회 요청", description = "화이트 와인에 해당하는 와인 리스트 조회가 진행됩니다.", tags = { "WineController" })
	@GetMapping("/wines/white")
	public ResponseEntity<ResponseDTO> selectWineListAboutWhite(
			@RequestParam(name = "offset", defaultValue = "1") String offset) {
		int whitetotal = winesService.selectWhiteWineTotal();
//		log.info("total값: {}", whitetotal);
		Criteria cri = new Criteria(Integer.valueOf(offset), 20);
		PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
		
		/* 1. offset의 번호에 맞는 페이지에 뿌릴 Product들 */
		pagingResponseDTO.setData(winesService.selectWineListAboutWhite(cri));
//		log.info("☆☆☆[WineController] pagingResponseDTO의 data getter☆☆☆: {}", pagingResponseDTO.getData());
		
		/* 2. PageDTO(Criteria(보고싶은 페이지, 한페이지에 뿌릴 개수), 전체 상품 수) : 화면에서 페이징 처리에 필요한 개념들을 더 계산해서 추출함 */
		pagingResponseDTO.setPageInfo(new PageDTO(cri, whitetotal));
//		log.info("[WineController] pagingResponseDTO의 pageInfo getter: {}", pagingResponseDTO.getPageInfo());
		
//		log.info("[WineController] selectWineListWithPaging(cri): {}", cri);	// Criteria{pageNum=1, amount=20, searchValue='null'}
//		log.info("☆☆☆[WineController] selectWineListWithPaging(pagingResponseDTO)☆☆☆: {}", pagingResponseDTO);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
	}
	
	@Operation(summary = "로제와인 리스트 조회 요청", description = "로제 와인에 해당하는 와인 리스트 조회가 진행됩니다.", tags = { "WineController" })
	@GetMapping("/wines/rose")
	public ResponseEntity<ResponseDTO> selectWineListAboutRose(
			@RequestParam(name = "offset", defaultValue = "1") String offset) {
		int rosetotal = winesService.selectRoseWineTotal();
//		log.info("total값: {}", rosetotal);
		Criteria cri = new Criteria(Integer.valueOf(offset), 20);
		PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
		
		/* 1. offset의 번호에 맞는 페이지에 뿌릴 Product들 */
		pagingResponseDTO.setData(winesService.selectWineListAboutRose(cri));
//		log.info("☆☆☆[WineController] pagingResponseDTO의 data getter☆☆☆: {}", pagingResponseDTO.getData());
		
		/* 2. PageDTO(Criteria(보고싶은 페이지, 한페이지에 뿌릴 개수), 전체 상품 수) : 화면에서 페이징 처리에 필요한 개념들을 더 계산해서 추출함 */
		pagingResponseDTO.setPageInfo(new PageDTO(cri, rosetotal));
//		log.info("[WineController] pagingResponseDTO의 pageInfo getter: {}", pagingResponseDTO.getPageInfo());
		
//		log.info("[WineController] selectWineListWithPaging(cri): {}", cri);	// Criteria{pageNum=1, amount=20, searchValue='null'}
//		log.info("☆☆☆[WineController] selectWineListWithPaging(pagingResponseDTO)☆☆☆: {}", pagingResponseDTO);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
	}
	
	@Operation(summary = "스파클링와인 리스트 조회 요청", description = "스파클링 와인에 해당하는 와인 리스트 조회가 진행됩니다.", tags = { "WineController" })
	@GetMapping("/wines/sparkling")
	public ResponseEntity<ResponseDTO> selectWineListAboutSparkling(
			@RequestParam(name = "offset", defaultValue = "1") String offset) {
		int sparklingtotal = winesService.selectSparklingWineTotal();
//		log.info("total값: {}", sparklingtotal);
		Criteria cri = new Criteria(Integer.valueOf(offset), 20);
		PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
		
		/* 1. offset의 번호에 맞는 페이지에 뿌릴 Product들 */
		pagingResponseDTO.setData(winesService.selectWineListAboutSparkling(cri));
//		log.info("☆☆☆[WineController] pagingResponseDTO의 data getter☆☆☆: {}", pagingResponseDTO.getData());
		
		/* 2. PageDTO(Criteria(보고싶은 페이지, 한페이지에 뿌릴 개수), 전체 상품 수) : 화면에서 페이징 처리에 필요한 개념들을 더 계산해서 추출함 */
		pagingResponseDTO.setPageInfo(new PageDTO(cri, sparklingtotal));
//		log.info("[WineController] pagingResponseDTO의 pageInfo getter: {}", pagingResponseDTO.getPageInfo());
		
//		log.info("[WineController] selectWineListWithPaging(cri): {}", cri);	// Criteria{pageNum=1, amount=20, searchValue='null'}
//		log.info("☆☆☆[WineController] selectWineListWithPaging(pagingResponseDTO)☆☆☆: {}", pagingResponseDTO);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
	}
	
	@Operation(summary = "와인 조회 요청", description = "검색어에 해당하는 와인 리스트 조회가 진행됩니다.", tags = { "WineController" })
	@GetMapping("/wines/search")
	public ResponseEntity<ResponseDTO> selectSearchWineList(@RequestParam(name="wineNameKo", defaultValue="all") String search,
    		@RequestParam(name = "search", defaultValue = "1")String offset) {
		
		int total = winesService.selectSearchWineList(search);
		
		Criteria cri = new Criteria(Integer.valueOf(offset), 20);
		PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
		
		pagingResponseDTO.setData(winesService.selectSearchWineListWithPaging(cri, search));
		pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }
	
	@Operation(summary = "와인 등록 요청", description = "해당 와인 등록이 진행됩니다.", tags = {"WineController"})
	@PostMapping(value = "/wines")
	public ResponseEntity<ResponseDTO> insertWine(@ModelAttribute WineDTO wineDTO, MultipartFile wineImage) {
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "와인 등록 성공", winesService.insertWine(wineDTO, wineImage)));
	}
	
	@Operation(summary = "와인 수정 요청", description = "해당 와인 수정이 진행됩니다.", tags = {"WineController"})
	@PutMapping(value = "/wines")
	public ResponseEntity<ResponseDTO> updateWine(@ModelAttribute WineDTO  wineDTO, MultipartFile wineImage) {
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "와인 수정 성공",  winesService.updateWine(wineDTO, wineImage)));
	}
	
}
