package com.justinmtech.webank.controller;

import com.justinmtech.webank.email.EmailService;
import com.justinmtech.webank.model.User;
import com.justinmtech.webank.service.ConfirmationTokenService;
import com.justinmtech.webank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CompletableFuture;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @RequestMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @RequestMapping("/testload")
    @Async
    public CompletableFuture<String> testLoad(Model model) {
        model.addAttribute("user", new User());
        int userAmount = 250;
        for (int i = 0; i < userAmount; i++) {
            try {
                getUserService().createUser("username" + i, "password");
            } catch (Exception e) {
                return CompletableFuture.completedFuture("error-page");
            }
        }
        return CompletableFuture.completedFuture("dashboard");
    }

    public UserService getUserService() {
        return userService;
    }

    public ConfirmationTokenService getConfirmationTokenService() {
        return confirmationTokenService;
    }
}
