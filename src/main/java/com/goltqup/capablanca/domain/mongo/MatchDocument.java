package com.goltqup.capablanca.domain.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
public class MatchDocument {
    @Id
    private String id;

    private String stadium;

    private LocalDateTime schedule;

    @Indexed
    private String encodedId;

    private String localTeam;

    private int localGoals;

    private String visitorTeam;

    private int visitorGoals;

    public MatchDocument(final String encodedId,
                         final String stadium,
                         final LocalDateTime schedule,
                         final String localTeam,
                         final String visitorTeam) {
        this.encodedId = encodedId;
        this.stadium = stadium;
        this.schedule = schedule;
        this.localTeam = localTeam;
        this.visitorTeam = visitorTeam;
    }

}
