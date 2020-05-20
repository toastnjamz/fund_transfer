package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {

    //TODO: do I need this? Where will I use it?
    Connection findConnectionByUserEmail(String userEmail);

    //TODO: how can this work? Shouldn't this go in a service class?
    List<Connection> findConnectionListByUserEmail(String userEmail);
}
