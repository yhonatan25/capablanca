package com.goltqup.capablanca.service;

import com.goltqup.capablanca.domain.api.Group;
import com.goltqup.capablanca.domain.api.Match;
import com.goltqup.capablanca.domain.api.Team;
import com.goltqup.capablanca.domain.api.Tournament;
import com.goltqup.capablanca.domain.mongo.GroupDocument;
import com.goltqup.capablanca.domain.mongo.MatchDocument;
import com.goltqup.capablanca.domain.mongo.TeamDocument;
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

    TournamentService(final TournamentRepository tournamentRepository) {
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

    public Mono<Tournament> getTournament(final String tournamentId) {
        return tournamentRepository.findOneByEncodedId(tournamentId)
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
        return new Group(groupDocument.getName(), getTeamSet(groupDocument.getTeamDocumentSet()), getMatchSet(groupDocument.getMatchDocumentSet()));
    }

    private Set<Team> getTeamSet(final Set<TeamDocument> teamDocumentSet) {
        return teamDocumentSet.stream()
                .map(this::getTeam)
                .collect(toSet());
    }

    private Team getTeam(final TeamDocument teamDocument) {
        return new Team.TeamBuilder(teamDocument.getName()).build();
    }

    private Set<Match> getMatchSet(final Set<MatchDocument> matchDocumentSet) {
        return matchDocumentSet.stream()
                .map(this::getMatch)
                .collect(toSet());
    }

    private Match getMatch(final MatchDocument matchDocument) {
        return new Match.MatchBuilder(matchDocument.getStadium(),
                matchDocument.getSchedule(),
                matchDocument.getLocalTeam(),
                matchDocument.getVisitorTeam())
                .localGoals(matchDocument.getLocalGoals())
                .visitorGoals(matchDocument.getVisitorGoals())
                .build();
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
        return new GroupDocument(group.getName(),
                group.getId(),
                getTeamDocumentSet(group.getTeamSet()),
                getMatchDocumentSet(group.getMatchSet()));
    }

    private Set<TeamDocument> getTeamDocumentSet(final Set<Team> teamSet) {
        return teamSet.stream()
                .map(team -> new TeamDocument(team.getName(), team.getId()))
                .collect(toSet());
    }

    private Set<MatchDocument> getMatchDocumentSet(final Set<Match> matchSet) {
        return matchSet.stream().map(this::getMatchDocument).collect(toSet());
    }

    private MatchDocument getMatchDocument(final Match match) {
        return new MatchDocument(match.getId(),
                match.getStadium(),
                match.getSchedule(),
                match.getLocalTeam(),
                match.getVisitorTeam());
    }
}
