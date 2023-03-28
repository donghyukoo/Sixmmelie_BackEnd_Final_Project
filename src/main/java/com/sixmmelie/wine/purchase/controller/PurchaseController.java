package com.sixmmelie.wine.purchase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sixmmelie.wine.common.ResponseDTO;
import com.sixmmelie.wine.purchase.dto.PurchaseDTO;
import com.sixmmelie.wine.purchase.service.PurchaseService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class PurchaseController {
	
	private static final Logger log = LoggerFactory.getLogger(PurchaseController.class);
	
	private final PurchaseService purchaseService;

	@Autowired
	public PurchaseController(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	@Operation(summary = "상품 주문 요청", description = "해당 상품 주문이 진행됩니다.", tags = { "OrderController" })
	@PostMapping("/purchase")
	public ResponseEntity<ResponseDTO> insertPurchase(@RequestBody PurchaseDTO purchaseDTO) {	// @RequestBody를 써서 넘어온 JSON 문자열을 모두 받아줄 DTO를 작성할 것(getter, setter 필수!)

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "주문 성공",  purchaseService.insertProduct(purchaseDTO)));
	}

	@Operation(summary = "회원 주문 리스트 조회 요청", description = "해당 회원의 주문건에 대한 상품 리스트 조회가 진행됩니다.", tags = { "OrderController" })
	@GetMapping("/purchase/{memberId}")
    public ResponseEntity<ResponseDTO> getPurchaseList(@PathVariable String memberId) {
	
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "주문리스트 조회 성공",  purchaseService.selectPurchaseList(memberId)));
    }
}
