package com.williamhill.scoreboards.controller;

import com.williamhill.scoreboards.model.Event;
import com.williamhill.scoreboards.service.EventService;

import java.time.Duration;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Controller
public class ScoreBoardsController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String homePage(Model model) {
        return "index";
    }

    @GetMapping(value = "/data/flux", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Object> streamDataFlux() {
        return Flux.interval(Duration.ofSeconds(1)).map(i -> eventService.getAllEvents());
    }

    @GetMapping(value = "/events")
    public ResponseEntity getEvents() {
        var result = eventService.getAllEvents();
        return new ResponseEntity<>(Arrays.toString(result.toArray()), HttpStatus.OK);
    }

    @GetMapping(value = "/event/score/{id}")
    public ResponseEntity getScoreByEventId(@PathVariable String id) {
        var result = eventService.getEventById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/event")
    public ResponseEntity addEvent(@Validated @RequestBody Event event) {
        var result = eventService.saveEvent(event);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/event/score")
    public ResponseEntity updateEvent(@RequestBody Event event) {
        var result = eventService.modifyEventScore(event) == 1 ? "Update successful" : "Update not successful";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
