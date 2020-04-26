package com.lk.service;

import com.lk.entity.Response;
import com.lk.entity.User;
import com.lk.entity.UserRegistration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/UserService")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface UserProfileService {

    public Response getUserDocuments(HttpServletRequest request);

    public Response getUserEducation(HttpServletRequest request);

    public Response getUserRepresentative(HttpServletRequest request);

    public Response getUserAddress(HttpServletRequest request);

    public Response saveUserMainInfo(User user, HttpServletRequest request);

    public Response saveUserEducation(User user, HttpServletRequest request);

    public Response saveUserAddress(User user, HttpServletRequest request);

    public Response saveUserRepresentative(User user, HttpServletRequest request);

    public Response saveUserDocuments(User user, HttpServletRequest request);


}




