package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.*;
import com.paymybuddy.fund_transfer.repository.TransactionRepository;
import com.paymybuddy.fund_transfer.repository.TransactionTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @Mock
    private TransactionTypeRepository transactionTypeRepositoryMock;

    @Mock
    private AccountService accountServiceMock;

    @Mock
    private CurrencyService currencyServiceMock;

    @Mock
    private BankAccountService bankAccountServiceMock;

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    RoleType roleType;
    User user1;
    User user2;
    Account account1;
    Account account2;

    @Before
    public void setup() {
        transactionServiceImpl = new TransactionServiceImpl(transactionRepositoryMock,
                transactionTypeRepositoryMock, accountServiceMock, currencyServiceMock, bankAccountServiceMock);

        roleType = new RoleType("Regular");
        user1 = new User(roleType, "user1@test.com", "1234", "user_1");
        user2 = new User(roleType, "user2@test.com", "1234", "user_2");

        account1 = new Account();
        account1.setId(1);
        account1.setUser(user1);
        account1.setBalance(new BigDecimal(0.0));
        user1.setAccount(account1);

        account2 = new Account();
        account2.setId(2);
        account2.setUser(user2);
        account2.setBalance(new BigDecimal(0.0));
        user1.setAccount(account2);
    }

    @Test
    public void findTransactionListByAccount_accountExists_transactionListReturned() {
        //arrange
        Transaction transaction = new Transaction(account1, account2.getId(), new BigDecimal(10.0));

        when(transactionRepositoryMock.findTransactionListByAccount(account1)).thenReturn(Arrays.asList(transaction));

        //act
        List<Transaction> result = transactionServiceImpl.findTransactionListByAccount(account1);

        //assert
        assertEquals(1, result.size());
    }

    //TODO: figure out how to pass in a null account or remove test
    @Test
    public void findTransactionListByAccount_accountDoesNotExist_nullReturned() {
        //arrange

        //act

        //assert
    }

    @Test
    public void findTransactionListByTransactionType_transactionTypeExists_transactionListReturned() {
        //arrange
        Transaction transaction = new Transaction(account1, account2.getId(), new BigDecimal(10.0));
        TransactionType transactionType = new TransactionType("Regular");
        transaction.setTransactionType(transactionType);

        when(transactionRepositoryMock.findAll()).thenReturn(Arrays.asList(transaction));

        //act
        List<Transaction> result = transactionServiceImpl.findTransactionListByTransactionType("Regular");

        //assert
        assertEquals(1, result.size());
    }

    @Test
    public void findTransactionListByTransactionType_transactionTypeDoesNotExist_emptyTransactionListReturned() {
        //arrange
        account1.setBalance(new BigDecimal(10.0));

        Transaction transaction = new Transaction(account1, account2.getId(), new BigDecimal(10.0));
        TransactionType transactionType = new TransactionType("Regular");
        transaction.setTransactionType(transactionType);

        when(transactionRepositoryMock.findAll()).thenReturn(Arrays.asList(transaction));

        //act
        List<Transaction> result = transactionServiceImpl.findTransactionListByTransactionType("Admin");

        //assert
        assertEquals(0, result.size());
    }

    @Test
    public void createTransactionByTransferToFriend_transactionValid_transactionSaved() {
        //arrange
        account1.setBalance(new BigDecimal(10.0));

        String amountToTransfer = "5.0";
        String description = "Test transaction";
        MathContext mc = new MathContext(3);

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);
        when(accountServiceMock.findAccountByUserEmail(user2.getEmail())).thenReturn(account2);

        //act
        transactionServiceImpl.createTransactionByTransferToFriend(user1, user2.getEmail(), amountToTransfer, description);

        //assert
        verify(transactionRepositoryMock, times(1)).save(any(Transaction.class));
        assertEquals(new BigDecimal(4.97).round(mc), account1.getBalance());
    }

    @Test
    public void createTransactionByTransferToFriend_transactionInvalid_transactionNotSaved() {
        //arrange
        account1.setBalance(new BigDecimal(10.0));
        String amountToTransfer = "50.0";
        String description = "Test transaction";

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);

        //act
        transactionServiceImpl.createTransactionByTransferToFriend(user1, user2.getEmail(), amountToTransfer, description);

        //assert
        verify(transactionRepositoryMock, times(0)).save(any(Transaction.class));
    }

    @Test
    public void createTransactionByAddMoney_transactionValid_transactionSaved() {
        //arrange
        BankAccount bankAccount = new BankAccount(account1, "testBankAccountNo");
        String amountToTransfer = "5";
        MathContext mc = new MathContext(4);

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);
        when(bankAccountServiceMock.findBankAccountByAccount(account1)).thenReturn(bankAccount);

        //act
        transactionServiceImpl.createTransactionByAddMoney(user1, amountToTransfer);

        //assert
        verify(transactionRepositoryMock, times(1)).save(any(Transaction.class));
        assertEquals(new BigDecimal(5.0).round(mc), account1.getBalance());
    }

    @Test
    public void createTransactionByAddMoney_transactionInvalid_transactionNotSaved() {
        //arrange
        String amountToTransfer = "10.0";
        String description = "Test transaction";

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);

        //act
        transactionServiceImpl.createTransactionByTransferToFriend(user1, user2.getEmail(), amountToTransfer, description);

        //assert
        verify(transactionRepositoryMock, times(0)).save(any(Transaction.class));
    }

    @Test
    public void createTransactionByTransferToBank_transactionValid_transactionSaved() {
        //arrange
        account1.setBalance(new BigDecimal(10.0));
        BankAccount bankAccount = new BankAccount(account1, "testBankAccountNo");
        MathContext mc = new MathContext(4);

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);
        when(bankAccountServiceMock.findBankAccountByAccount(account1)).thenReturn(bankAccount);

        //act
        transactionServiceImpl.createTransactionByTransferToBank(user1);

        //assert
        verify(transactionRepositoryMock, times(1)).save(any(Transaction.class));
        assertEquals(new BigDecimal(0.0).round(mc), account1.getBalance());
    }

    @Test
    public void createTransactionByTransferToBank_noLinkedBankAccount_transactionNotSaved() {
        //arrange
        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);

        //act
        transactionServiceImpl.createTransactionByTransferToBank(user1);

        //assert
        verify(transactionRepositoryMock, times(0)).save(any(Transaction.class));
    }

    @Test
    public void createTransactionByTransferToBank_zeroBalance_transactionNotSaved() {
        //arrange
        BankAccount bankAccount = new BankAccount(account1, "testBankAccountNo");

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);
        when(bankAccountServiceMock.findBankAccountByAccount(account1)).thenReturn(bankAccount);

        //act
        transactionServiceImpl.createTransactionByTransferToBank(user1);

        //assert
        verify(transactionRepositoryMock, times(0)).save(any(Transaction.class));
    }

    @Test
    public void isInCurrencyFormat_validCurrencyFormat_trueReturned() {
        //arrange
        String amount = "10";

        //act
        boolean result = transactionServiceImpl.isInCurrencyFormat(amount);

        //assert
        assertTrue(result);
    }

    @Test
    public void isInCurrencyFormat_invalidCurrencyFormat_falseReturned() {
        //arrange
        String amount = "&*DSlfkdsfslfdj";

        //act
        boolean result = transactionServiceImpl.isInCurrencyFormat(amount);

        //assert
        assertFalse(result);
    }

    @Test
    public void transactionValidator_validRegularTransaction_trueReturned() {
        //arrange
        account1.setBalance(new BigDecimal(10.0));
        BigDecimal transactionAmount = new BigDecimal(5.0);

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);

        //act
        boolean result = transactionServiceImpl.transactionValidator("Regular", user1.getEmail(), transactionAmount);

        //assert
        assertTrue(result);
    }

    @Test
    public void transactionValidator_invalidRegularTransaction_falseReturned() {
        //arrange
        account1.setBalance(new BigDecimal(10.0));
        BigDecimal transactionAmount = new BigDecimal(20.0);

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);

        //act
        boolean result = transactionServiceImpl.transactionValidator("Regular", user1.getEmail(), transactionAmount);

        //assert
        assertFalse(result);
    }

    @Test
    public void transactionValidator_validAddMoneyTransaction_trueReturned() {
        //arrange;
        BigDecimal transactionAmount = new BigDecimal(5.0);

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);

        //act
        boolean result = transactionServiceImpl.transactionValidator("AddMoney", user1.getEmail(), transactionAmount);

        //assert
        assertTrue(result);
    }

    @Test
    public void transactionValidator_invalidAddMoneyTransaction_falseReturned() {
        //arrange;
        BigDecimal transactionAmount = new BigDecimal(0.0);

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);

        //act
        boolean result = transactionServiceImpl.transactionValidator("AddMoney", user1.getEmail(), transactionAmount);

        //assert
        assertFalse(result);
    }

    @Test
    public void transactionValidator_validTransferToBankTransaction_trueReturned() {
        //arrange;
        account1.setBalance(new BigDecimal(10.0));
        BigDecimal transactionAmount = new BigDecimal(10.0);

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);

        //act
        boolean result = transactionServiceImpl.transactionValidator("TransferToBank", user1.getEmail(), transactionAmount);

        //assert
        assertTrue(result);
    }

    @Test
    public void transactionValidator_invalidTransferToBankTransaction_falseReturned() {
        //arrange;
        BigDecimal transactionAmount = new BigDecimal(0.0);

        when(accountServiceMock.findAccountByUserEmail(user1.getEmail())).thenReturn(account1);

        //act
        boolean result = transactionServiceImpl.transactionValidator("TransferToBank", user1.getEmail(), transactionAmount);

        //assert
        assertFalse(result);
    }
}
