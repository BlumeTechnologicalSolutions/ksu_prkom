package com.lk.service;

import com.lk.entity.Event;
import com.lk.entity.Response;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/EventService")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface EventService {

    public Response getEventEducation(Integer education);
    public Response getEvents();
    public Response addEvent(Event event);
    public Response removeEvent(Event event);

}
