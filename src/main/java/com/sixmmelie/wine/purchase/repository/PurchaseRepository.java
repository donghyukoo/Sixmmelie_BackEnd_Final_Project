package com.sixmmelie.wine.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sixmmelie.wine.purchase.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer>{

}
