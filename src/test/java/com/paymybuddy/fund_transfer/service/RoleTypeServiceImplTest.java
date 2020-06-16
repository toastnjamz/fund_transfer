package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.repository.RoleTypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleTypeServiceImplTest {

    @Mock
    private RoleTypeRepository roleTypeRepositoryMock;

    @InjectMocks
    private RoleTypeServiceImpl roleTypeServiceImpl;

    @Test
    public void findRoleTypeByRoleType_roleTypeExists_roleTypeReturned() {
        //arrange
        RoleType roleType = new RoleType("Regular");

        when(roleTypeRepositoryMock.findRoleTypeByRoleType(roleType.getRoleType())).thenReturn(roleType);

        //act
        RoleType result = roleTypeServiceImpl.findRoleTypeByRoleType(roleType.getRoleType());

        //assert
        assertEquals(roleType.getRoleType(), result.getRoleType());
    }

    @Test
    public void findRoleTypeByRoleType_roleTypeDoesNotExist_nullReturned() {
        //arrange

        //act
        RoleType result = roleTypeServiceImpl.findRoleTypeByRoleType("Admin");

        //assert
        assertNull(result);
    }
}