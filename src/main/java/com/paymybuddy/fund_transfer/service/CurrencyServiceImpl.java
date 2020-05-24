package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Currency;
import com.paymybuddy.fund_transfer.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> findAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency findCurrencyById(int id) {
        Optional<Currency> currencyOptional = currencyRepository.findById(id);
        if (currencyOptional.isPresent()) {
            Currency currency = currencyOptional.get();
            return currency;
        }
        return null;
    }

    @Override
    public Currency findCurrencyByCurrencyLabel(String currencyLabel) {
        return currencyRepository.findCurrencyByCurrencyLabel(currencyLabel);
    }

    @Override
    public Currency createCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Override
    public void updateCurrency(Currency currency) {
        Currency updatedCurrency = findCurrencyById(currency.getId());
        updatedCurrency.setCurrencyLabel(currency.getCurrencyLabel());
        updatedCurrency.setCurrencyDescription(currency.getCurrencyDescription());
    }

    @Override
    public void deleteCurrency(int id) {
        currencyRepository.deleteById(id);
    }
}
