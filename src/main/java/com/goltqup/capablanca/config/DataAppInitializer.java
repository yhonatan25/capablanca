package com.goltqup.capablanca.config;

import com.goltqup.capablanca.domain.api.Group;
import com.goltqup.capablanca.domain.api.Tournament;
import com.goltqup.capablanca.service.TournamentService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Arrays.stream;
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
                .subscribe(null, null, this::printTournaments);
    }

    private Tournament getInitialTournament() {
        final String tournamentName = "FIFA";
        final String tournamentPlace = "Russia";
        final int tournamentYear = 2018;
        return new Tournament(tournamentName, tournamentPlace, tournamentYear, getGroupSet());
    }

    private Set<Group> getGroupSet() {
        return stream("A B C D E F G H".split(" "))
                .map(Group::new)
                .collect(toSet());
    }

    private void printTournaments() {
        this.tournamentService.getTournaments()
                .subscribe(System.out::println);
    }
}