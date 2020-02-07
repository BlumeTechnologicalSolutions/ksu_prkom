package com.lk.controller;

import com.lk.entity.User;
import com.lk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

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
    public User authorization (@RequestBody User user, final HttpServletRequest request) {
        return userService.authorization(user);
    }

    @RequestMapping(value = "/getUserByToken/{token}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUserByToken (@PathVariable("token") String tokenInfo, final HttpServletRequest request) {
        return userService.getUserByToken(tokenInfo);
    }



}
