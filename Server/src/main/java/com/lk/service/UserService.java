package com.lk.service;


import com.lk.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/UserService")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface UserService {

    public User getUserByToken(String tokenInfo);

    public User authorization (User user );

}
