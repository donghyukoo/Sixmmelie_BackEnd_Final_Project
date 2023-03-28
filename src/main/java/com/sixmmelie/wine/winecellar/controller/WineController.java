package com.sixmmelie.wine.winecellar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sixmmelie.wine.common.Criteria;
import com.sixmmelie.wine.common.PageDTO;
import com.sixmmelie.wine.common.PagingResponseDTO;
import com.sixmmelie.wine.common.ResponseDTO;
import com.sixmmelie.wine.winecellar.service.WineService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class WineController {
	
	private static final Logger log = LoggerFactory.getLogger(WineController.class);
	private final WineService wineService;
	
	@Autowired
	public WineController(WineService wineService) {
		this.wineService = wineService;
	}
	
	@Operation(summary = "판매량으로 조회 요청", description = "판매량순으로 조회가 진행됩니다.", tags = { "WineController" })
	@GetMapping("/")
	public ResponseEntity<ResponseDTO> selectWineSalesAboutMain(
			@RequestParam(name = "offset", defaultValue = "1") String offset) {
		
		int totalSales = wineService.selectWineSalesTotal();
		Criteria cri	 = new Criteria(Integer.valueOf(offset), 4);
		PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

		pagingResponseDTO.setData(wineService.selectWineSalesAboutMain(cri));
		pagingResponseDTO.setPageInfo(new PageDTO(cri, totalSales));

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "판매량으로 조회 성공", pagingResponseDTO));
	}
	
	@Operation(summary = "서베이로 조회 요청", description = "서베이로 조회가 진행됩니다.", tags = { "WineController" })
	   @GetMapping("/survey")
	   public ResponseEntity<ResponseDTO> selectSurveyWineCode(
			   @RequestParam(name = "nationCode", defaultValue = "5") int nationCode
	         , @RequestParam(name = "winePrice", defaultValue = "12500")int winePrice
	         , @RequestParam(name = "alcoholLevel", defaultValue = "12") double alcoholLevel
	      ) {
	      
//	      log.info("selectSurveyWineCode : " + nationCode);
//	      log.info("selectSurveyWineCode: " + winePrice);
//	      log.info("selectSurveyWineCode: " + alcoholLevel);
	      return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "와인 서베이로 상세정보 조회 성공",
	    		  					wineService.selectAll(nationCode, winePrice, alcoholLevel)));
	   }

	
}
