package com.sixmmelie.wine.info.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sixmmelie.wine.info.entity.Information;

public interface InformationRepository extends JpaRepository<Information, Integer>{

	List<Information> findAll();
	
	Page<Information> findAll(Pageable paging);
	
//	List<Information> findByInfoTitleContaining(String search);
	

}
