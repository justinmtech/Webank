package com.justinmtech.webank.exceptions.email;

import jakarta.mail.MessagingException;

public class EmailError extends Exception {
    public EmailError(String to, String from, MessagingException e) {
        super("Email could not be sent to '" + to + "' from " + from);
        setStackTrace(e.getStackTrace());
    }
    public EmailError(String to, MessagingException e) {
        super("Email could not be sent to '" + to + "'");
        setStackTrace(e.getStackTrace());
    }
}
