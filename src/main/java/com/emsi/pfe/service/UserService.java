package com.emsi.pfe.service;

import com.emsi.pfe.exception.*;
import com.emsi.pfe.request.ForgetPasswordRequest;
import com.emsi.pfe.request.ForgottenPasswordResetRequest;
import com.emsi.pfe.request.PasswordResetRequest;
import com.emsi.pfe.request.UserRegisterRequest;
import com.emsi.pfe.security.UserDetailsImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserService {
    void driverRegister(UserRegisterRequest userRegisterRequest) throws EmailDuplicationException;

    void passengerRegister(UserRegisterRequest userRegisterRequest) throws EmailDuplicationException;

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, RefreshTokenMissingException;

    UserDetailsImpl loadUser(String email);
    void passwordReset(PasswordResetRequest passwordResetRequest) throws IncorrectPassword;

    void forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws EmailNotFoundException;

    void forgottenPasswordReset(ForgottenPasswordResetRequest forgottenPasswordResetRequest) throws EmailNotFoundException, IncorrectResetPasswordTokenException;
}
