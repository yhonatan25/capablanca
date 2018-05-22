package com.goltqup.capablanca.domain.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Document(collection = "group")
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

    @Field("matches")
    private Set<MatchDocument> matchDocumentSet;

    public GroupDocument(final String name,
                         final String encodedId,
                         final Set<TeamDocument> teamDocumentSet,
                         final Set<MatchDocument> matchDocumentSet) {
        this.name = name;
        this.encodedId = encodedId;
        this.teamDocumentSet = teamDocumentSet;
        this.matchDocumentSet = matchDocumentSet;
    }
}
