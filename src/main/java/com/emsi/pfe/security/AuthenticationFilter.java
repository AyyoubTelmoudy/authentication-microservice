package com.emsi.pfe.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.emsi.pfe.constant.SecurityConstants;
import com.emsi.pfe.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager)
    {this.authenticationManager = authenticationManager;}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  {
        try {
              LoginRequest credentials= new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
              return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>()));
           }
        catch (IOException e) {
              throw new RuntimeException(e);
          }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException{
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.TOKEN_SECRET.getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() +SecurityConstants.ACCESS_TOKEN_EXPIRATION))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(SecurityConstants.ROLES,user.getRoles())
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() +SecurityConstants.REFRESH_TOKEN_EXPIRATION))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put(SecurityConstants.ACCESS_TOKEN,access_token);
        tokens.put(SecurityConstants.REFRESH_TOKEN,refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> errors=new HashMap<>();

        if(e instanceof BadCredentialsException)
           errors.put(SecurityConstants.ERROR,SecurityConstants.PASSWORD_INCORRECT);
        else
            errors.put(SecurityConstants.ERROR,SecurityConstants.EMAIL_NOT_FOUND_EXCEPTION_MESSAGE);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        mapper.writeValue(out,errors);
    }
}
