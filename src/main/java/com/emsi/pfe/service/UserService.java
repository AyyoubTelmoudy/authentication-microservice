package com.emsi.pfe.service;

import com.emsi.pfe.exception.EmailDuplicationException;
import com.emsi.pfe.exception.RefreshTokenMissingException;
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
}
