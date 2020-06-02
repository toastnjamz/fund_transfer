package com.paymybuddy.fund_transfer.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "connection", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Owning user (the user to whom the connection belongs)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private User user;

    @Column(name = "connected_user_id")
    private int connectedUserId;

    public Connection() {};

    public Connection(User user, int connectedUserId) {
        this.user = user;
        this.connectedUserId = connectedUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getConnectedUserId() {
        return connectedUserId;
    }

    public void setConnectedUserId(int connectedUserId) {
        this.connectedUserId = connectedUserId;
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
