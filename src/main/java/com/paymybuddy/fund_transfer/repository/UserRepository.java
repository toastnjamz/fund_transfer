package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(String email);
}
