package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {

    //TODO: delete unused method?
    Connection findConnectionByUserEmail(String userEmail);

    List<Connection> findConnectionListByUserEmail(String userEmail);
}
