package com.backend.controller;

import com.backend.model.User;
import com.backend.security.JwtRequest;
import com.backend.security.JwtResponse;
import com.backend.security.JwtTokenUtil;
import com.backend.service.impl.EmailServiceImpl;
import com.backend.service.impl.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Autowired
    private EmailServiceImpl emailService;

    private static final String confirmationSubject = "Confirmation mail from Kick-off app";
    private static final String confirmationBody = """
                Hey there!\s
                We wanted to reach out and let you know we are 
                thrilled to welcome you to Kick-off app! Thanks for signing up!
                We want to make sure you are having the best experience here,
                so if there is anything we can do for you, please let us know 
                by sending us an email through the form in the Contact page.\s
                Enjoy the app and have a great day!
                            
            Kick-off app""";

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
        User userToBeSaved = userDetailsService.save(user);
        if (userToBeSaved == null) {
            return new ResponseEntity<>("The given user already exists in the database " +
                    "or the provided information is incorrect.", HttpStatus.BAD_REQUEST);
        }

        emailService.sendEmail(userToBeSaved.getEmail(), confirmationSubject, confirmationBody);
        return ResponseEntity.ok(userToBeSaved);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}