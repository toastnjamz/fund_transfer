package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleTypeRepository extends JpaRepository<RoleType, Integer> {

    RoleType findRoleTypeByRoleType(String roleType);
}
