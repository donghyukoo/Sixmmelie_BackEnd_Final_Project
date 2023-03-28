package com.sixmmelie.wine.jwt;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.sixmmelie.wine.exception.TokenException;
import com.sixmmelie.wine.member.dto.TokenDTO;
import com.sixmmelie.wine.member.entity.Member;
import com.sixmmelie.wine.member.entity.MemberRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/* 토큰 생성, 토큰 인증(Authentication 객체 반환), 토큰 유효성 검사 이 3가지를 하는게 TokenProvider에서 하는 일*/
@Component
public class TokenProvider {

	/* 클레임: key와 value의 쌍 즉, 엔트리를 뜻함 */
	
	private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);
	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_TYPE = "bearer";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분(ms 단위)
	
	/* 세션방식과는 다르게 토큰방식은 유저디테일 서비스를 확장하지않고 그대로 사용한다. */
	private final UserDetailsService userDetailsService;
	private final Key key;		// java.security.Key로 임포트 할것
	
	public TokenProvider(@Value("${jwt.secret}")String secretKey,
								UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
}
	
	/* 토큰생성을 위한 메소드 (DB에서 데이터를 가져오는 구간) */
	public TokenDTO generateTokenDTO(Member member) {	// 여기까지 작성함.
		
		log.info("[TokenProvider] generateTokenDTO Start ========================");
		log.info("[TokenProvider] Member {}", member);
		
		List<String> roles = new ArrayList<>();
		for(MemberRole memberRole : member.getMemberRole()) {
			roles.add(memberRole.getAuthority().getAuthorityName());
		}
		
		log.info("[TokenProvider] {}", roles);		// SLF4J에서 제공하는 치환문자 활용(+(덧셈)같은 연산처리 작업 생략)
		
		/* 회원 아이디를 "sub"이라는 클레임으로 토큰에 추가 추가할 부분이 있다면 여기에 추가 이름도 여기로 추가하나? */
		Claims claims = Jwts.claims().setSubject(member.getMemberId());
		
		/* 회원의 권한들을 "auth"라는 클레임으로 토큰에 추가 추가할 부분이 있다면 여기에 추가 */
		claims.put(AUTHORITIES_KEY, roles); 			// 비공개클레임(우리가만든클레임)이기 때문에 put으로 넣어줬다.
//		claims.put("auth", roles);
		
		long now = System.currentTimeMillis();
		
		Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);	// util.Date로 import하기
		String accessToken = Jwts.builder()
								 .setClaims(claims)
								 
								 /* 토큰의 만료 기간을 DATE형으로 토큰에 추가("exp"라는 클레임으로 long 형으로 토큰에 추가) */
								 .setExpiration(accessTokenExpiresIn)
								 .signWith(key, SignatureAlgorithm.HS512)
								 .compact();
		
		log.info("[TokenProvider]  generateTokenDTO End ========================");
		
		return new TokenDTO(BEARER_TYPE, member.getMemberName(), accessToken,	// DTO는 member패키지에 TokenDTO를 만듬
							 accessTokenExpiresIn.getTime());
	}
	
	/* 2. 토큰의 등록된 클레임의 subjec에서 해당 회원의 아이디를 추출 */
	public String getUserId(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key).build()
				.parseClaimsJws(token)
				.getBody()					// payload Claims 추출
				.getSubject();				// Claim중에 등록 클렘이에 해당하는 sub값 추출(회원 아이디)
	}
	
	/* 3. AccessToken으로 인증 객체 추출  (이 클래스의 5번과 2번에 해당하는 메소드를 사용)*/
	public Authentication getAuthentication(String token) {
		
		log.info("[TokenProvider] getAuthentication Start ========================");
		
		/* 토큰에서 claim들을 추출( 토큰 복호화)*/
		Claims claims = parseClaims(token);		//아래 5번에서 만든 메소드 5번을 활용하여 3번을 만든다.

		if(claims.get(AUTHORITIES_KEY) == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}
		
		/* 클레임에서 권한정보 가져오기 권한들을 인지시키는 단계 */
		Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))	// 권한들이 들어있는 String 배열에서 toString과 split을 사용하여 파싱 
																					// ex:"ROLE_ADIN","ROLE_USER"같은 문자열  
				.map(role -> new SimpleGrantedAuthority(role))						// 문자열 배열에 들어있는 권한 문자열 마다 SimpleGrantedAuthority객체로 만듦
				.collect(Collectors.toList());										// List<SimpleGrantedAuthority>로 만들었다.
		log.info("[TokenProvider] authorities {}", authorities);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
		
		log.info("생성된 토큰의 회원 아이디 {}", this.getUserId(token));
		
		log.info("[TokenProvider] getAuthentication End ========================");
		
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
				
	}
	/* 4. 토큰 유효성 검사 여기가 jwtfilter 클래스에 if문에있는 StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) 이쪽부분 즉 토큰 유효성검사*/
	public boolean validateToken(String token) {		
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
			throw new TokenException("잘못된 JWT 서명 입니다.");
		} catch (ExpiredJwtException e){
			log.info("[TokenProvider] 만료된 JWT 서명입니다.");
			throw new TokenException("만료된 JWT 서명 입니다.");
		} catch(UnsupportedJwtException e) {
			log.info("[TokenProvider] 지원되지 않는 JWT 서명입니다.");
			throw new TokenException("지원되지 않는 JWT 서명 입니다.");
		} catch(IllegalArgumentException e) {
			log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
			throw new TokenException("JWT 토큰이 잘못되었습니다.");
		}
	}
	/* 5. AccessToken에서 클레임 추출하는 메소드 작성 */
	private Claims parseClaims(String token) {
		try {
			
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();			// 토큰이 만료되어 예외가 발생하더라도 클레임 값들은 뽑아낼 수 있다.
		}
	}
	
	
	
	
}
