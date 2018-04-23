package com.goltqup.capablanca.domain.mongo;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Tournament {

    @Id
    private String id;

    private String name;

    private String place;

    private int year;

    @Indexed
    private String encodedId;

    public Tournament(final String name, final String place, final int year, final String encodedId) {
        this.name = name;
        this.place = place;
        this.year = year;
        this.encodedId = encodedId;
    }

}
