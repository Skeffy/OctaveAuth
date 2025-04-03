package io.github.skeffy.octave.controller;

import io.github.skeffy.octave.dao.UserDao;
import io.github.skeffy.octave.security.googleoauth.GoogleUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@RestController
@RequestMapping("/")
public class AuthController {

    private final GoogleUserService googleUserService;
    private final UserDao userDao;

    public AuthController(UserDao userDao) {
        this.googleUserService = new GoogleUserService();
        this.userDao = userDao;
    }

    @PostMapping("/login")
    public RedirectView loginWithGoogle(@RequestBody Map<String, String> payload) {
        String googleToken = payload.get("token");
        String email = googleUserService.get(googleToken).getEmail();
        if (userDao.getUserIdByEmail(email) == null) {
            //user does not exist
            //redirect to profile creation
        }
        //issue jwt
        //redirect to profile

        return null;
    }
}
