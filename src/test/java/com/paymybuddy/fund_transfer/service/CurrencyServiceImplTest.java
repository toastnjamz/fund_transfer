package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Currency;
import com.paymybuddy.fund_transfer.repository.CurrencyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepositoryMock;

    @InjectMocks
    private CurrencyServiceImpl currencyServiceImpl;

    @Test
    public void findCurrencyByCurrencyLabel_currencyExists_currencyReturned() {
        //arrange
        Currency currency = new Currency("USD", "US Dollars");

        when(currencyRepositoryMock.findCurrencyByCurrencyLabel(currency.getCurrencyLabel())).thenReturn(currency);

        //act
        Currency result = currencyServiceImpl.findCurrencyByCurrencyLabel("USD");

        //assert
        assertEquals("US Dollars", result.getCurrencyDescription());
    }

    @Test
    public void findCurrencyByCurrencyLabel_currencyDoesNotExist_nullReturned() {
        //arrange

        //act
        Currency result = currencyServiceImpl.findCurrencyByCurrencyLabel("USD");

        //assert
        assertNull(result);
    }
}
