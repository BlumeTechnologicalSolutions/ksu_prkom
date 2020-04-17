package com.lk.service;

import com.lk.entity.Response;
import com.lk.entity.User;
import com.lk.entity.UserRegistration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/UserService")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface UserService {

    public Response getUserByToken(HttpServletRequest request);

    public Response authorization (User user );

    public Response registration(UserRegistration user);

    public Response remember(User user);

    public Response getRegistrationSecretQuestions();

}
