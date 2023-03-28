package com.sixmmelie.wine.winecellar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sixmmelie.wine.common.Criteria;
import com.sixmmelie.wine.winecellar.dto.WineDTO;
import com.sixmmelie.wine.winecellar.entity.WineEntity;
import com.sixmmelie.wine.winecellar.repository.WineAndCategoryRepository;
import com.sixmmelie.wine.winecellar.repository.WineRepository;

@Service
public class WineService {
	
	private static final Logger log = LoggerFactory.getLogger(WineService.class);

	private final WineRepository wineRepository;
	private final WineAndCategoryRepository wineAndCategoryRepository;
	private final ModelMapper modelMapper;
	
	/* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;
    
    @Autowired
    public WineService(WineRepository wineRepository, ModelMapper modelMapper, WineAndCategoryRepository wineAndCategoryRepository) {
		this.wineRepository = wineRepository;
		this.wineAndCategoryRepository = wineAndCategoryRepository;
		this.modelMapper = modelMapper;	
    }

	// 와인 전체 조회/판매량
	public int selectWineSalesTotal() {
		List<WineEntity> wineSalesList = wineRepository.findAll();
		
		return wineSalesList.size();
	}

	// 와인 전체 페이징/판매량
	public List<WineDTO> selectWineSalesAboutMain(Criteria cri) {
		
		int index = cri.getPageNum() - 1;
		int count = cri.getAmount();
		Pageable paging = PageRequest.of(index, count, Sort.by("wineSales").descending());
		
		Page<WineEntity> result = wineRepository.findAll(paging);
		List<WineEntity> wineSalesList = (List<WineEntity>)result.getContent();
		
		for(int i = 0; i < wineSalesList.size(); i++) {
			wineSalesList.get(i).setWineImg(IMAGE_URL + "wineimgs/" + wineSalesList.get(i).getWineImg());
		}
	
		return wineSalesList.stream().map(wineEntity -> 
				modelMapper.map(wineEntity, WineDTO.class)).collect(Collectors.toList());
	}

	// 서베이로 와인 상세 조회
			public Object selectAll(int nationCode, int winePrice, double alcoholLevel) {
				List<WineEntity> survey = wineRepository.selectAll(nationCode, winePrice, alcoholLevel);
				log.info("[wineService] selectAll {} ", survey);

				return survey;
	}
}

