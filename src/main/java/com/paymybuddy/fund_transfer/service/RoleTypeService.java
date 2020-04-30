package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.RoleType;

import java.util.List;

public interface RoleTypeService {

    public List<RoleType> findAllRoleTypes();

    public RoleType findRoleTypeById(int id);

    public RoleType findRoleTypeByRoleType(String roleType);

    public RoleType createRoleType(RoleType roleType);

    public void updateRoleType(RoleType roleType);

    public void deleteRoleType(int id);
}
