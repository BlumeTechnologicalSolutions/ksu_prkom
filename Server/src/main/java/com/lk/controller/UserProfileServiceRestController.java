package com.lk.controller;

import com.lk.entity.Response;
import com.lk.entity.User;
import com.lk.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/UserProfile")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserProfileServiceRestController {

    private UserProfileService userProfileService;
    private Logger logger = LoggerFactory.getLogger(UserProfileServiceRestController.class);

    @Inject
    public UserProfileServiceRestController(@Named("userProfileService") UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @RequestMapping(value = "/getUserDocuments", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Response getUserDocuments (HttpServletRequest request) {
        return userProfileService.getUserDocuments(request);
    }

    @RequestMapping(value = "/getUserEducation", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Response getUserEducation (HttpServletRequest request) {
        return userProfileService.getUserEducation(request);
    }

    @RequestMapping(value = "/getUserAddress", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Response getUserAddress (HttpServletRequest request) {
        return userProfileService.getUserAddress(request);
    }

    @RequestMapping(value = "/getUserRepresentative", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Response getUserRepresentative (HttpServletRequest request) {
        return userProfileService.getUserRepresentative(request);
    }
    //saves
    @RequestMapping(value = "/saveUserEducation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Response saveUserEducation (@RequestBody User user, HttpServletRequest request) {
        return userProfileService.saveUserEducation(user, request);
    }

    @RequestMapping(value = "/saveUserAddress", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Response saveUserAddress(@RequestBody User user, HttpServletRequest request) {
        return userProfileService.saveUserAddress(user, request);
    }

    @RequestMapping(value = "/saveUserRepresentative", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Response saveUserRepresentative(@RequestBody User user, HttpServletRequest request) {
        return userProfileService.saveUserRepresentative(user, request);
    }

    @RequestMapping(value = "/saveUserMainInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Response saveUserMainInfo(@RequestBody User user, HttpServletRequest request) {
        return userProfileService.saveUserMainInfo(user, request);
    }

    @RequestMapping(value = "/saveUserDocuments", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Response saveUserDocuments(@RequestBody User user, HttpServletRequest request) {
        return userProfileService.saveUserDocuments(user, request);
    }

}
