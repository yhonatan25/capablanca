package com.goltqup.capablanca.service;

import com.goltqup.capablanca.domain.api.Tournament;
import com.goltqup.capablanca.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(final TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Flux<Tournament> getTournaments() {
        return tournamentRepository.findAll()
                .flatMap(tournament -> Mono.just(new Tournament(tournament.getEncodedId(), tournament.getName(), tournament.getPlace(), tournament.getYear())));
    }

    public Mono<Tournament> save(final Tournament tournament) {
        return tournamentRepository.save(new com.goltqup.capablanca.domain.mongo.Tournament(tournament.getName(), tournament.getPlace(), tournament.getYear(), tournament.getId()))
                .map(tournamentMongo -> new Tournament(tournamentMongo.getEncodedId(), tournamentMongo.getName(), tournamentMongo.getPlace(), tournamentMongo.getYear()));
    }
}
