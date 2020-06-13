package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.Connection;
import com.paymybuddy.fund_transfer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {

    List<Connection> findConnectionListByUser(User user);
}
