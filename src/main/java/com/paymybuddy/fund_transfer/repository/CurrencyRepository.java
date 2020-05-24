package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Currency findCurrencyByCurrencyLabel(String currencyLabel);
}
