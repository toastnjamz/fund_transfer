package com.paymybuddy.fund_transfer.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "connection", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private User owningUser;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_connection_user_id")
    private User connectedUser;

//    //TODO: How to remove this, since @Transient doesn't work and ConnectionRepository seems to need it?
    //Email field for ConnectionRepository method findConnectionListByUserEmail()
    private String userEmail;

//    @Transient
//    private String connectedUserEmail;

    //TODO: List<Transaction> transactionList, will go here?

    public Connection() {};

    public Connection(User owningUser, User connectedUser) {
        this.owningUser = owningUser;
        this.connectedUser = connectedUser;
        this.userEmail = owningUser.getEmail();
//        this.connectedUserEmail = connectedUser.getEmail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwningUser() {
        return owningUser;
    }

    public void setOwningUser(User owningUser) {
        this.owningUser = owningUser;
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        Connection that = (Connection) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
