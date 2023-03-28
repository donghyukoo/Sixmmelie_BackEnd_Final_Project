package com.sixmmelie.wine.winecellar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sixmmelie.wine.winecellar.entity.WineEntity;

public interface WineRepository extends JpaRepository<WineEntity, Integer>{

	List<WineEntity> findByCategoryCode(int categoryCode);
	List<WineEntity> findByWineNameKoContaining(String search);
	Page<WineEntity> findByCategoryCode(int i, Pageable paging);
	List<WineEntity> findByWineSales(int wineSales);
	Page<WineEntity> findByWineNameKoContaining(String search, Pageable paging);
	
	@Query(value ="SELECT w FROM WineEntity w "
			+ "WHERE w.nationCode = ?1 AND w.winePrice = ?2 AND w.alcoholLevel = ?3")
	public List<WineEntity> selectAll(int nationCode, int winePrice, double alcoholLevel);
	
}
