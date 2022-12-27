package com.emsi.pfe.controller;

import com.emsi.pfe.constant.SecurityAPI;
import com.emsi.pfe.exception.EmailDuplicationException;
import com.emsi.pfe.exception.RefreshTokenMissingException;
import com.emsi.pfe.request.UserRegisterRequest;
import com.emsi.pfe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(SecurityAPI.DRIVER_REGISTER)
    public void driverRegister(@RequestBody UserRegisterRequest userRegisterRequest) throws EmailDuplicationException {
        userService.driverRegister(userRegisterRequest);
    }

    @PostMapping(SecurityAPI.PASSENGER_REGISTER)
    public void passengerRegister(@RequestBody UserRegisterRequest userRegisterRequest) throws EmailDuplicationException {
        userService.passengerRegister(userRegisterRequest);
    }

    @PostMapping(SecurityAPI.TOKEN_REFRESH)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, RefreshTokenMissingException {
        userService.refreshToken(request,response);
    }


}
