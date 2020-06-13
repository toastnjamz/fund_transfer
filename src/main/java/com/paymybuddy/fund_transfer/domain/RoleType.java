package com.paymybuddy.fund_transfer.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role_type", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class RoleType {

    @Id
    private int id;

    //Role types: 1 - Admin, 2 - Regular
    private String roleType;

    public RoleType() { }

    public RoleType(String roleType) {
        this.roleType = roleType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleType)) return false;
        RoleType roleType = (RoleType) o;
        return id == roleType.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
