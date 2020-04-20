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

    public Response registration(UserRegistration user, String code);

    public Response remember(String email);

    public Response sendRegistrationCode(String toEmail);

    public Response getRegistrationSecretQuestions();

}
