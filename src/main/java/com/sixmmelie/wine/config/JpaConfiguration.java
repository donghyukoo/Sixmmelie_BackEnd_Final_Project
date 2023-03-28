package com.sixmmelie.wine.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*컴포넌트 스캔은 설정파일들을 따로 관리하기위해 패키지를 만들었기때문에 인지할수있도록 스캔 어노테이션을 달아줌*/
/* bean은 entity와 repository는 인지하지않는다 별개임. 그래서 따로 만들어줘야한다.(JpaConfiguration)*/

/* 엔티티 및 레포지토리 인터페이스를 인식 시켜주기 위해 반드시 필요한 설정이다. 꼭 미리 만들어주자*/
@Configuration
@EntityScan(basePackages = {"com.sixmmelie.wine"}) //  인지 할 엔티티 범위 중괄호를 작성하면 여러 패키지를 인식시켜줄수있다. 
@EnableJpaRepositories(basePackages = "com.sixmmelie.wine") //  인지 할 레포지토리 범위 중괄호를 안적으면 하나만 작성할 수 있다.
public class JpaConfiguration {

}
