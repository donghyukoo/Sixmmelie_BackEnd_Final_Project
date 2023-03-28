package com.sixmmelie.wine.purchase.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sixmmelie.wine.member.repository.MemberRepository;
import com.sixmmelie.wine.purchase.dto.PurchaseAndWineDTO;
import com.sixmmelie.wine.purchase.dto.PurchaseDTO;
import com.sixmmelie.wine.purchase.entity.Purchase;
import com.sixmmelie.wine.purchase.entity.PurchaseAndWine;
import com.sixmmelie.wine.purchase.repository.PurchaseAndWineRepository;
import com.sixmmelie.wine.purchase.repository.PurchaseRepository;
import com.sixmmelie.wine.winecellar.entity.WineEntity;
import com.sixmmelie.wine.winecellar.repository.WineRepository;

@Service
public class PurchaseService {

	private static final Logger log = LoggerFactory.getLogger(PurchaseService.class);
	
	private final MemberRepository memberRepository;
	private final PurchaseRepository purchaseRepository;
	private final WineRepository wineRepository;
	private final PurchaseAndWineRepository purchaseAndWineRepository;
	private final ModelMapper modelMapper;
	
	@Autowired
	public PurchaseService(MemberRepository memberRepository, PurchaseRepository purchaseRepository, WineRepository wineRepository, PurchaseAndWineRepository purchaseAndWineRepository, ModelMapper modelMapper) {
		this.memberRepository = memberRepository;
		this.purchaseRepository = purchaseRepository;
		this.modelMapper = modelMapper;
		this.wineRepository = wineRepository;
		this.purchaseAndWineRepository = purchaseAndWineRepository;
	}

	@Transactional
	public Object insertProduct(PurchaseDTO purchaseDTO) {
		log.info("[OrderService] insertPurchase Start ==============================");
        log.info("[OrderService] purchaseDTO : " + purchaseDTO);

        int result = 0;
        
        try {
        	/* 해당 주문 회원 pk값 조회 */
        	int memberCode = memberRepository.findMemberNoByMemberId(purchaseDTO.getMemberId());
    
	        /* 주문 insert(주문건 저장을 위한 Order 엔티티 설정) */
	        Purchase purchase = new Purchase(); 
	        purchase.setWineCode(purchaseDTO.getWineCode());
	        purchase.setOrderMember(memberCode);
	        purchase.setOrderPhone(purchaseDTO.getOrderPhone());
	        purchase.setOrderAddress(purchaseDTO.getOrderAddress());
	        purchase.setOrderAmount(purchaseDTO.getOrderAmount());
    		
    		java.util.Date now = new java.util.Date();
    		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    		String orderDate = sdf.format(now);
    		purchase.setOrderDate(orderDate);
	        
	        purchaseRepository.save(purchase);
	        
	        /* 상품 재고 update */
	        WineEntity wine = wineRepository.findById(Integer.valueOf(purchase.getWineCode())).get();
	        wine.setWineStock(wine.getWineStock() - purchaseDTO.getOrderAmount());
        		
        	result = 1;
        } catch (Exception e) {
        	log.info("[order] Exception!!");
        }
        
        log.info("[OrderService] insertPurchase End ==============================");
        return (result > 0) ? "주문 성공" : "주문 실패";
	}

	public Object selectPurchaseList(String memberId) {
		log.info("[OrderService] selectPurchaseList Start ==============================");
		
		int memberNo = memberRepository.findMemberNoByMemberId(memberId);
		
        List<PurchaseAndWine> orderList = purchaseAndWineRepository.findByOrderMember(memberNo);
//        log.info("[OrderService] purchaseList {}", orderList);

        log.info("[OrderService] selectPurchaseList End ==============================");
        
        return orderList.stream().map(purchase -> modelMapper.map(purchase, PurchaseAndWineDTO.class)).collect(Collectors.toList());
	}

}
