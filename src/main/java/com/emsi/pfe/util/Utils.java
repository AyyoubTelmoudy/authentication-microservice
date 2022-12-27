package com.emsi.pfe.util;

import java.security.SecureRandom;
import java.util.Random;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    private final static Random RANDOM=new SecureRandom();
    private final static String ALPHANUM="01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz";

    public String genereteRandomString(int length)
    {
        StringBuilder returnvalue=new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnvalue.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return new String(returnvalue);
    }

    public static Object getAuthenticatedUser()
    {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
