package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Currency;
import com.paymybuddy.fund_transfer.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency findCurrencyByCurrencyLabel(String currencyLabel) {
        return currencyRepository.findCurrencyByCurrencyLabel(currencyLabel);
    }
}
