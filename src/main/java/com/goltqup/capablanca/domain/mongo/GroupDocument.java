package com.goltqup.capablanca.domain.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Document
@Getter
@Setter
public class GroupDocument {

    @Id
    private String id;

    private String name;

    @Indexed
    private String encodedId;

    private TeamDocument first;

    private TeamDocument second;

    @Field("teams")
    private Set<TeamDocument> teamDocumentSet;

    public GroupDocument(final String name, final String encodedId) {
        this.name = name;
        this.encodedId = encodedId;
    }

}