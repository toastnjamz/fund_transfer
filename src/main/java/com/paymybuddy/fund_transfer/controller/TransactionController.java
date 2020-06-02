package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.Transaction;
import com.paymybuddy.fund_transfer.domain.TransactionDTO;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.service.AccountService;
import com.paymybuddy.fund_transfer.service.ConnectionService;
import com.paymybuddy.fund_transfer.service.TransactionService;
import com.paymybuddy.fund_transfer.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
public class TransactionController {

    private UserService userService;
    private ConnectionService connectionService;
    private TransactionService transactionService;
    private AccountService accountService;

    public TransactionController(UserService userService, ConnectionService connectionService,
                                 TransactionService transactionService, AccountService accountService) {
        this.userService = userService;
        this.connectionService = connectionService;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    //TODO
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
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setToUserEmail(accountService.findAccountById(transaction.getToAccountId()).getUser().getEmail());
                transactionDTO.setAmount(transaction.getAmount());
                transactionDTO.setDescription(transaction.getDescription());
                transactionDTOList.add(transactionDTO);
            }
            if (transactionDTOList.isEmpty()) {
                transactionDTOList.add(new TransactionDTO("You don't have any ", "transactions yet.", new BigDecimal(0.0)));
            }

            modelAndView.setViewName("transfer");
            modelAndView.addObject("connectedUsersList", connectedUsersList);
            modelAndView.addObject("transactionDTOList", transactionDTOList);
//            modelAndView.addObject("request", new TransactionDTO());
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    //TODO: remove when done with above method
//    @GetMapping("/user/transfer")
//    public ModelAndView getTransfer() {
//        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User userFromAuth = userService.getUserFromAuth(auth);
//        if (userFromAuth != null) {
//            List<User> connectedUsersList = connectionService.findConnectedUsersByOwningUser(userFromAuth);
//            modelAndView.setViewName("transfer");
//            modelAndView.addObject("connectedUsersList", connectedUsersList);
//        }
//        else {
//            modelAndView.setViewName("redirect:/login");
//        }
//        return modelAndView;
//    }

    //TODO
//    @PostMapping("/user/transfer")
//    public ModelAndView postTransfer(@RequestParam("email") String email, @RequestParam("description") String description,
//                                     @RequestParam("amount") String amount, BindingResult result) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        if (
//
//        if (result.hasErrors()) {
//
//        }
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User userFromAuth = userService.getUserFromAuth(auth);
//        if (userFromAuth != null) {
//            transactionService.createTransactionByTransferToFriend(userFromAuth, email, description, amount);
//            List<User> connectedUsersList = connectionService.findConnectedUsersByOwningUser(userFromAuth);
//            modelAndView.setViewName("transfer");
//            modelAndView.addObject("connectedUsersList", connectedUsersList);
//        }
//        else {
//            modelAndView.setViewName("redirect:/login");
//        }
//        return modelAndView;
//    }

    //TODO: delete when done with method above
    @PostMapping("/user/transfer")
    public ModelAndView postTransfer() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            List<User> connectedUsersList = connectionService.findConnectedUsersByOwningUser(userFromAuth);
            modelAndView.setViewName("transfer");
            modelAndView.addObject("connectedUsersList", connectedUsersList);
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    //TODO
//    @GetMapping("/user/transferToBank")
//    public ModelAndView getTransferToBank() {
//        ModelAndView modelAndView = new ModelAndView();
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.getUserFromAuth(auth);
//
//        if (user != null) {
//            modelAndView.setViewName("transferToBank");
//            modelAndView.addObject("connectionList", connectionList);
//        }
//        else {
//            modelAndView.setViewName("redirect:/login");
//        }
//        return modelAndView;
//    }
}
