package com.paymybuddy.fund_transfer.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role_type")
@EntityListeners(AuditingEntityListener.class)
public class RoleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String roleType;

//    @OneToMany(mappedBy = "roleType")
//    @JsonManagedReference
//    private List<User> usersList;

    @OneToOne(mappedBy = "roleType", cascade = CascadeType.ALL)
    private User user;

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

//    public List<User> getUsersList() {
//        return usersList;
//    }
//
//    public void setUsersList(List<User> usersList) {
//        this.usersList = usersList;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
