package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.repository.RoleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleTypeServiceImpl implements RoleTypeService {

    private RoleTypeRepository roleTypeRepository;

    @Autowired
    public RoleTypeServiceImpl(RoleTypeRepository roleTypeRepository) {
        this.roleTypeRepository = roleTypeRepository;
    }

    @Override
    public RoleType findRoleTypeByRoleType(String roleType) {
        return roleTypeRepository.findRoleTypeByRoleType(roleType);
    }
}
