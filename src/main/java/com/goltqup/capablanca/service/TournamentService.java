package com.goltqup.capablanca.service;

import com.goltqup.capablanca.domain.api.Group;
import com.goltqup.capablanca.domain.api.Tournament;
import com.goltqup.capablanca.domain.mongo.GroupDocument;
import com.goltqup.capablanca.domain.mongo.TournamentDocument;
import com.goltqup.capablanca.repository.TournamentRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static reactor.core.publisher.Mono.just;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(final TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Flux<Tournament> getTournaments() {
        return tournamentRepository.findAll()
                .flatMap(this::getTournamentPublisher);
    }

    public Mono<Tournament> save(final Tournament tournament) {
        return tournamentRepository.findOneByEncodedId(tournament.getId())
                .switchIfEmpty(tournamentRepository.save(getTournamentDocument(tournament)))
                .map(this::getTournament);
    }

    private Publisher<? extends Tournament> getTournamentPublisher(final TournamentDocument tournamentDocument) {
        return just(getTournament(tournamentDocument));
    }

    private Tournament getTournament(final TournamentDocument tournamentDocument) {
        return new Tournament(tournamentDocument.getName(), tournamentDocument.getPlace(),
                tournamentDocument.getYear(), getGroupSet(tournamentDocument.getGroupDocumentSet()));
    }

    private Set<Group> getGroupSet(final Set<GroupDocument> groupDocumentSet) {
        return groupDocumentSet.stream()
                .map(this::getGroup)
                .collect(toSet());
    }

    private Group getGroup(final GroupDocument groupDocument) {
        return new Group(groupDocument.getName());
    }

    private TournamentDocument getTournamentDocument(final Tournament tournament) {
        return new TournamentDocument(tournament.getName(), tournament.getPlace(),
                tournament.getYear(), tournament.getId(),
                getGroupDocumentSet(tournament.getGroupSet()));
    }

    private Set<GroupDocument> getGroupDocumentSet(final Set<Group> groupSet) {
        return groupSet.stream()
                .map(this::getGroupDocument)
                .collect(toSet());
    }

    private GroupDocument getGroupDocument(final Group group) {
        return new GroupDocument(group.getName(), group.getId());
    }
}
