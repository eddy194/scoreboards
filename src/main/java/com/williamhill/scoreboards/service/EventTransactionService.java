package com.williamhill.scoreboards.service;

import com.williamhill.scoreboards.model.Event;
import com.williamhill.scoreboards.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class EventTransactionService {
    private final EventRepository eventRepository;

}
