package com.team16.project.registration.phone;

import static spark.Spark.post;
/**
* This controller sets the API for phone verification
* @author  Team 16
*/
public class PhoneRegistrationController {
    private static final String PHONE_VERIFICATION_API = "/registration/phone";
    private PhoneRegistrationService phoneRegistrationService;

    public PhoneRegistrationController() {
        phoneRegistrationService = new PhoneRegistrationService();
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(PHONE_VERIFICATION_API, (req, res) ->
        {
            return phoneRegistrationService.verfyPhone(req.body());
        });
    }
}
