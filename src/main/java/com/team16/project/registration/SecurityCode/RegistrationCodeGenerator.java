package com.team16.project.registration.SecurityCode;

import java.util.Random;
/**
 * This class is a random number generating class used for creating registration code.
 * @author OOSE_Team16
 */
public class RegistrationCodeGenerator {
    private static final int MIN = 100000;
    private static final int MAX = 999999;
    private int registrationCode;

    public RegistrationCodeGenerator() {
        Random random = new Random();
        registrationCode = random.nextInt(MAX - MIN + 1) + MIN;
    }

    public int getRegistrationCode() {
        return registrationCode;
    }
}
