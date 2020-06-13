package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.service.ConnectionService;
import com.paymybuddy.fund_transfer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
public class UserController {

    private UserService userService;
    private ConnectionService connectionService;

    @Autowired
    public UserController(UserService userService, ConnectionService connectionService) {
        this.userService = userService;
        this.connectionService = connectionService;
    }

    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            result.rejectValue("email", "error-registration","The email you entered is already taken.");
        }

        if (result.hasErrors()) {
            modelAndView.setViewName("/register");
        }
        else {
            userService.createUserByRegistration(user);
            modelAndView.setViewName("/login");

        }
        return modelAndView;
    }

    @GetMapping("/user/addConnection")
    public ModelAndView getAddConnection(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            modelAndView.setViewName("addConnection");
            modelAndView.addObject("user", user);
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @PostMapping("/user/addConnection")
    public ModelAndView createAddConnection(@ModelAttribute("user") User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            if (userService.findUserByEmail(user.getEmail()) != null) {
                connectionService.createConnection(userFromAuth, user.getEmail());
                RedirectView redirectView = new RedirectView();
                redirectView.setUrl("/user/transfer");
                modelAndView.setView(redirectView);
            }
            else {
                result.rejectValue("email", "error-addConnection", "The email you entered isn't registered with our system.");
                modelAndView.setViewName("addConnection");
            }
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }
}
