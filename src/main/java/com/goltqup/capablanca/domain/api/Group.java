package com.goltqup.capablanca.domain.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Set;

import static java.util.Base64.getEncoder;
import static java.util.Collections.unmodifiableSet;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notEmpty;

@Getter
public class Group {

    private final String id;
    private final String name;
    @JsonProperty("teams")
    private final Set<Team> teamSet;
    @JsonProperty("matches")
    private final Set<Match> matchSet;

    @JsonCreator
    public Group(@JsonProperty("name") final String name,
                 @JsonProperty("teams") final Set<Team> teamSet,
                 @JsonProperty("matches") final Set<Match> matchSet) {
        hasText(name, "Group name must have text.");
        notEmpty(teamSet, "Group team set must not be empty.");
        notEmpty(matchSet, "Group match set must not be empty.");
        this.name = name;
        this.id = getEncoder().encodeToString(name.getBytes());
        this.teamSet = unmodifiableSet(teamSet);
        this.matchSet = unmodifiableSet(matchSet);
    }

    public boolean equals(final Object object) {
        if (object == this) return true;
        if (!(object instanceof Group)) return false;
        final Group other = (Group) object;
        return id.equals(other.id) && name.equals(other.name);
    }

    public int hashCode() {
        return id.hashCode();
    }

    public String toString() {
        return "Group(id=" + this.getId() + ", name=" + this.getName() + ", teams=" + this.getTeamSet() + "matches=" + this.getMatchSet() + ")";
    }
}
