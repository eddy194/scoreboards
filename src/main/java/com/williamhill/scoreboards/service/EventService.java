package com.williamhill.scoreboards.service;

import com.williamhill.scoreboards.exceptions.EventNotFoundException;
import com.williamhill.scoreboards.exceptions.InvalidVersionException;
import com.williamhill.scoreboards.model.Event;
import com.williamhill.scoreboards.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(String id) {
        return eventRepository.findById(Long.parseLong(id)).orElseThrow(EventNotFoundException::new);
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public Integer modifyEventScore(Event event) {
        var eventFromDb = eventRepository.findById(event.getId()).orElseThrow(EventNotFoundException::new);
        if(eventFromDb.getVersion().equals(event.getVersion())) {
            return eventRepository.updateScoreById(event.getId(), event.getScoreTeamA(), event.getScoreTeamB());
        } else {
            throw new InvalidVersionException();
        }
    }
}
