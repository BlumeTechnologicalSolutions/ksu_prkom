package com.lk.service;


import com.lk.entity.Response;
import com.lk.entity.User;
import com.lk.entity.UserRegistration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/UserService")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface UserService {

    public User getUserByToken(String tokenInfo);

    public Response authorization (User user );

    public Response registration(UserRegistration user);

}
