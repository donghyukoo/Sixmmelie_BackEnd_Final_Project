package com.sixmmelie.wine.winecellar.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.sixmmelie.wine.common.Criteria;
import com.sixmmelie.wine.util.FileUploadUtils;
import com.sixmmelie.wine.winecellar.dto.WineDTO;
import com.sixmmelie.wine.winecellar.entity.WineEntity;
import com.sixmmelie.wine.winecellar.repository.WineAndCategoryRepository;
import com.sixmmelie.wine.winecellar.repository.WineRepository;

@Service
public class WinesService {
	
	private static final Logger log = LoggerFactory.getLogger(WinesService.class);

	private final WineRepository wineRepository;
	private final WineAndCategoryRepository wineAndCategoryRepository;
	private final ModelMapper modelMapper;
	
	/* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;
    
    @Autowired
    public WinesService(WineRepository wineRepository, ModelMapper modelMapper, WineAndCategoryRepository wineAndCategoryRepository) {
		this.wineRepository = wineRepository;
		this.wineAndCategoryRepository = wineAndCategoryRepository;
		this.modelMapper = modelMapper;	
    }

    /* 와인 전체 조회 */
	/* 와인 전체 페이지 */
	public int selectWineTotal() {
//		log.info("[WineService] selectWineTotal Start ========");
		
		List<WineEntity> wineList = wineRepository.findAll();
		
//		log.info("[WineService] selectWineTotal {}", wineList);
//		log.info("[WineService] selectWineTotal End ========");
		
		return wineList.size();
	}
	
	/* 레드 와인 전체 페이지 */
	public int selectRedWineTotal() {
		log.info("[WineService] selectRedWineTotal Start ========");
		
		List<WineEntity> wineList = wineRepository.findByCategoryCode(1);
		
//		log.info("[WineService] selectRedWineTotal {}", wineList);
		log.info("[WineService] selectRedWineTotal End ========");
		
		return wineList.size();
	}
	
	/* 화이트 와인 전체 페이지 */
	public int selectWhiteWineTotal() {
		log.info("[WineService] selectWhiteWineTotal Start ========");
		
		List<WineEntity> wineList = wineRepository.findByCategoryCode(2);
		
//		log.info("[WineService] selectWhiteWineTotal {}", wineList);
		log.info("[WineService] selectWhiteWineTotal End ========");
		
		return wineList.size();
	}
	
	/* 로제 와인 전체 페이지 */
	public int selectRoseWineTotal() {
		log.info("[WineService] selectRoseWineTotal Start ========");
		
		List<WineEntity> wineList = wineRepository.findByCategoryCode(3);
		
//		log.info("[WineService] selectRoseWineTotal {}", wineList);
		log.info("[WineService] selectRoseWineTotal End ========");
		
		return wineList.size();
	}
	
	/* 스파클링 와인 전체 페이지 */
	public int selectSparklingWineTotal() {
		log.info("[WineService] selectRoseWineTotal Start ========");
		
		List<WineEntity> wineList = wineRepository.findByCategoryCode(4);
		
//		log.info("[WineService] selectRoseWineTotal {}", wineList);
		log.info("[WineService] selectRoseWineTotal End ========");
		
		return wineList.size();
	}

	/* 전체 와인 페이징처리 */
	public Object selectWineListWithPaging(Criteria cri) {
//		log.info("[WineService] selectWineListWithPaging Start ========");
		
		int index = cri.getPageNum() - 1;
		int count = cri.getAmount();
		Pageable paging = PageRequest.of(index, count, Sort.by("wineCode").descending());
		
		Page<WineEntity> result = wineRepository.findAll(paging);
//		log.info("[WineService] selectWineListWithPaging(result) {}", result);		// Page 1 of 19 containing com.sixmmelie.wine.winecellar.entity.WineEntity instances
		
		List<WineEntity> wineList = (List<WineEntity>)result.getContent();
//		log.info("[WineService] selectWineListWithPaging(wineList) {}", wineList);	// [WineEntity [wineCode=367, wineNameKo=알마비바 레드 2016, wineNameEn=Almaviva Red 2016, wineAre...
		
		for(int i = 0; i < wineList.size(); i++) {
			wineList.get(i).setWineImg(IMAGE_URL + "wineimgs/" + wineList.get(i).getWineImg());
		}
		
//		log.info("[WineService] selectWineListWithPaging End ========");
		
		return wineList.stream().map(wineEntity -> modelMapper.map(wineEntity, WineDTO.class)).collect(Collectors.toList());	
	}
	
	/* 와인 상세 조회 */
	public Object selectWine(int wineCode) {
		log.info("[WineService] selectWine Start ========");
		
		WineEntity wine = wineRepository.findById(wineCode).get();
		wine.setWineImg(IMAGE_URL  + "wineimgs/" +  wine.getWineImg());
		
		
//		log.info("wineCode {}", wine);
		log.info("[WineService] selectWine End ========");
		
		return modelMapper.map(wine, WineEntity.class);
	}

	/* 레드 와인 리스트 페이징처리 */
	public Object selectWineListAboutRed(Criteria cri) {
		log.info("[WineService] selectWineListAboutRed Start =========");
		
		int index = cri.getPageNum() - 1;
		int count = cri.getAmount();
		Pageable paging = PageRequest.of(index, count, Sort.by("wineCode").descending());
		
		Page<WineEntity> result = wineRepository.findByCategoryCode(1, paging);
		
		List<WineEntity> redList = (List<WineEntity>)result.getContent();
		
		for(int i = 0; i < redList.size(); i++) {
			redList.get(i).setWineImg(IMAGE_URL + "wineimgs/" + redList.get(i).getWineImg());
		}
		
		log.info("[WineService] selectWineListAboutRed End =========");
		return redList.stream().map(wineEntity -> modelMapper.map(wineEntity, WineDTO.class)).collect(Collectors.toList());
	}

	/* 화이트 와인 리스트 페이징처리 */
	public Object selectWineListAboutWhite(Criteria cri) {
		log.info("[WineService] selectWineListAboutWhite Start =========");
		
		int index = cri.getPageNum() - 1;
		int count = cri.getAmount();
		Pageable paging = PageRequest.of(index, count, Sort.by("wineCode").descending());
		
		Page<WineEntity> result = wineRepository.findByCategoryCode(2, paging);
		
		List<WineEntity> whiteList = (List<WineEntity>)result.getContent();
		
		for(int i = 0; i < whiteList.size(); i++) {
			whiteList.get(i).setWineImg(IMAGE_URL + "wineimgs/" + whiteList.get(i).getWineImg());
		}
		
		log.info("[WineService] selectWineListAboutWhite End =========");
		return whiteList.stream().map(wineEntity -> modelMapper.map(wineEntity, WineDTO.class)).collect(Collectors.toList());
	}

	/* 로제 와인 리스트 페이징처리 */
	public Object selectWineListAboutRose(Criteria cri) {
		log.info("[WineService] selectWineListAboutRose Start =========");
		
		int index = cri.getPageNum() - 1;
		int count = cri.getAmount();
		Pageable paging = PageRequest.of(index, count, Sort.by("wineCode").descending());
		
		Page<WineEntity> result = wineRepository.findByCategoryCode(3, paging);
		
		List<WineEntity> roseList = (List<WineEntity>)result.getContent();
		
		for(int i = 0; i < roseList.size(); i++) {
			roseList.get(i).setWineImg(IMAGE_URL + "wineimgs/" + roseList.get(i).getWineImg());
		}
		
		log.info("[WineService] selectWineListAboutRose End =========");
		return roseList.stream().map(wineEntity -> modelMapper.map(wineEntity, WineDTO.class)).collect(Collectors.toList());
	}

	/* 스파클링 와인 리스트 페이징처리 */
	public Object selectWineListAboutSparkling(Criteria cri) {
		log.info("[WineService] selectWineListAboutSparkling Start =========");
		
		int index = cri.getPageNum() - 1;
		int count = cri.getAmount();
		Pageable paging = PageRequest.of(index, count, Sort.by("wineCode").descending());
		
		Page<WineEntity> result = wineRepository.findByCategoryCode(4, paging);
		
		List<WineEntity> sparklingList = (List<WineEntity>)result.getContent();
		
		for(int i = 0; i < sparklingList.size(); i++) {
			sparklingList.get(i).setWineImg(IMAGE_URL + "wineimgs/" + sparklingList.get(i).getWineImg());
		}
		
		log.info("[WineService] selectWineListAboutSparkling End =========");
		return sparklingList.stream().map(wineEntity -> modelMapper.map(wineEntity, WineDTO.class)).collect(Collectors.toList());
	}

	/* 와인 검색 */
	public int selectSearchWineList(String search) {
		List<WineEntity> wineList = wineRepository.findByWineNameKoContaining(search);
		return wineList.size();
	}
	
	public Object selectSearchWineListWithPaging(Criteria cri, String search) {
		log.info("[WineService] selectSearchWineListWithPaging Start ===================================");
        log.info("[WineService] searchValue : " + search);
        
        int index = cri.getPageNum() - 1;
        int count = cri.getAmount(); 
        Pageable paging = PageRequest.of(index, count, Sort.by("wineCode").descending());
        
        Page<WineEntity> result = null;
	    if(StringUtils.equals(search, "all")) {
	    	result = wineRepository.findAll(paging);
	    }else {
	    	result = wineRepository.findByWineNameKoContaining(search, paging);
	    }
        List<WineEntity> wineList = (List<WineEntity>)result.getContent();
        
        log.info("[WineService] result??================"+result);

        for(int i = 0 ; i < wineList.size() ; i++) {
        	wineList.get(i).setWineImg(IMAGE_URL + "wineimgs/" + wineList.get(i).getWineImg());
        }
        
        log.info("[WineService] selectSearchInfoList End ===================================");

        return wineList.stream().map(wine -> modelMapper.map(wine, WineDTO.class)).collect(Collectors.toList());
	}
	
	
	/* 와인 신규 등록 */
	@Transactional
	public Object insertWine(WineDTO wineDTO, MultipartFile wineImage) {
		log.info("[WineService] insertWine Start ========");
		log.info("[WineService] wineDTO: {}", wineDTO);
		
		String imageName = UUID.randomUUID().toString().replace("-", "");
		String replaceFileName = null;
		int result = 0;
		
		try {
			replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR + "/wineimgs/", imageName, wineImage);
			
			wineDTO.setWineImg(replaceFileName);
			
			log.info("[WineService] insert Image Name: {}",replaceFileName);
        	
			WineEntity insertWine = modelMapper.map(wineDTO,  WineEntity.class);
			wineRepository.save(insertWine);
			result = 1;
		} catch (Exception e) {
			FileUploadUtils.deleteFile(IMAGE_DIR + "/wineimgs/", replaceFileName);
			throw new RuntimeException(e);
		}
		log.info("[WineService] insertWine End ========");
		return (result > 0) ? "와인 등록 성공" : "와인 등록 실패";
	}

	/* 와인 수정 */
	@Transactional
	public Object updateWine(WineDTO wineDTO, MultipartFile wineImage) {
		log.info("[WineService] updateWine Start ========");
//		log.info("[WineService] wineDTO: {}", wineDTO );
		
		String replaceFileName = null;
		int result = 0;
		
		try {
			WineEntity wine = wineRepository.findById(wineDTO.getWineCode()).get();
			String oriImage = wine.getWineImg();
//			log.info("[WineService- updateWine] oriImage: {}", oriImage);
			
			/* update할 엔티티 값 수정 */
			wine.setCategoryCode(wineDTO.getCategoryCode());
			wine.setWineNameKo(wineDTO.getWineNameKo());
			wine.setWineNameEn(wineDTO.getWineNameEn());
			wine.setWineContent(wineDTO.getWineContent());
			wine.setWineArea(wineDTO.getWineArea());
			wine.setWinePrice(wineDTO.getWinePrice());
			wine.setWineStock(wineDTO.getWineStock());
			
			if(wineImage != null) {
				String imageName = UUID.randomUUID().toString().replace("-", "");
				replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, replaceFileName, wineImage);
//				log.info("[WineService- updateWine] InsertFileName: {}", replaceFileName);
				
				wine.setWineImg(replaceFileName);
//				log.info("[WineService- updateWine] deleteImg : {}", oriImage);
				
				boolean isDelete = FileUploadUtils.deleteFile(IMAGE_DIR, oriImage);
				
			} else {
				wine.setWineImg(oriImage);
			}
			result = 1;
		} catch (IOException e) {
			FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
			throw new RuntimeException(e);
		}
		
		log.info("[WineService] updateWine End ========");
		return (result > 0) ? "와인 업데이트 성공" : "와인 업데이트 실패";
	}

	public void deleteWine(int wineCode) {
		
	}

}
