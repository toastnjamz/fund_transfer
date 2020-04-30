package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.repository.RoleTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleTypeServiceImpl implements RoleTypeService {

    private RoleTypeRepository roleTypeRepository;

    public RoleTypeServiceImpl(RoleTypeRepository roleTypeRepository) {
        this.roleTypeRepository = roleTypeRepository;
    }

    @Override
    public List<RoleType> findAllRoleTypes() {
        return roleTypeRepository.findAll();
    }

    @Override
    public RoleType findRoleTypeById(int id) {
        return null;
    }

    @Override
    public RoleType findRoleTypeByRoleType(String roleType) {
        return roleTypeRepository.findRoleTypeByRoleType(roleType);
    }

    @Override
    public RoleType createRoleType(RoleType roleType) {
        return roleTypeRepository.save(roleType);
    }

    @Override
    public void updateRoleType(RoleType roleType) {
        roleTypeRepository.save(roleType);
    }

    @Override
    public void deleteRoleType(int id) {
        roleTypeRepository.deleteById(id);
    }
}