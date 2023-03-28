package com.sixmmelie.wine.winecellar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sixmmelie.wine.winecellar.entity.WineAndCategoryEntity;

public interface WineAndCategoryRepository extends JpaRepository<WineAndCategoryEntity, Integer>{

}
