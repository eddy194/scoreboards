package com.williamhill.scoreboards.repository;

import com.williamhill.scoreboards.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<Event> findById(Long eventId);

    @Modifying
    @Query("update Event e set e.scoreTeamA = :scoreTeamA, e.scoreTeamB = :scoreTeamB where e.id = :eventId")
    Integer updateScoreById(Long eventId, Integer scoreTeamA, Integer scoreTeamB);

}
