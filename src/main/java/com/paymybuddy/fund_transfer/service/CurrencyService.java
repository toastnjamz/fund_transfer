package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Currency;

public interface CurrencyService {

    public Currency findCurrencyByCurrencyLabel(String currencyLabel);
}
