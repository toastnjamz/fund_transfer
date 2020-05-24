package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Currency;

import java.util.List;

public interface CurrencyService {

    public List<Currency> findAllCurrencies();

    public Currency findCurrencyById(int id);

    public Currency findCurrencyByCurrencyLabel(String currencyLabel);

    public Currency createCurrency(Currency currency);

    public void updateCurrency(Currency currency);

    public void deleteCurrency(int id);
}
