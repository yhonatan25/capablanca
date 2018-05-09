package com.goltqup.capablanca.domain.mongo;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Document(collection = "tournament")
@Getter
@Setter
public class TournamentDocument {

    @Id
    private String id;

    private String name;

    private String place;

    private int year;

    @Field("groups")
    private Set<GroupDocument> groupDocumentSet;

    @Indexed
    private String encodedId;

    public TournamentDocument(final String name, final String place, final int year, final String encodedId, final Set<GroupDocument> groupDocumentSet) {
        this.name = name;
        this.place = place;
        this.year = year;
        this.encodedId = encodedId;
        this.groupDocumentSet = groupDocumentSet;
    }

}
