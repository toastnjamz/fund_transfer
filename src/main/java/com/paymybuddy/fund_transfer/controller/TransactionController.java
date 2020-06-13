package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.*;
import com.paymybuddy.fund_transfer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {

    private UserService userService;
    private ConnectionService connectionService;
    private TransactionService transactionService;
    private AccountService accountService;
    private BankAccountService bankAccountService;

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public TransactionController(UserService userService, ConnectionService connectionService,
                                 TransactionService transactionService, AccountService accountService,
                                 BankAccountService bankAccountService) {
        this.userService = userService;
        this.connectionService = connectionService;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/user/transfer")
    public ModelAndView getTransfer() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            List<User> connectedUsersList = connectionService.findConnectedUsersByOwningUser(userFromAuth);
            List<Transaction> transactionList = transactionService.findTransactionListByAccount(userFromAuth.getAccount());
            List<TransactionDTO> transactionDTOList = new ArrayList<>();

            for (Transaction transaction : transactionList) {
                if (transaction.getTransactionType().getTransactionType().equals("Regular")) {
                    TransactionDTO transactionDTO = new TransactionDTO();
                    transactionDTO.setToUserEmail(accountService.findAccountById(transaction.getToAccountId()).getUser().getEmail());
                    transactionDTO.setAmount(transaction.getAmount().toString());
                    transactionDTO.setDescription(transaction.getDescription());
                    transactionDTOList.add(transactionDTO);
                }
                else if (transaction.getTransactionType().getTransactionType().equals("AddMoney")) {
                    createTransactionDTOToFromBank(transactionDTOList, transaction);
                }
                else if (transaction.getTransactionType().getTransactionType().equals("TransferToBank")) {
                    createTransactionDTOToFromBank(transactionDTOList, transaction);
                }
            }
            if (transactionDTOList.isEmpty()) {
                transactionDTOList.add(new TransactionDTO("Pay some buddies!", "0.0", ""));
            }
            modelAndView.setViewName("transfer");
            modelAndView.addObject("connectedUsersList", connectedUsersList);
            modelAndView.addObject("transactionDTOList", transactionDTOList);
            modelAndView.addObject("transactionDTO", new TransactionDTO());
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    //TODO: decide if i want to keep extracted method
    private void createTransactionDTOToFromBank(List<TransactionDTO> transactionDTOList, Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setToUserEmail("");
        transactionDTO.setAmount(transaction.getAmount().toString());
        transactionDTO.setDescription(transaction.getDescription());
        transactionDTOList.add(transactionDTO);
    }

//    //TODO: fix? displaying error works, but dropdown list doesn't populate transactionDTO.toUserEmail field
//    @PostMapping("/user/transfer")
//    public ModelAndView postTransfer(@ModelAttribute("transactionDTO") TransactionDTO transactionDTO, BindingResult result) {
//
//        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User userFromAuth = userService.getUserFromAuth(auth);
//
//        if (userFromAuth != null) {
//
//            List<User> connectedUsersList = connectionService.findConnectedUsersByOwningUser(userFromAuth);
//            modelAndView.setViewName("/transfer");
//            modelAndView.addObject("connectedUsersList", connectedUsersList);
//
//            if (!transactionService.isInCurrencyFormat(transactionDTO.getAmount())) {
//                result.rejectValue("amount", "error-transfer","Enter a valid amount.");
//            }
//
//            if (!result.hasErrors()) {
//                transactionService.createTransactionByTransferToFriend(userFromAuth, transactionDTO.getToUserEmail(),
//                        transactionDTO.getAmount(), transactionDTO.getDescription());
//                RedirectView redirectView = new RedirectView();
//                redirectView.setUrl("/user/transfer");
//                modelAndView.setView(redirectView);
//                modelAndView.addObject("connectedUsersList", connectedUsersList);
//            }
//            else {
//                modelAndView.setViewName("/transfer");
//            }
//        }
//        else {
//            modelAndView.setViewName("redirect:/login");
//        }
//        return modelAndView;
//    }

    //Method logs request and response for future use with invoicing system
    @PostMapping("/user/transfer")
    public ModelAndView postTransfer(@RequestParam("email") String email, @RequestParam("description") String description,
                                     @RequestParam("amount") String amount) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        log.debug("HTTP POST request received for postTransfer: {} {} {} {}", userFromAuth, email, description, amount);
        if (userFromAuth != null) {
            transactionService.createTransactionByTransferToFriend(userFromAuth, email, amount, description);
            log.info("HTTP POST request received for postTransfer, SUCCESS");
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/user/transfer");
            modelAndView.setView(redirectView);
        }
        else {
            log.error("HTTP Post request rejected for postTransfer, ERROR");
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    //TODO: test
    @GetMapping("/admin/transactions")
    public ModelAndView getTransactionsLog() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserFromAuth(auth);
        if (user != null && user.getRoleType().getRoleType().equals("Admin")) {
            List<Transaction> transactionList = transactionService.findTransactionListByTransactionType("Regular");
            List<TransactionOrderDTO> transactionOrderDTOList = new ArrayList<>();
            for (Transaction transaction : transactionList) {
                TransactionOrderDTO transactionOrderDTO = new TransactionOrderDTO();
                transactionOrderDTO.setFromUserEmail(transaction.getAccount().getUser().getEmail());
                transactionOrderDTO.setFromUserEmail(accountService.findAccountById(transaction.getToAccountId()).getUser().getEmail());
                transactionOrderDTO.setDescription(transaction.getDescription());
                transactionOrderDTO.setAmount(transaction.getAmount().toString());
                transactionOrderDTO.setFee(transaction.getTransactionFee().toString());
                transactionOrderDTO.setDate(transaction.getCreatedOn().toString());
                transactionOrderDTOList.add(transactionOrderDTO);
            }
            modelAndView.setViewName("transactionLog");
            modelAndView.addObject("transactionOrderDTOList", transactionOrderDTOList);
            modelAndView.addObject("transactionOrderDTO", new TransactionOrderDTO());
        }
        else {
            modelAndView.setViewName("403");
        }
        return modelAndView;
    }

    //TODO: fix balance not updating on profile page
    @GetMapping("/user/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserFromAuth(auth);
        if (user != null) {
            modelAndView.setViewName("profile");
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @PostMapping("/user/addBankAccount")
    public ModelAndView postAddBankAccount(@RequestParam("bankAccountNo") String bankAccountNo) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            bankAccountService.createBankAccount(userFromAuth, bankAccountNo);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/user/profile");
            modelAndView.setView(redirectView);
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @PostMapping("/user/addMoney")
    public ModelAndView postAddMoney(@ModelAttribute("amount") String amount) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            transactionService.createTransactionByAddMoney(userFromAuth, amount);
//            RedirectView redirectView = new RedirectView();
//            redirectView.setUrl("/user/profile");
//            modelAndView.setView(redirectView);
            modelAndView.setViewName("profile");
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @PostMapping("/user/transferToBank")
    public ModelAndView postTransferToBank() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            transactionService.createTransactionByTransferToBank(userFromAuth);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/user/profile");
            modelAndView.setView(redirectView);
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }
}
