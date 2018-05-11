package com.goltqup.capablanca;

import com.goltqup.capablanca.domain.api.Group;
import com.goltqup.capablanca.domain.api.Team;
import com.goltqup.capablanca.domain.api.Tournament;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

public class TournamentAssert {
    private static final String TOURNAMENT_NAME = "FIFA";
    private static final String TOURNAMENT_PLACE = "Russia";
    private static final int TOURNAMENT_YEAR = 2018;

    public static Tournament getExpectedTournament() {
        return new Tournament(TOURNAMENT_NAME, TOURNAMENT_PLACE, TOURNAMENT_YEAR, getGroupSet());
    }

    private static Set<Group> getGroupSet() {
        return getTournamentGroupsMap().entrySet().stream()
                .map(stringListEntry -> new Group(stringListEntry.getKey(),
                        stringListEntry.getValue().stream()
                                .map(s -> new Team.TeamBuilder(s).build()).collect(toSet())))
                .collect(toSet());
    }

    private static Map<String, List<String>> getTournamentGroupsMap() {
        final Map<String, List<String>> tournamentGroupsMap = new HashMap<>();
        tournamentGroupsMap.put("A", asList("Russia", "Saudi_Arabia", "Egypt", "Uruguay"));
        tournamentGroupsMap.put("B", asList("Portugal", "Spain", "Morocco", "IR Iran"));
        tournamentGroupsMap.put("C", asList("France", "Australia", "Peru", "Denmark"));
        tournamentGroupsMap.put("D", asList("Argentina", "Iceland", "Croatia", "Nigeria"));
        tournamentGroupsMap.put("E", asList("Brazil", "Switzerland", "Costa_Rica", "Serbia"));
        tournamentGroupsMap.put("F", asList("Germany", "Mexico", "Sweden", "Korea_Republic"));
        tournamentGroupsMap.put("G", asList("Belgium", "Panama", "Tunisia", "England"));
        tournamentGroupsMap.put("H", asList("Poland", "Senegal", "Colombia", "Japan"));
        return tournamentGroupsMap;
    }

    public static boolean tournamentMatchesExpected(final Tournament actualTournament, final Tournament expectedTournament) {
        return expectedTournament.equals(actualTournament)
                && groupSetsAreEquals(actualTournament.getGroupSet(), expectedTournament.getGroupSet());
    }

    private static boolean groupSetsAreEquals(final Set<Group> actualGroupSet, final Set<Group> expectedGroupSet) {
        return groupSetMatchesExpected(actualGroupSet, expectedGroupSet)
                && groupSetsHaveTheSameSize(actualGroupSet, expectedGroupSet);
    }

    private static boolean groupSetMatchesExpected(final Set<Group> actualGroupSet, final Set<Group> expectedGroupSet) {
        return expectedGroupSet.stream()
                .allMatch(expectedGroup -> groupMatchesExpected(getActualGroup(actualGroupSet, expectedGroup),
                        expectedGroup));
    }

    private static Group getActualGroup(final Set<Group> actualGroupSet, final Group expectedGroup) {
        return actualGroupSet.stream()
                .filter(expectedGroup::equals)
                .findFirst()
                .orElse(null);
    }

    private static boolean groupSetsHaveTheSameSize(final Set<Group> actualGroupSet, final Set<Group> expectedGroupSet) {
        return expectedGroupSet.size() == actualGroupSet.size();
    }

    private static boolean groupMatchesExpected(final Group actualGroup, final Group expectedGroup) {
        return actualGroup != null && teamSetsAreEquals(actualGroup.getTeamSet(), expectedGroup.getTeamSet());
    }

    private static boolean teamSetsAreEquals(final Set<Team> actualTeamSet, final Set<Team> expectedTeamSet) {
        return teamSetMatchesExpected(actualTeamSet, expectedTeamSet)
                && teamSetsHaveTheSameSize(actualTeamSet, expectedTeamSet);
    }

    private static boolean teamSetMatchesExpected(final Set<Team> actualTeamSet, final Set<Team> expectedTeamSet) {
        return expectedTeamSet.containsAll(actualTeamSet);
    }

    private static boolean teamSetsHaveTheSameSize(final Set<Team> actualTeamSet, final Set<Team> expectedTeamSet) {
        return expectedTeamSet.size() == actualTeamSet.size();
    }

}
