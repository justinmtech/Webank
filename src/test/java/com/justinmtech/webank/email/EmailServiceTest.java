package com.justinmtech.webank.email;

import com.justinmtech.webank.exceptions.email.EmailError;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void sendEmail() throws EmailError {
        emailService.send("justin.mitchell09@gmail.com", "test");
    }
}