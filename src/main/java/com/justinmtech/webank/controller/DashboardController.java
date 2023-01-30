package com.justinmtech.webank.controller;

import com.justinmtech.webank.repository.TransactionRepository;
import com.justinmtech.webank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    TransactionRepository transactionRepository;

    @RequestMapping("/dashboard")
    public String getDashboard(Model model) {

        model.addAttribute("user", getUserService().getCurrentAuthenticatedUser());
        model.addAttribute("transactions", transactionRepository.findAll());
        return "dashboard";
    }

    public UserService getUserService() {
        return userService;
    }
}
