package com.sixmmelie.wine.member.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sixmmelie.wine.exception.DupilcatedEmailException;
import com.sixmmelie.wine.exception.DupilcatedIdException;
import com.sixmmelie.wine.exception.LoginFailedException;
import com.sixmmelie.wine.jwt.TokenProvider;
import com.sixmmelie.wine.member.dto.MemberDTO;
import com.sixmmelie.wine.member.dto.TokenDTO;
import com.sixmmelie.wine.member.entity.Member;
import com.sixmmelie.wine.member.entity.MemberRole;
import com.sixmmelie.wine.member.repository.MemberRepository;
import com.sixmmelie.wine.member.repository.MemberRoleRepository;

@Service
public class AuthService {
	
	private static final Logger log = LoggerFactory.getLogger(AuthService.class);
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final ModelMapper modelMapper;
	private final MemberRoleRepository memberRoleRepository;
	
	@Autowired
	public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder
						, TokenProvider tokenProvider, ModelMapper modelMapper
						, MemberRoleRepository memberRoleRepository) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.modelMapper = modelMapper;
		this.memberRoleRepository = memberRoleRepository;
	}
	
	
	public Object login(MemberDTO memberDTO) { // @service를 어노테이션명시적으로 작성해줘서 bean으로 등록해줘야함 그래야 authController에서 사용가능함!
		log.info("[AuthService] Login Start =======================================");
		log.info("[AuthService] {} ", memberDTO);
//		log.info("[AuthService] " +  memberDTO);	이렇게 작성할걸 {}를 사용해서 할 수 있다. 성능향상에 좋음
		
		
		/* 1. 아이디 조회 (DB와 대조해서 일치하는지 확인) */
		/* 레파지토리는 쿼리메소드를 사용할 수 있는 DAO역할을 하는 인터페이스이다. dml등 작업을 하고 Member엔티티에 작업내용이 담긴다.*/
		/* 이 부분은 사용자가 입력한 ID가 틀렸을때 예외처리를 해주기 위해 작성한 것이다.*/
		Member member = memberRepository.findByMemberId(memberDTO.getMemberId());
		
		if(member == null) {
			throw new LoginFailedException(memberDTO.getMemberId() + "를 찾을 수 없습니다.");
		}
		
		/* 2. 비밀번호 매칭(BCrypt 암호화 라이브러리 bean을 의존성 주입을 받아 처리하는 부분부터 security 설정 부분을 추가해보자) */
		/* matches(왼쪽에 평문, 오른쪽이 암호화처리된 다이제스트를 작성해야 한다.) */
		/* 여기에 작성하는 구문은 사용자가 입력한 비번이 맞지 않을때 예외처리를 해주기 위해 작성한 것이다.*/
		if(!passwordEncoder.matches(memberDTO.getMemberPw(), member.getMemberPw())) {
			log.info("[AuthService] Password Match Fail @@@@");
			throw new LoginFailedException("잘못된 비밀번호 입니다.");
		}
		
		
		/* 3. 토큰 발급 */
		TokenDTO tokenDTO = tokenProvider.generateTokenDTO(member);
		log.info("[AuthService] tokenDTO {}", tokenDTO);
		
		log.info("[AuthService] Login End =======================================");
		return tokenDTO;
	}


	/* ====================================== 여기부터가 회원가입 로직 ================================================*/

	@Transactional		// DML 작업은 Transactional 어노테이션을 추가해야한다.
	public MemberDTO signup(MemberDTO memberDTO) {
		log.info("[AuthService] Signup Start======================");
		log.info("[AuthService] memberDTO{}", memberDTO);
		
		/* 
		 	회원가입은 INSERT 작업시에 2번을 해야한다. 회원테이블에 한번 회원별 권한 테이블에 한번. 
		  	가입한게 관리자라면 회원별 권한에 총2번 인서트해야한다. 회원과 관리자 권한을 가저야 하므로.
		 */
		
		/* 이메일 중복 유효성 검사 / 화면단에서도 가능하지만 뺵단에서 하는 예시(선택적)  이메일이 ID로 사용되는경우도 있으므로 이메일로함 */
		if(memberRepository.findByMemberEmail(memberDTO.getMemberEmail()) != null) {
			log.info("[AuthService] 이메일이 중복됩니다.");
			throw new DupilcatedEmailException("이메일이 중복됩니다.");	// 익셉션을 디테일하게 뿌린이유는 프로젝트를 진행하기전에
																		// 예외상황처리를 모두 정의한 뒤 프로젝트를 진행하는게 일반적이기 때문이다.
		}
		
		/* 아이디 중복 유효성 검사 */
		if(memberRepository.findByMemberId(memberDTO.getMemberId()) != null) {
			log.info("[AuthService] 아이디가 중복됩니다.");
			throw new DupilcatedIdException("아이디가 중복됩니다.");
		}
		
		
		/* 우선 repository를 통해 쿼리를 날리기 전에 DTO에 담긴 값을 Entity로 변환하자.(Service는 엔티티와 DTO의 교차지점) */
		Member registMember = modelMapper.map(memberDTO, Member.class);
		
		
		/* 1. TBL_MEMBER 테이블에 INSERT */
		/* 비밀번호 암호화 */
		registMember.setMemberPw(passwordEncoder.encode(registMember.getMemberPw()));;
		Member result1 = memberRepository.save(registMember); // 반환형이 int값이 아니다. 성공하면 member를 반환 실패하면 null을 반환
		
		/* 2. TBL_MEMBER_ROLE 테이블에 회원별 권한 INSERT(주의사항: 현재 엔티티에는 회원가입 후 pk값이 없다) */
		/* 트랜잭션단위는 전부다 끝난뒤에 커밋하는 개념이기때문에 save를 작성했다해서 pk값이 있는건 아니다.(커밋이 아직 안된 상태이기 때문에) */
		/* 2.1 일반권한의 회원을 추가 (AuthorityCode값이 2번 )*/
		/* 
		   2-2. 엔티티에는 추가 할 회원의 pk값이 아직 없으므로 기존 회원의 마지막 회원 번호를 조회해서 + 1 한 값을 조회한다.
		   (하지만 jpql에 의해 앞선 save(인서트)와 jpql이 flush()로 쿼리와 함께 날아가고 회원이 이미 sequence객체 값
		   증가와 함께 insert가 돼버린다. -> 결론은, maxMemberCode가 현재 가입하는 회원의 번호이다.)
		 */
		
		int maxMemberCode = memberRepository.maxMemberNo();		// 등록되어있는 마지막회원 번호를 추출
																	// jpql을 활용해서 회원번호 max값 추출
		// jsql은 반드시 플러시를 발동시킨다. 그렇기 때문에 jpa 동작이랑 다르게 모든작업이 처리되고 커밋이되는게 아닌 jpql이 사이에 껴있으면 중간에 플러시가되어 날아가게된다.(이렇게되면 회원번호가 꼬여버린다.)
 		
		MemberRole registMemberRole = new MemberRole(maxMemberCode, 2 );
//		MemberRole registMemberRole = new MemberRole(maxMemberCode + 1 ,2);		// 뒤에 2번은 권한코드이다.
	
		MemberRole result2 = memberRoleRepository.save(registMemberRole);
		
		log.info("[AuthService] Member Insert Result{}", (result1 != null && result2 != null) ? "회원 가입 성공" : "회원 가입 실패");
		
		log.info("[AuthService] Signup End======================");
		return memberDTO;
	}		
}
