package com.lk.controller;

import com.lk.entity.Response;
import com.lk.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;

@RestController
@RequestMapping("/EventService")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EventServiceRestController {

    private EventService eventService;

    @Inject
    public EventServiceRestController(@Named("eventService") EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/getEvents", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Response getEvents () { return eventService.getEvents(); }

}
