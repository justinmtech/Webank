package com.justinmtech.webank.email;

import com.justinmtech.webank.exceptions.email.EmailError;

public interface EmailSender {
    void send(String to, String email) throws EmailError;
}
