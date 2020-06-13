package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    private UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/user/contact")
    public ModelAndView contact() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserFromAuth(auth);
        if (user != null) {
            modelAndView.setViewName("contact");
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }
}
