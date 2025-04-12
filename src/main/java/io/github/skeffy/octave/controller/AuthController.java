package io.github.skeffy.octave.controller;

import io.github.skeffy.octave.dao.UserDao;
import io.github.skeffy.octave.exception.DaoException;
import io.github.skeffy.octave.model.LoginResponseDto;
import io.github.skeffy.octave.model.RegisterUserDto;
import io.github.skeffy.octave.model.User;
import io.github.skeffy.octave.security.CustomAuthenticationManager;
import io.github.skeffy.octave.security.googleoauth.GoogleUserService;
import io.github.skeffy.octave.security.jwt.TokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/")
public class AuthController {

    private final CustomAuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final GoogleUserService googleUserService;
    private final UserDao userDao;

    public AuthController(CustomAuthenticationManager authenticationManager, TokenProvider tokenProvider, UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.googleUserService = new GoogleUserService();
        this.userDao = userDao;
    }

    @PostMapping("/login")
    public LoginResponseDto loginWithGoogle(@RequestBody Map<String, String> payload) {
        String googleToken = payload.get("token");
        String email = googleUserService.get(googleToken).getEmail();
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            //user does not exist
            //redirect to profile creation
            return null;
        }
        //issue jwt
        //redirect to profile
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getId(), user.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        String refresh = tokenProvider.createRefresh(authentication);
        return new LoginResponseDto(jwt, refresh, user);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public LoginResponseDto register(@Valid @RequestBody RegisterUserDto newUser) {
        try {
            if (userDao.getUserByUsername(newUser.getUsername()) != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken");
            } else {
                User user = userDao.createUser(
                        new User(newUser.getUsername(), newUser.getName(), newUser.getEmail())
                );
                return new LoginResponseDto("jwt", "refresh", user);
            }
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed");
        }
    }

    @PostMapping("/refresh")
    public void refresh() {

    }

    @PostMapping("/logout")
    public void logout(@RequestBody String token, @RequestBody String refresh) {

    }
}
