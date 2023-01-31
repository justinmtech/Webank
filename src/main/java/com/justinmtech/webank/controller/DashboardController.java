package com.justinmtech.webank.controller;

import com.justinmtech.webank.model.Transaction;
import com.justinmtech.webank.model.User;
import com.justinmtech.webank.service.TransactionService;
import com.justinmtech.webank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    TransactionService transactionService;

    @RequestMapping("/dashboard")
    public String getDashboard(Model model) {
        User user = getUserService().getCurrentAuthenticatedUser();
        model.addAttribute("user", user);
        try {
            List<Transaction> transactionList = getTransactionService().getAllTransactionsByUsername(user.getUsername()).join();
            model.addAttribute("transactions", transactionList);
            model.addAttribute("transactionsCount", transactionList.size());
        } catch (ExecutionException | InterruptedException e) {
            return "error-page";
        }

        return "dashboard";
    }

    public UserService getUserService() {
        return userService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }
}
