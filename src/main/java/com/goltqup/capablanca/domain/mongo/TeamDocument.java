package com.goltqup.capablanca.domain.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class TeamDocument {

    @Id
    private String id;

    @Indexed
    private String name;

    private int playedMatches;

    private int wonMatches;

    private int drawMatches;

    private int defeatedMatches;

    private int goalsFor;

    private int goalsAgainst;

    private int goalDifference;

    private int points;

}
