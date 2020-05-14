package com.paymybuddy.fund_transfer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransferController {

    @RequestMapping("/transfer")
    public ModelAndView transfer() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("transfer");
        return modelAndView;
    }
}
