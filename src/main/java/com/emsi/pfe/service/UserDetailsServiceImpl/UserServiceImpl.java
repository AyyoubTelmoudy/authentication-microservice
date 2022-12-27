package com.emsi.pfe.service.UserDetailsServiceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.emsi.pfe.constant.SecurityConstants;
import com.emsi.pfe.entity.Role;
import com.emsi.pfe.entity.User;
import com.emsi.pfe.exception.EmailDuplicationException;
import com.emsi.pfe.exception.RefreshTokenMissingException;
import com.emsi.pfe.repository.RoleRepository;
import com.emsi.pfe.repository.UserRepository;
import com.emsi.pfe.request.UserRegisterRequest;
import com.emsi.pfe.security.UserDetailsImpl;
import com.emsi.pfe.service.UserService;
import com.emsi.pfe.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Utils utils;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(userRepository.findByEmail(email)!=null)
        {return new UserDetailsImpl(this.userRepository.findByEmail(email));}
        else
        {
            return  null;
        }
    }

    @Override
    public void driverRegister(UserRegisterRequest userRegisterRequest) throws EmailDuplicationException {
        if(userRepository.findByEmail(userRegisterRequest.getEmail())!=null)
            throw new EmailDuplicationException(SecurityConstants.EMAIL_DUPLICATION_EXCEPTION_MESSAGE);
        else
        {
            User user=new User();
            user.setEmail(userRegisterRequest.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
            user.setPublicId(utils.genereteRandomString(30));
            List<Role> roles=new ArrayList<Role>();
            roles.add(roleRepository.findByRole(SecurityConstants.DRIVER));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    @Override
    public void passengerRegister(UserRegisterRequest userRegisterRequest) throws EmailDuplicationException {
        if(userRepository.findByEmail(userRegisterRequest.getEmail())!=null)
            throw new EmailDuplicationException(SecurityConstants.EMAIL_DUPLICATION_EXCEPTION_MESSAGE);
        else
        {
            User user=new User();
            user.setEmail(userRegisterRequest.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
            user.setPublicId(utils.genereteRandomString(30));
            List<Role> roles=new ArrayList<Role>();
            roles.add(roleRepository.findByRole(SecurityConstants.PASSENGER));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, RefreshTokenMissingException {
        String authorizationHeader = request.getHeader(SecurityConstants.REFRESH_TOKEN);
        if(authorizationHeader != null && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                String refresh_token = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.TOKEN_SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                UserDetailsImpl userDetails= this.loadUser(email);
                String access_token = JWT.create()
                        .withSubject(userDetails.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.ACCESS_TOKEN_EXPIRATION))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(SecurityConstants.ROLES, userDetails.getRoles())
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put(SecurityConstants.ACCESS_TOKEN, access_token);
                tokens.put(SecurityConstants.REFRESH_TOKEN, refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            }catch (Exception exception) {
                response.setHeader(SecurityConstants.ERROR, exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put(SecurityConstants.ERROR_MESSAGE, exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RefreshTokenMissingException(SecurityConstants.REFRESH_TOKE_MISSING_ERROR_MESSAGE);
        }
    }

    @Override
    public UserDetailsImpl loadUser(String email) {
        User user=userRepository.findByEmail(email);
        if(user!=null)
        {return new UserDetailsImpl(user);}
        else
        {
            return null;
        }
    }
}
