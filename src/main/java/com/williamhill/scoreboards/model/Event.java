package com.williamhill.scoreboards.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(updatable = false)
    private String teamA;

    @Column(updatable = false)
    private String teamB;

    private Integer scoreTeamA;

    private Integer scoreTeamB;

    @Version
    private Integer version;

}
