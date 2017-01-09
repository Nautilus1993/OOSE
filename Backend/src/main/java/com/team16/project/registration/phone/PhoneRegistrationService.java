package com.team16.project.registration.phone;

import com.team16.project.registration.SecurityCode.RegistrationCodeGenerator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;

/**
* This class utlizes the text message transit services provided by  major phone carriers.
* It sends an email to a phone number with an additional domain string "@xxx.com". The
* phone carrier will then translate the email to a text message and send it to the
* recipient's phone number.
* @author  Team 16
*/
class PhoneRegistrationService {
    private static final String TO_BE_VERIFIED = "toBeVerified";
    private static final String ATT_TRANSIT = "@txt.att.net";
    private static final String T_MOBILE_TRANSIT ="@tmomail.net";
    private static final String SPRINT_TRANSIT = "@messaging.sprintpcs.com";
    private static final String VERIZON_TRANSIT = "@vtext.com";
    private JSONParser parser;

    PhoneRegistrationService() {
        parser = new JSONParser();
    }

    int verfyPhone(String body) throws ParseException, MessagingException {
        JSONObject jsonObject = (JSONObject) parser.parse(body);
        String phoneToBeVerified =  (String) jsonObject.get(TO_BE_VERIFIED);
        String att = phoneToBeVerified + ATT_TRANSIT;
        String tmobile = phoneToBeVerified + T_MOBILE_TRANSIT;
        String sprint = phoneToBeVerified + SPRINT_TRANSIT;
        String verizon = phoneToBeVerified + VERIZON_TRANSIT;
        RegistrationCodeGenerator registrationCodeGenerator = new RegistrationCodeGenerator();
        PhoneRegistrationCode attMsg = new PhoneRegistrationCode(att,
                registrationCodeGenerator.getRegistrationCode());
        PhoneRegistrationCode tmobileMsg = new PhoneRegistrationCode(tmobile,
                registrationCodeGenerator.getRegistrationCode());
        PhoneRegistrationCode sprintMsg = new PhoneRegistrationCode(sprint,
                registrationCodeGenerator.getRegistrationCode());
        PhoneRegistrationCode verizonMsg = new PhoneRegistrationCode(verizon,
                registrationCodeGenerator.getRegistrationCode());
        return registrationCodeGenerator.getRegistrationCode();
    }
}
