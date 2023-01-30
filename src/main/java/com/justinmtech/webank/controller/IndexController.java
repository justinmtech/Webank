package com.justinmtech.webank.controller;

import com.justinmtech.webank.model.User;
import com.justinmtech.webank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        try {
            getUserService().createUser(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getPhoneNumber());
            return "account-created";
        } catch (Exception e) {
            return "error-page";
        }
    }

    public UserService getUserService() {
        return userService;
    }
}
