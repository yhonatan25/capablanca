package com.goltqup.capablanca;

import com.goltqup.capablanca.domain.api.Group;
import com.goltqup.capablanca.domain.api.Tournament;

import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

public class ExpectedTournamentSupplier {
    private static final String TOURNAMENT_NAME = "FIFA";
    private static final String TOURNAMENT_PLACE = "Russia";
    private static final int TOURNAMENT_YEAR = 2018;

    public static Tournament getExpectedTournament() {
        return new Tournament(TOURNAMENT_NAME, TOURNAMENT_PLACE, TOURNAMENT_YEAR, getGroupSet());
    }

    private static Set<Group> getGroupSet() {
        return stream("A B C D E F G H".split(" "))
                .map(Group::new)
                .collect(toSet());
    }

}
