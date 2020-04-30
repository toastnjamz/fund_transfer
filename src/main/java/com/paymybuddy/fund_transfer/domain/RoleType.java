package com.paymybuddy.fund_transfer.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role_type", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class RoleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column(name = "role_type")
    private String roleType;

    @OneToMany(mappedBy = "roleType")
    private List<User> usersList;

    public RoleType() { }

    public RoleType(int id, String roleType) {
        this.id = id;
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

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
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
