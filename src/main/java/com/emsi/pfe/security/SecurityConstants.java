package com.emsi.pfe.security;

public class SecurityConstants {
    public static final String TOKEN_SECRET="inna_lilah_awdi_a_hmad";
    public static final Long ACCESS_TOKEN_EXPIRATION=999999999L*99*60*1000l;
    public static final Long REFRESH_TOKEN_EXPIRATION=48*60*60*1000l;
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String ROLES="roles";
    public static final String ACCESS_TOKEN="access_token";
    public static final String REFRESH_TOKEN="refresh_token";
    public static final String PASSENGER="PASSENGER";
    public static final String DRIVER="DRIVER";
    public static final String ERROR_MESSAGE="error_message";
    public static final String ERROR="error";
    public static final String EMAIL="email";
    public static final String REFRESH_TOKE_MISSING_ERROR_MESSAGE="Refresh token is missing";
    public static final String EMAIL_DUPLICATION_EXCEPTION_MESSAGE="Email already exists !";
    public static final String EMAIL_NOT_FOUND_EXCEPTION_MESSAGE ="Email not found !";
    public static final String PASSWORD_INCORRECT = "Password is incorrect !";

    public static final String APP_EMAIL ="kay.technologie.gdr@gmail.com" ;
    public static final String RESET_PASSWORD_TOKEN ="Reset password token" ;
    public static final String INCORRECT_RESET_PASSWORD_TOKEN ="Incorrect reset password token" ;
}
