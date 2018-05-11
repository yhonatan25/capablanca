package com.goltqup.capablanca.config;

import com.goltqup.capablanca.domain.api.Group;
import com.goltqup.capablanca.domain.api.Team.TeamBuilder;
import com.goltqup.capablanca.domain.api.Tournament;
import com.goltqup.capablanca.service.TournamentService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                .map(stringListEntry -> new Group(stringListEntry.getKey(),
                        stringListEntry.getValue().stream()
                                .map(s -> new TeamBuilder(s).build()).collect(toSet())))
                .collect(toSet());
    }

    private void printTournaments() {
        this.tournamentService.getTournaments()
                .subscribe(System.out::println);
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
}