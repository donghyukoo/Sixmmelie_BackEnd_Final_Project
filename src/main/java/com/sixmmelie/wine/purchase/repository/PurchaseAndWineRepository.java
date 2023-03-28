package com.sixmmelie.wine.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sixmmelie.wine.purchase.entity.PurchaseAndWine;

public interface PurchaseAndWineRepository extends JpaRepository<PurchaseAndWine, Integer>{

	List<PurchaseAndWine> findByOrderMember(int memberNo);

}
