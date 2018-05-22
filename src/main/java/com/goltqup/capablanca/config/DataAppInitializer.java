package com.goltqup.capablanca.config;

import com.goltqup.capablanca.domain.api.Group;
import com.goltqup.capablanca.domain.api.Match;
import com.goltqup.capablanca.domain.api.Team;
import com.goltqup.capablanca.domain.api.Tournament;
import com.goltqup.capablanca.service.TournamentService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

@Component
public class DataAppInitializer {
    private final TournamentService tournamentService;

    public DataAppInitializer(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        tournamentService.getTournaments()
                .switchIfEmpty(tournamentService.save(getInitialTournament()))
                .subscribe(tournament -> {
                        },
                        throwable -> {
                        },
                        this::printTournaments);
    }

    private Tournament getInitialTournament() {
        final String tournamentName = "FIFA";
        final String tournamentPlace = "Russia";
        final int tournamentYear = 2018;
        return new Tournament(tournamentName, tournamentPlace, tournamentYear, getGroupSet());
    }

    private Set<Group> getGroupSet() {
        return getTournamentGroupsMap().entrySet().stream()
                .map(stringGroupDTOEntry -> new Group(stringGroupDTOEntry.getKey(),
                        stringGroupDTOEntry.getValue().getTeamList().stream()
                                .map(s -> new Team.TeamBuilder(s).build()).collect(toSet()),
                        stringGroupDTOEntry.getValue().getMatchList().stream()
                                .map(this::getMatch).collect(toSet())))
                .collect(toSet());
    }

    private Match getMatch(final String matchString) {
        final String[] groupArray = matchString.split(" ");
        final String stadium = groupArray[0];
        final LocalDateTime schedule = LocalDateTime.of(parseInt(groupArray[1]),
                parseInt(groupArray[2]),
                parseInt(groupArray[3]),
                parseInt(groupArray[4]),
                parseInt(groupArray[5]));
        final String localTeam = groupArray[6];
        final String visitorTeam = groupArray[7];
        return new Match.MatchBuilder(stadium, schedule, localTeam, visitorTeam).build();
    }

    private Map<String, GroupDTO> getTournamentGroupsMap() {
        final Map<String, GroupDTO> tournamentGroupsMap = new HashMap<>();
        tournamentGroupsMap.put("A", new GroupDTO(asList("Russia", "Saudi_Arabia", "Egypt", "Uruguay"),
                asList("Luzhniki_Stadium 2018 6 14 9 0 Russia Saudi_Arabia",
                        "Ekaterinburg_Arena 2018 6 15 6 0 Egypt Uruguay",
                        "Saint_Petersburg_Stadium 2018 6 19 12 0 Russia Egypt",
                        "Rostov_Arena 2018 6 20 9 0 Uruguay Saudi_Arabia",
                        "Samara_Arena 2018 6 25 8 0 Uruguay Russia",
                        "Volgograd_Arena 2018 6 25 8 0 Saudi_Arabia Egypt")));
        tournamentGroupsMap.put("B", new GroupDTO(asList("Portugal", "Spain", "Morocco", "IR_Iran"),
                asList("Saint_Petersburg_Stadium 2018 6 15 9 0 Morocco IR_Iran",
                        "Fisht_Stadium 2018 6 15 12 0 Portugal Spain",
                        "Luzhniki_Stadium 2018 6 20 6 0 Portugal Morocco",
                        "Kazan_Arena 2018 6 20 12 0 IR_Iran Spain",
                        "Kaliningrad_Stadium 2018 6 20 12 0 Spain Morocco",
                        "Mordovia_Arena 2018 6 20 12 0 IR_Iran Portugal")));
        tournamentGroupsMap.put("C", new GroupDTO(asList("France", "Australia", "Peru", "Denmark"),
                asList("Kazan_Arena 2018 6 16 4 0 France Australia",
                        "Mordovia_Arena 2018 6 16 10 0 Peru Denmark",
                        "Samara_Arena 2018 6 21 6 0 Denmark Australia",
                        "Ekaterinburg_Arena 2018 6 21 9 0 France Peru",
                        "Fisht_Stadium 2018 6 26 8 0 Australia Peru",
                        "Luzhniki_Stadium 2018 6 26 8 0 Denmark France")));
        tournamentGroupsMap.put("D", new GroupDTO(asList("Argentina", "Iceland", "Croatia", "Nigeria"),
                asList("Spartak_Stadium 2018 6 16 7 0 Argentina Iceland",
                        "Kaliningrad_Stadium 2018 6 16 13 0 Croatia Nigeria",
                        "Nizhny_Novgorod_Stadium 2018 6 21 12 0 Argentina Croatia",
                        "Volgograd_Arena 2018 6 22 9 0 Nigeria Iceland",
                        "Saint_Petersburg_Stadium 2018 6 26 12 0 Nigeria Argentina",
                        "Rostov_Arena 2018 6 26 12 0 Iceland Croatia")));
        tournamentGroupsMap.put("E", new GroupDTO(asList("Brazil", "Switzerland", "Costa_Rica", "Serbia"),
                asList("Samara_Arena 2018 6 17 6 0 Costa_Rica Serbia",
                        "Rostov_Arena 2018 6 17 12 0 Brazil Switzerland",
                        "Saint_Petersburg_Stadium 2018 6 22 6 0 Brazil Costa_Rica",
                        "Kaliningrad_Stadium 2018 6 22 12 0 Serbia Switzerland",
                        "Spartak_Stadium 2018 6 27 12 0 Serbia Brazil",
                        "Nizhny_Novgorod 2018 6 27 12 0 Switzerland Costa_Rica")));
        tournamentGroupsMap.put("F", new GroupDTO(asList("Germany", "Mexico", "Sweden", "Korea_Republic"),
                asList("Luzhniki_Stadium 2018 6 17 9 0 Germany Mexico",
                        "Nizhny_Novgorod_Stadium 2018 6 18 6 0 Sweden Korea_Republic",
                        "Rostov_Arena 2018 6 23 9 0 Korea_Republic Mexico",
                        "Fisht_Stadium 2018 6 23 12 0 Germany Sweden",
                        "Kazan_Arena 2018 6 27 8 0 Korea_Republic Germany",
                        "Ekaterinburg_Arena 2018 6 27 8 0 Mexico Sweden")));
        tournamentGroupsMap.put("G", new GroupDTO(asList("Belgium", "Panama", "Tunisia", "England"),
                asList("Fisht_Stadium 2018 6 18 9 0 Belgium Panama",
                        "Volgograd_Arena 2018 6 18 9 0 Tunisia England",
                        "Spartak_Stadium 2018 6 23 6 0 Belgium Tunisia",
                        "Nizhny_Novgorod_Stadium 2018 6 24 6 0 England Panama",
                        "Mordovia_Arena 2018 6 28 12 0 Panama Tunisia",
                        "Kaliningrad_Stadium 2018 6 28 12 0 England Belgium")));
        tournamentGroupsMap.put("H", new GroupDTO(asList("Poland", "Senegal", "Colombia", "Japan"),
                asList("Mordovia_Arena 2018 6 19 6 0 Colombia Japan",
                        "Spartak_Stadium 2018 6 19 9 0 Poland Senegal",
                        "Ekaterinburg_Arena 2018 6 24 9 0 Japan Senegal",
                        "Kazan_Arena 2018 6 24 12 0 Poland Colombia",
                        "Volgograd_Arena 2018 6 28 8 0 Japan Poland",
                        "Samara_Arena 2018 6 28 8 0 Senegal Colombia")));
        return tournamentGroupsMap;
    }

    private void printTournaments() {
        this.tournamentService.getTournaments()
                .subscribe(System.out::println);
    }
}