package com.williamhill.scoreboards.service;

import com.williamhill.scoreboards.exceptions.EventNotFoundException;
import com.williamhill.scoreboards.exceptions.InvalidVersionException;
import com.williamhill.scoreboards.model.Event;
import com.williamhill.scoreboards.repository.EventRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTests {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;
    private Event eventVersion1;

    @BeforeEach
    public void setUp() {
        event = new Event();
        event.setId(1);
        event.setScoreTeamA(1);
        event.setScoreTeamB(2);
        event.setTeamA("Team A");
        event.setTeamB("Team B");
        event.setVersion(0);

        eventVersion1 = new Event();
        eventVersion1.setId(1);
        eventVersion1.setScoreTeamA(1);
        eventVersion1.setScoreTeamB(2);
        eventVersion1.setTeamA("Team A");
        eventVersion1.setTeamB("Team B");
        eventVersion1.setVersion(1);
    }

    @Test
    public void getAllEvents_successfully() {
        when(eventRepository.findAll())
                .thenReturn(List.of(event));
        var result = eventService.getAllEvents();

        Assertions.assertEquals(result, List.of(event));
    }

    @Test
    public void getEventById_successfully() {
        when(eventRepository.findById(Long.valueOf(1)))
                .thenReturn(Optional.of(event));
        var result = eventService.getEventById(String.valueOf(event.getId()));

        Assertions.assertEquals(result, event);
    }

    @Test
    public void getEventById_unsuccessfully() {
        when(eventRepository.findById(Long.valueOf(0)))
                .thenThrow(EventNotFoundException.class);

        EventNotFoundException thrown = assertThrows(
                EventNotFoundException.class,
                () -> eventService.getEventById("0")
        );
        Assertions.assertEquals(thrown.getClass(), EventNotFoundException.class);
    }

    @Test
    public void saveEvent_successfully() {
        when(eventRepository.save(event))
                .thenReturn(event);
        var result = eventService.saveEvent(event);

        Assertions.assertEquals(result, event);
    }

    @Test
    public void updateEvent_successfully() {
        when(eventRepository.findById(event.getId()))
                .thenReturn(Optional.of(event));
        when(eventRepository.updateScoreById(event.getId(), event.getScoreTeamA(), event.getScoreTeamB()))
                .thenReturn(1);

        var result = eventService.modifyEventScore(event);

        Assertions.assertEquals(result, 1);
    }

    @Test
    public void updateEvent_throwEventNotFoundException() {
        when(eventRepository.findById(event.getId()))
                .thenThrow(EventNotFoundException.class);

        EventNotFoundException thrown = assertThrows(
                EventNotFoundException.class,
                () -> eventService.modifyEventScore(event)
        );
        Assertions.assertEquals(thrown.getClass(), EventNotFoundException.class);
    }

    @Test
    public void updateEvent_throwInvalidVersionException() {
        when(eventRepository.findById(event.getId()))
                .thenReturn(Optional.of(eventVersion1));

        InvalidVersionException thrown = assertThrows(
                InvalidVersionException.class,
                () -> eventService.modifyEventScore(event)
        );
        Assertions.assertEquals(thrown.getClass(), InvalidVersionException.class);
    }

}
