package com.emsi.pfe.controller;

import com.emsi.pfe.exception.*;
import com.emsi.pfe.request.ForgetPasswordRequest;
import com.emsi.pfe.request.ForgottenPasswordResetRequest;
import com.emsi.pfe.security.SecurityAPI;
import com.emsi.pfe.request.PasswordResetRequest;
import com.emsi.pfe.request.UserRegisterRequest;
import com.emsi.pfe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequestMapping
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(SecurityAPI.DRIVER_REGISTER)
    public void driverRegister(@RequestBody @Valid UserRegisterRequest userRegisterRequest) throws EmailDuplicationException {
        userService.driverRegister(userRegisterRequest);
    }

    @PostMapping(SecurityAPI.PASSENGER_REGISTER)
    public void passengerRegister(@RequestBody @Valid UserRegisterRequest userRegisterRequest) throws EmailDuplicationException {
        userService.passengerRegister(userRegisterRequest);
    }

    @PostMapping(SecurityAPI.TOKEN_REFRESH)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, RefreshTokenMissingException {
        userService.refreshToken(request,response);
    }

    @PostMapping(SecurityAPI.PASSWORD_RESET)
    public void passwordReset(@RequestBody @Valid PasswordResetRequest passwordResetRequest) throws IncorrectPassword {
        userService.passwordReset(passwordResetRequest);
    }

    @PostMapping(SecurityAPI.FORGET_PASSWORD)
    public void forgetPassword(@RequestBody @Valid ForgetPasswordRequest forgetPasswordRequest) throws EmailNotFoundException {
        userService.forgetPassword(forgetPasswordRequest);
    }

    @PostMapping(SecurityAPI.RESET_FORGOTTEN_PASSWORD)
    public void forgottenPasswordReset(@RequestBody @Valid ForgottenPasswordResetRequest forgottenPasswordResetRequest) throws EmailNotFoundException, IncorrectResetPasswordTokenException {
        userService.forgottenPasswordReset(forgottenPasswordResetRequest);
    }


}
