package com.emsi.pfe.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Component
public class SecurityUtils {
    public String generateCurrentUserToken(HttpServletRequest request)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email= (String) auth.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.TOKEN_SECRET.getBytes());
        String access_token = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() +SecurityConstants.ACCESS_TOKEN_EXPIRATION))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(SecurityConstants.ROLES,this.getCurrentUserRoles())
                .sign(algorithm);
        return access_token;
    }
    public String getCurrentUserEmail()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email= (String) auth.getPrincipal();
        return email;
    }
    public List<String> getCurrentUserRoles()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles=new ArrayList<>();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority().substring(5));
        }
        return roles;
    }
}
