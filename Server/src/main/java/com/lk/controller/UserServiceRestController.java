package com.lk.controller;

import com.lk.entity.Response;
import com.lk.entity.User;
import com.lk.entity.UserRegistration;
import com.lk.persistence.Authentification;
import com.lk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/UserService")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserServiceRestController {

    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserServiceRestController.class);

    @Inject
    public UserServiceRestController(@Named("userService") UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Response authorization (@RequestBody User user) {
        return userService.authorization(user);
    }

    @RequestMapping(value = "/getUserByToken", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Response getUserByToken (final HttpServletRequest request) {
        return userService.getUserByToken(request);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Response authorization (@RequestBody UserRegistration user) {
        return userService.registration(user);
    }

    @RequestMapping(value = "/remember", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Response remember (@RequestBody User user) {
        return userService.remember(user);
    }


    @RequestMapping(value = "/getRegistrationSecretQuestions", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Response getRegistrationSecretQuestions () {
        return userService.getRegistrationSecretQuestions();
    }
    /*@RequestMapping(value = "/get", method = RequestMethod.GET) // JUST EXAMPLE
    @ResponseStatus(HttpStatus.OK)
    public Response remember (@RequestBody User user, final HttpServletRequest request, final HttpServletResponse response) {
        if(new Authentification().getUserByCookie(request) != null) {
            return userService.remember(user);
        } else {
            response.sendError(403, "403 Forbidden");
        }
    }*/

}
