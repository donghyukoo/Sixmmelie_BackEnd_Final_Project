package com.sixmmelie.wine.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sixmmelie.wine.member.entity.MemberRole;
import com.sixmmelie.wine.member.entity.MemberRolePk;

public interface MemberRoleRepository extends JpaRepository<MemberRole, MemberRolePk> {

}
