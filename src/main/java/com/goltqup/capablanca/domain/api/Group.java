package com.goltqup.capablanca.domain.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import static java.util.Base64.getEncoder;

@Getter
public class Group {

    private final String id;
    private final String name;

    @JsonCreator
    public Group(@JsonProperty("name") final String name) {
        this.name = name;
        this.id = getEncoder().encodeToString(name.getBytes());
    }

    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof Group)) return false;
        final Group other = (Group) object;
        return id.equals(other.id) && name.equals(other.name);

    }

    public int hashCode() {
        return id.hashCode();
    }

    public String toString() {
        return "Group(id=" + this.getId() + ", name=" + this.getName() + ")";
    }
}
