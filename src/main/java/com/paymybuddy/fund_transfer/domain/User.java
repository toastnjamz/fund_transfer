package com.paymybuddy.fund_transfer.domain;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_role_type_id")
    private RoleType roleType;

    @NotEmpty(message="Please provide an email.")
    @Email(message="Email must be in a valid format.")
    private String email;

    @NotEmpty(message="Enter a valid password.")
    @Size(min=5, max=60, message="Passwords need to be between 5-60 characters.")
    private String password;

    @Column(name = "display_name")
    @NotEmpty(message="Enter a display name.")
    private String displayName;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Type(type = "numeric_boolean")
    private boolean isActive;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Connection> connectionList;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;

    public User() {}

    public User(RoleType roleType, @NotEmpty String email, @NotEmpty String password, String displayName) {
        this.roleType = roleType;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        isActive = isActive;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @PrePersist
    public void setCreatedOn() {
        this.createdOn = new Date();
        this.isActive = true;
    }

    @PreUpdate
    public void setUpdatedOn() {
        this.updatedOn = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
