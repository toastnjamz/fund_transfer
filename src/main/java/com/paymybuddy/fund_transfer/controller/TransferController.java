package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.Connection;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.service.ConnectionService;
import com.paymybuddy.fund_transfer.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class TransferController {

    private UserService userService;
    private ConnectionService connectionService;

    public TransferController(UserService userService, ConnectionService connectionService) {
        this.userService = userService;
        this.connectionService = connectionService;
    }

    @GetMapping("/user/transfer")
    public ModelAndView getTransfer() {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserFromAuth(auth);

        if (user != null) {
            String userEmail = user.getEmail();
            List<Connection> connectionList = connectionService.findConnectionListByUserEmail(userEmail);
            modelAndView.setViewName("transfer");
            modelAndView.addObject("connectionList", connectionList);
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }
}
