package com.goltqup.capablanca;

import com.goltqup.capablanca.config.GroupDTO;
import com.goltqup.capablanca.domain.api.Group;
import com.goltqup.capablanca.domain.api.Match;
import com.goltqup.capablanca.domain.api.Match.MatchBuilder;
import com.goltqup.capablanca.domain.api.Team;
import com.goltqup.capablanca.domain.api.Tournament;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Integer.parseInt;
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
                .map(stringGroupDTOEntry -> new Group(stringGroupDTOEntry.getKey(),
                        stringGroupDTOEntry.getValue().getTeamList().stream()
                                .map(s -> new Team.TeamBuilder(s).build()).collect(toSet()),
                        stringGroupDTOEntry.getValue().getMatchList().stream()
                                .map(TournamentAssert::getMatch).collect(toSet())))
                .collect(toSet());
    }

    private static Match getMatch(final String matchString) {
        final String[] groupArray = matchString.split(" ");
        final String stadium = groupArray[0];
        final LocalDateTime schedule = LocalDateTime.of(parseInt(groupArray[1]),
                parseInt(groupArray[2]),
                parseInt(groupArray[3]),
                parseInt(groupArray[4]),
                parseInt(groupArray[5]));
        final String localTeam = groupArray[6];
        final String visitorTeam = groupArray[7];
        return new MatchBuilder(stadium, schedule, localTeam, visitorTeam).build();
    }

    private static Map<String, GroupDTO> getTournamentGroupsMap() {
        final Map<String, GroupDTO> tournamentGroupsMap = new HashMap<>();
        tournamentGroupsMap.put("A", new GroupDTO(asList("Russia", "Saudi_Arabia", "Egypt", "Uruguay"),
                asList("Luzhniki_Stadium 2018 6 14 15 0 Russia Saudi__Arabia",
                        "Ekaterinburg_Arena 2018 6 15 12 0 Egypt Uruguay",
                        "Saint_Petersburg_Stadium 2018 6 19 18 0 Russia Egypt",
                        "Rostov_Arena 2018 6 20 15 0 Uruguay Saudi_Arabia",
                        "Samara_Arena 2018 6 25 14 0 Uruguay Russia",
                        "Volgograd_Arena 2018 6 25 14 0 Saudi_Arabia Egypt")));
        tournamentGroupsMap.put("B", new GroupDTO(asList("Portugal", "Spain", "Morocco", "IR_Iran"),
                asList("Saint_Petersburg_Stadium 2018 6 15 15 0 Morocco IR_Iran",
                        "Fisht_Stadium 2018 6 15 18 0 Portugal Spain",
                        "Luzhniki_Stadium 2018 6 20 12 0 Portugal Morocco",
                        "Kazan_Arena 2018 6 20 18 0 IR_Iran Spain",
                        "Kaliningrad_Stadium 2018 6 20 18 0 Spain Morocco",
                        "Mordovia_Arena 2018 6 20 18 0 IR_Iran Portugal")));
        tournamentGroupsMap.put("C", new GroupDTO(asList("France", "Australia", "Peru", "Denmark"),
                asList("Kazan_Arena 2018 6 16 10 0 France Australia",
                        "Mordovia_Arena 2018 6 16 16 0 Peru Denmark",
                        "Samara_Arena 2018 6 21 12 0 Denmark Australia",
                        "Ekaterinburg_Arena 2018 6 21 15 0 France Peru",
                        "Fisht_Stadium 2018 6 26 14 0 Australia Peru",
                        "Luzhniki_Stadium 2018 6 26 14 0 Denmark France")));
        tournamentGroupsMap.put("D", new GroupDTO(asList("Argentina", "Iceland", "Croatia", "Nigeria"),
                asList("Spartak_Stadium 2018 6 16 13 0 Argentina Iceland",
                        "Kaliningrad_Stadium 2018 6 16 19 0 Croatia Nigeria",
                        "Nizhny_Novgorod_Stadium 2018 6 21 18 0 Argentina Croatia",
                        "Volgograd_Arena 2018 6 22 15 0 Nigeria Iceland",
                        "Saint_Petersburg_Stadium 2018 6 26 18 0 Nigeria Argentina",
                        "Rostov_Arena 2018 6 26 18 0 Iceland Croatia")));
        tournamentGroupsMap.put("E", new GroupDTO(asList("Brazil", "Switzerland", "Costa_Rica", "Serbia"),
                asList("Samara_Arena 2018 6 17 12 0 Costa_Rica Serbia",
                        "Rostov_Arena 2018 6 17 18 0 Brazil Switzerland",
                        "Saint_Petersburg_Stadium 2018 6 22 12 0 Brazil Costa_Rica",
                        "Kaliningrad_Stadium 2018 6 22 18 0 Serbia Switzerland",
                        "Spartak_Stadium 2018 6 27 18 0 Serbia Brazil",
                        "Nizhny_Novgorod 2018 6 27 18 0 Switzerland Costa_Rica")));
        tournamentGroupsMap.put("F", new GroupDTO(asList("Germany", "Mexico", "Sweden", "Korea_Republic"),
                asList("Luzhniki_Stadium 2018 6 17 15 0 Germany Mexico",
                        "Nizhny_Novgorod_Stadium 2018 6 18 12 0 Sweden Korea_Republic",
                        "Rostov_Arena 2018 6 23 15 0 Korea_Republic Mexico",
                        "Fisht_Stadium 2018 6 23 18 0 Germany Sweden",
                        "Kazan_Arena 2018 6 27 14 0 Korea_Republic Germany",
                        "Ekaterinburg_Arena 2018 6 27 14 0 Mexico Sweden")));
        tournamentGroupsMap.put("G", new GroupDTO(asList("Belgium", "Panama", "Tunisia", "England"),
                asList("Fisht_Stadium 2018 6 18 15 0 Belgium Panama",
                        "Volgograd_Arena 2018 6 18 18 0 Tunisia England",
                        "Spartak_Stadium 2018 6 23 12 0 Belgium Tunisia",
                        "Nizhny_Novgorod_Stadium 2018 6 24 12 0 England Panama",
                        "Mordovia_Arena 2018 6 28 18 0 Panama Tunisia",
                        "Kaliningrad_Stadium 2018 6 28 18 0 England Belgium")));
        tournamentGroupsMap.put("H", new GroupDTO(asList("Poland", "Senegal", "Colombia", "Japan"),
                asList("Mordovia_Arena 2018 6 19 12 0 Colombia Japan",
                        "Spartak_Stadium 2018 6 19 15 0 Poland Senegal",
                        "Ekaterinburg_Arena 2018 6 24 15 0 Japan Senegal",
                        "Kazan_Arena 2018 6 24 18 0 Poland Colombia",
                        "Volgograd_Arena 2018 6 28 14 0 Japan Poland",
                        "Samara_Arena 2018 6 28 14 0 Senegal Colombia")));
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
        return actualGroup != null
                && teamSetsAreEquals(actualGroup.getTeamSet(), expectedGroup.getTeamSet())
                && matchSetsAreEquals(actualGroup.getMatchSet(), expectedGroup.getMatchSet());
    }

    private static boolean teamSetsAreEquals(final Set<Team> actualTeamSet, final Set<Team> expectedTeamSet) {
        return teamSetMatchesExpected(actualTeamSet, expectedTeamSet)
                && teamSetsHaveTheSameSize(actualTeamSet, expectedTeamSet);
    }

    private static boolean matchSetsAreEquals(final Set<Match> actualMatchSet, final Set<Match> expectedMatchSet) {
        return matchSetMatchesExpected(actualMatchSet, expectedMatchSet)
                && matchSetsHaveTheSameSize(actualMatchSet, expectedMatchSet);
    }

    private static boolean teamSetMatchesExpected(final Set<Team> actualTeamSet, final Set<Team> expectedTeamSet) {
        return expectedTeamSet.containsAll(actualTeamSet);
    }

    private static boolean teamSetsHaveTheSameSize(final Set<Team> actualTeamSet, final Set<Team> expectedTeamSet) {
        return expectedTeamSet.size() == actualTeamSet.size();
    }

    private static boolean matchSetMatchesExpected(final Set<Match> actualMatchSet, final Set<Match> expectedMatchSet) {
        return expectedMatchSet.containsAll(actualMatchSet);
    }

    private static boolean matchSetsHaveTheSameSize(final Set<Match> actualMatchSet, final Set<Match> expectedMatchSet) {
        return expectedMatchSet.size() == actualMatchSet.size();
    }
}