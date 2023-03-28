package com.sixmmelie.wine.info.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sixmmelie.wine.common.Criteria;
import com.sixmmelie.wine.info.entity.InfoAndInfoMember;

@EnableJpaRepositories
public interface InfoAndInfoMemberRepository extends JpaRepository<InfoAndInfoMember, Integer>{
	Page<InfoAndInfoMember> findByInfoTitleContaining(String search, Pageable paging);

	List<InfoAndInfoMember> findByInfoTitleContaining(String search);
}
