package com.justinmtech.webank.controller;

import com.justinmtech.webank.model.User;
import com.justinmtech.webank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

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

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        try {
            getUserService().createUser(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getPhoneNumber());
            return "dashboard";
        } catch (Exception e) {
            return "error-page";
        }
    }

    public UserService getUserService() {
        return userService;
    }
}
