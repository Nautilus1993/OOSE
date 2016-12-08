package com.team16.project.registration.phone;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import javax.mail.MessagingException;

import static org.junit.Assert.*;

public class PhoneRegistrationServiceTest {
    PhoneRegistrationService phoneRegistrationService = new PhoneRegistrationService();

    public PhoneRegistrationServiceTest() throws ParseException, MessagingException {
    }

    int registrationCode = phoneRegistrationService.verfyPhone("{\"toBeVerified\":\"4433104264\"}");

    @Test
    // Do not run this test too often. This test actually sends text message to my phone :)
    public void verifyPhone() throws Exception {
        System.out.print(registrationCode);
        assertTrue(String.valueOf(registrationCode).length() == 6
                   & registrationCode >= 100000
                   & registrationCode <= 999999);
    }

}