package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.*;
import com.paymybuddy.fund_transfer.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
public class TransactionController {

    private UserService userService;
    private ConnectionService connectionService;
    private TransactionService transactionService;
    private AccountService accountService;
    private BankAccountService bankAccountService;

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

//            List<Transaction> regularTransactionList = transactionService.findTransactionListByAccount(userFromAuth.getAccount()).stream()
//                    .filter(t -> t.getTransactionType().getTransactionType().equals("Regular")).collect(Collectors.toList());

            for (Transaction transaction : transactionList) {
                if (transaction.getTransactionType().getTransactionType().equals("Regular")) {
                    TransactionDTO transactionDTO = new TransactionDTO();
                    transactionDTO.setToUserEmail(accountService.findAccountById(transaction.getToAccountId()).getUser().getEmail());
                    transactionDTO.setAmount(transaction.getAmount().toString());
                    transactionDTO.setDescription(transaction.getDescription());
                    transactionDTOList.add(transactionDTO);
                }
                else if (transaction.getTransactionType().getTransactionType().equals("AddMoney")) {
                    TransactionDTO transactionDTO = new TransactionDTO();
                    transactionDTO.setToUserEmail("");
                    transactionDTO.setAmount(transaction.getAmount().toString());
                    transactionDTO.setDescription(transaction.getDescription());
                    transactionDTOList.add(transactionDTO);
                }
                else if (transaction.getTransactionType().getTransactionType().equals("TransferToBank")) {
                    TransactionDTO transactionDTO = new TransactionDTO();
                    transactionDTO.setToUserEmail("");
                    transactionDTO.setAmount(transaction.getAmount().toString());
                    transactionDTO.setDescription(transaction.getDescription());
                    transactionDTOList.add(transactionDTO);
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

    @PostMapping("/user/transfer")
    public ModelAndView postTransfer(@RequestParam("email") String email, @RequestParam("description") String description,
                                     @RequestParam("amount") String amount) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            transactionService.createTransactionByTransferToFriend(userFromAuth, email, amount, description);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/user/transfer");
            modelAndView.setView(redirectView);
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }
    
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

    //TODO: test
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

    //TODO: test
    @PostMapping("/user/addMoney")
    public ModelAndView postAddMoney(@ModelAttribute("amount") String amount) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            transactionService.createTransactionByAddMoney(userFromAuth, amount);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/user/profile");
            modelAndView.setView(redirectView);
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    //TODO: test
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
