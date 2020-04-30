package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleTypeRepository extends JpaRepository<RoleType, Integer> {

    RoleType findRoleTypeByRoleType(String roleType);
}
