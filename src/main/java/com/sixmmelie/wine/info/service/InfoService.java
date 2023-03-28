package com.sixmmelie.wine.info.service;

import java.io.IOException;
import java.util.ArrayList;
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
import com.sixmmelie.wine.info.dto.InfoAndInfoMemberDTO;
import com.sixmmelie.wine.info.dto.InformationDTO;
import com.sixmmelie.wine.info.entity.InfoAndInfoMember;
import com.sixmmelie.wine.info.entity.Information;
import com.sixmmelie.wine.info.repository.InfoAndInfoMemberRepository;
import com.sixmmelie.wine.info.repository.InformationRepository;
import com.sixmmelie.wine.util.FileUploadUtils;

@Service
public class InfoService {

	
	private static final Logger log = LoggerFactory.getLogger(InfoService.class);
	private final InformationRepository informationRepository;
	private final InfoAndInfoMemberRepository infoAndInfoMemberRepository;
	private final ModelMapper modelMapper;
	
	@Value("${image.image-dir}")
	private String IMAGE_DIR;
	@Value("${image.image-url}")
	private String IMAGE_URL;
	
	@Autowired
	public InfoService(InformationRepository informationRepository
			         , ModelMapper modelMapper
			         , InfoAndInfoMemberRepository infoAndInfoMemberRepository) {
		this.informationRepository = informationRepository;
		this.infoAndInfoMemberRepository = infoAndInfoMemberRepository;
		this.modelMapper = modelMapper;
	}
	
	public int selectInfoTotal() {
        log.info("[InfoService] selectInfoTotal Start ===================================");
        
        /* 페이징 처리 결과를 Page 타입으로 반환받음 */
        List<InfoAndInfoMember> infoList = infoAndInfoMemberRepository.findAll();
        
        log.info("[InfoService] selectInfoTotal End ===================================");
        
        return infoList.size();
    }

	public Object selectInfoListWithPaging(Criteria cri) {
		log.info("[InfoService] selectInfoListWithPaging Start ===================================");
		
		int index = cri.getPageNum() -1;
        int count = cri.getAmount(); 
        Pageable paging = PageRequest.of(index, count, Sort.by("infoNo").descending());
	        
        Page<InfoAndInfoMember> result = infoAndInfoMemberRepository.findAll(paging);
        List<InfoAndInfoMember> infoList = (List<InfoAndInfoMember>)result.getContent();
        
        for(int i = 0 ; i < infoList.size() ; i++) {
            infoList.get(i).setInfoImg(IMAGE_URL + "infoimgs/" + infoList.get(i).getInfoImg());
        }
        
        log.info("[InfoService] selectInfoListWithPaging End ===================================");
        
        return infoList.stream().map(information -> modelMapper.map(information, InfoAndInfoMemberDTO.class)).collect(Collectors.toList());
	}

	public Object selectInfoDetail(int infoNo) {
		log.info("[InfoService] selectInfoDetail Start ===================================");
		 
		InfoAndInfoMember InfoMem = infoAndInfoMemberRepository.findById(infoNo).get();
		InfoMem.setInfoImg(IMAGE_URL + "infoimgs/" + InfoMem.getInfoImg());
	     
	    log.info("[InfoService] selectInfoDetail End ===================================");
	     
	    return modelMapper.map(InfoMem, InfoAndInfoMember.class);
	}
	
	/*검색용*/
	public int selectSearchInfoList( String search) {
		log.info("[InfoService] selectSearchInfoList Start ===================================");
        
        /* 페이징 처리 결과를 Page 타입으로 반환받음 */
        List<InfoAndInfoMember> infoList = infoAndInfoMemberRepository.findByInfoTitleContaining(search); 
        log.info("[InfoService] selectSearchInfoList result cnt ===>>"+infoList.size());
        log.info("[InfoService] selectSearchInfoList End ===================================");
        
        return infoList.size();
	}
	
	public Object selectSearchInfoListWithPaging(Criteria cri, String search) {
		log.info("[InfoService] selectSearchInfoList Start ===================================");
        log.info("[InfoService] searchValue : " + search);
//        List<InfoAndInfoMember> infoListWithSearchValue = infoAndInfoMemberRepository.findByInfoTitleContaining(cri, Search);
        
        int index = cri.getPageNum() - 1;
        int count = cri.getAmount(); 
        Pageable paging = PageRequest.of(index, count, Sort.by("infoNo").descending());
        
        Page<InfoAndInfoMember> result = null;
	    if(StringUtils.equals(search, "all")) {
	    	result = infoAndInfoMemberRepository.findAll(paging);
	    }else {
	    	result = infoAndInfoMemberRepository.findByInfoTitleContaining( search, paging);
	    }
        List<InfoAndInfoMember> infoList = (List<InfoAndInfoMember>)result.getContent();
        
        log.info("[InfoService] result??================"+result);

        for(int i = 0 ; i < infoList.size() ; i++) {
        	log.info("[InfoService] infoListWithSearchValue : " + infoList.get(i));
        	infoList.get(i).setInfoImg(IMAGE_URL + "infoimgs/" + infoList.get(i).getInfoImg());
        }
        
        log.info("[InfoService] selectSearchInfoList End ===================================");

        return infoList.stream().map(information -> modelMapper.map(information, InfoAndInfoMemberDTO.class)).collect(Collectors.toList());
	}
	

	public Object updateInfo(InformationDTO informationDTO, MultipartFile infoImage) {
		log.info("[InfoService] updateInfo Start ===================================");
        log.info("[InfoService] informationDTO : " + informationDTO);
        
        String replaceFileName = null;
        int result = 0;

        try {
        	
        	/* update 할 엔티티 조회 */
        	Information Information = informationRepository.findById(informationDTO.getInfoNo()).get();
        	String oriImage = Information.getInfoImg();
            log.info("[updateInfo] oriImage : " + oriImage);
            
            /* update를 위한 엔티티 값 수정 */
            Information.setInfoNo(informationDTO.getInfoNo());
            Information.setInfoDetail(informationDTO.getInfoDetail());
            //Information.setInfoImg(informationDTO.getInfoImg());
            Information.setInfoTitle(informationDTO.getInfoTitle());
            //Information.setInfoDate(informationDTO.getInfoDate());
            //Information.setMemberNo(informationDTO.getMemberNo());
           
            
            if( infoImage != null){
                String imageName = UUID.randomUUID().toString().replace("-", "");
                replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR+"/infoimgs", imageName, infoImage);
                log.info("[updateInfo] InsertFileName : " + replaceFileName);
                
                Information.setInfoImg(replaceFileName);	// 새로운 파일 이름으로 update
                log.info("[updateInfo] deleteImage : " + oriImage);
                
                boolean isDelete = FileUploadUtils.deleteFile(IMAGE_DIR+"/infoimgs", oriImage);
                log.info("[update] isDelete : " + isDelete);
            } else {
            	
                /* 이미지 변경 없을 시 */
            	Information.setInfoImg(oriImage);
            }
            log.info("[update] Information data : " + Information);
            informationRepository.save(Information);
            result = 1;
        } catch (IOException e) {
            log.info("[updateInfo] Exception!!");
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }
        log.info("[InfoService] updateInfo End ===================================");
        return (result > 0) ? "상품 업데이트 성공" : "상품 업데이트 실패";
	}

	public Object insertInfo(InformationDTO informationDTO, MultipartFile infoImage) {
		log.info("[InfoService] insertInfo Start ===================================");
        log.info("[InfoService] insertInfo : " + informationDTO);
        
        String imageName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;
        int result = 0;

        try {
        	if(StringUtils.isNotEmpty(infoImage.getName())) {
        		/* util 패키지에 FileUploadUtils 추가 */
        		replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR+"/infoimgs", imageName, infoImage);
        		
        		informationDTO.setInfoImg(replaceFileName);
        		
        		log.info("[ProductService] insert Image Name : ", replaceFileName);
        	}
            Information insertInfo = modelMapper.map(informationDTO, Information.class);
            informationRepository.save(insertInfo);
            result = 1;
        } catch (Exception e) {
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }
        return (result > 0) ? "상품 입력 성공" : "상품 입력 실패";
	}
	
}
