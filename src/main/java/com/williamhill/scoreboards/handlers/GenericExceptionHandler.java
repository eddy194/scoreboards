package com.williamhill.scoreboards.handlers;

import com.williamhill.scoreboards.exceptions.EventNotFoundException;
import com.williamhill.scoreboards.exceptions.InvalidVersionException;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(value = {InvalidVersionException.class})
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public String invalidVersionError(Exception e) {
        return "The supplied event version is out of date.";
    }

    @ExceptionHandler(value = {EventNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String eventNotFoundError(Exception e) {
        return "Event not found.";
    }
}
