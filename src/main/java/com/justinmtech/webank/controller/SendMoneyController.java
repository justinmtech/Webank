package com.justinmtech.webank.controller;

import com.justinmtech.webank.model.Transaction;
import com.justinmtech.webank.service.TransactionService;
import com.justinmtech.webank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("SameReturnValue")
@Controller
public class SendMoneyController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/send-money")
    public String getSendMoneyPage(Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        return "send-money";
    }

    @PostMapping("/send-money")
    public String sendMoney(@ModelAttribute Transaction transaction, Model model) {
        model.addAttribute("transaction", transaction);
        transaction.setSender(getUserService().getCurrentAuthenticatedUser().getUsername());
        try {
            getTransactionService().handleTransaction(transaction);
            return "money-sent";
        } catch (Exception e) {
            return "error-page";
        }
    }

    @RequestMapping("/money-sent")
    public String getMoneySentPage() {
        return "money-sent";
    }

    private UserService getUserService() {
        return userService;
    }

    private TransactionService getTransactionService() {
        return transactionService;
    }
}
