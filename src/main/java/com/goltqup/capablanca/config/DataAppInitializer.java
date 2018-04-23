package com.goltqup.capablanca.config;

import com.goltqup.capablanca.domain.api.Tournament;
import com.goltqup.capablanca.service.TournamentService;
import com.goltqup.capablanca.service.encoding.TournamentIdEncoder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataAppInitializer {
    private final TournamentService tournamentService;
    private final TournamentIdEncoder tournamentIdEncoder;

    public DataAppInitializer(TournamentService tournamentService, TournamentIdEncoder tournamentIdEncoder) {
        this.tournamentService = tournamentService;
        this.tournamentIdEncoder = tournamentIdEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        final Tournament firstTournament = tournamentService.getTournaments().blockFirst();
        if (firstTournament == null) {
            final String tournamentName = "FIFA";
            final String tournamentPlace = "Russia";
            final int tournamentYear = 2018;
            final String tournamentId = tournamentIdEncoder.encode(tournamentName, tournamentPlace, tournamentYear);
            tournamentService.save(new Tournament(tournamentId, tournamentName, tournamentPlace, tournamentYear))
                    .subscribe(null,
                            null,
                            () -> this.tournamentService.getTournaments()
                                    .subscribe(System.out::println));
        }
    }
}