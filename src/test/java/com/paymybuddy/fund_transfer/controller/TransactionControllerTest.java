package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.*;
import com.paymybuddy.fund_transfer.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringSecurityAuthTestConfig.class)
@RunWith(SpringRunner.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private ConnectionService connectionServiceMock;

    @MockBean
    private TransactionService transactionServiceMock;

    @MockBean
    private AccountService accountServiceMock;

    @MockBean
    private BankAccountService bankAccountServiceMock;

    private User user;
    private Account userAccount;
    private Authentication auth;
    private SecurityContext securityContext;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Before
    public void setUpMockSecurity() {
        user = new User();
        user.setEmail("regular@test.com");
        user.setPassword("1234");
        user.setDisplayName("regular_user");
        user.setRoleType(new RoleType("Regular"));

        userAccount = new Account();
        userAccount.setUser(user);
        userAccount.setId(1);
        userAccount.setBalance(new BigDecimal(100.0));

        auth = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void getTransfer_userIsLoggedInNoTransactions_statusIsSuccessful() throws Exception {
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);
        when(connectionServiceMock.findConnectedUsersByOwningUser(user)).thenReturn(new ArrayList<>());
        when(transactionServiceMock.findTransactionListByAccount(userAccount)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/user/transfer"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void getTransfer_userIsLoggedInRegularTransaction_statusIsSuccessful() throws Exception {
        User user2 = new User();
        user2.setEmail("regular@test.com");

        Account user2Account = new Account();
        user2Account.setUser(user2);
        user2Account.setId(2);
        user2Account.setBalance(new BigDecimal(100.0));

        Connection connection = new Connection(user, user2.getId());

        Transaction regularTransaction = new Transaction();
        regularTransaction.setAccount(userAccount);
        regularTransaction.setToAccountId(2);
        regularTransaction.setAmount(new BigDecimal(5.0));

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);
        when(connectionServiceMock.findConnectedUsersByOwningUser(user)).thenReturn(Arrays.asList(user2));
        when(transactionServiceMock.findTransactionListByAccount(userAccount)).thenReturn(Arrays.asList(regularTransaction));

        mockMvc.perform(get("/user/transfer"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void getTransfer_userIsLoggedInAddMoneyTransaction_statusIsSuccessful() throws Exception {
        BankAccount bankAccount = new BankAccount(userAccount, "testBankAccountNo");
        bankAccount.setId(1);

        Transaction addMoneyTransaction = new Transaction();
        addMoneyTransaction.setAccount(userAccount);
        addMoneyTransaction.setBankAccount(bankAccount);
        addMoneyTransaction.setAmount(new BigDecimal(100.0));

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);
        when(connectionServiceMock.findConnectedUsersByOwningUser(user)).thenReturn(new ArrayList<>());
        when(transactionServiceMock.findTransactionListByAccount(userAccount)).thenReturn(Arrays.asList(addMoneyTransaction));

        mockMvc.perform(get("/user/transfer"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void getTransfer_userIsLoggedInTransferToBankTransaction_statusIsSuccessful() throws Exception {
        BankAccount bankAccount = new BankAccount(userAccount, "testBankAccountNo");
        bankAccount.setId(1);

        Transaction transferToBankTransaction = new Transaction();
        transferToBankTransaction.setAccount(userAccount);
        transferToBankTransaction.setBankAccount(bankAccount);
        transferToBankTransaction.setAmount(new BigDecimal(100.0));

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);
        when(connectionServiceMock.findConnectedUsersByOwningUser(user)).thenReturn(new ArrayList<>());
        when(transactionServiceMock.findTransactionListByAccount(userAccount)).thenReturn(Arrays.asList(transferToBankTransaction));

        mockMvc.perform(get("/user/transfer"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getTransfer_userIsNotLoggedIn_statusIsRedirectionToLogin() throws Exception {
        mockMvc.perform(get("/user/transfer")
                .requestAttr("user", user))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void postTransfer_userIsLoggedIn_statusIRedirectionToTransfer() throws Exception {
        String description = "Test Description";
        String amount = "100.00";

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);

        mockMvc.perform(post("/user/transfer")
                .queryParam("email", user.getEmail())
                .queryParam("description", description)
                .queryParam("amount", amount))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/transfer"));
    }

    @Test
    public void postTransfer_userIsNotLoggedIn_statusIsClientError() throws Exception {
        mockMvc.perform(post("/user/transfer")
                .requestAttr("user", user))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithUserDetails("admin@test.com")
    public void getTransactionsLog_adminIsLoggedIn_statusISuccessful() throws Exception {
        User adminUser = new User();
        adminUser.setEmail("admin@test.com");
        adminUser.setPassword("1234");
        adminUser.setDisplayName("regular_user");
        adminUser.setRoleType(new RoleType("Admin"));

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(adminUser);

        mockMvc.perform(get("/admin/transactions"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getTransactionsLog_adminIsNotLoggedIn_statusIs403Successful() throws Exception {
        mockMvc.perform(get("/admin/transactions"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void profile_userIsLoggedIn_statusISuccessful() throws Exception {
        user.setAccount(userAccount);
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);

        mockMvc.perform(get("/user/profile"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void profile_userIsNotLoggedIn_statusIsRedirectionToLogin() throws Exception {
        mockMvc.perform(get("/user/profile"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void postAddBankAccount_userIsLoggedIn_statusIsRedirectionToProfile() throws Exception {
        user.setAccount(userAccount);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountNo("testBankAccountNo");

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);
        when(bankAccountServiceMock.createBankAccount(user, "testBankAccountNo")).thenReturn(bankAccount);

        mockMvc.perform(post("/user/addBankAccount")
                .queryParam("bankAccountNo", "testBankAccountNo"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void postAddBankAccount_userIsNotLoggedIn_statusIsRedirectionToLogin() throws Exception {
        BankAccount bankAccount = new BankAccount();

        mockMvc.perform(post("/user/addBankAccount")
                .queryParam("bankAccountNo", "testBankAccountNo"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void postAddMoney_userIsLoggedIn_statusIsRedirectionToProfile() throws Exception {
        user.setAccount(userAccount);
        String amount = "100.00";

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);

        mockMvc.perform(post("/user/addMoney")
                .requestAttr("amount", amount))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void postAddMoney_userIsNotLoggedIn_statusIsRedirectionToLogin() throws Exception {
        String amount = "100.00";

        mockMvc.perform(post("/user/addMoney")
                .requestAttr("amount", amount))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void postTransferToBank_userIsLoggedIn_statusIsRedirectionToProfile() throws Exception {
        user.setAccount(userAccount);

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);

        mockMvc.perform(post("/user/transferToBank"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void postTransferToBank_userIsNotLoggedIn_statusIsRedirectionToLogin() throws Exception {
        mockMvc.perform(post("/user/transferToBank"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }
}
