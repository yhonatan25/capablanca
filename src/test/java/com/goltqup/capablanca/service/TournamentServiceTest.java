package com.goltqup.capablanca.service;

import com.goltqup.capablanca.domain.api.Tournament;
import com.goltqup.capablanca.domain.mongo.TournamentDocument;
import com.goltqup.capablanca.repository.TournamentRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.goltqup.capablanca.TournamentAssert.getExpectedTournament;
import static com.goltqup.capablanca.TournamentAssert.tournamentMatchesExpected;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static reactor.test.StepVerifier.create;


@RunWith(SpringRunner.class)
@DataMongoTest
public class TournamentServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TournamentRepository tournamentRepository;

    private TournamentService tournamentService;

    @Before
    public void setup() {
        tournamentService = new TournamentService(tournamentRepository);
        mongoTemplate.dropCollection(Tournament.class);
    }

    @Test
    public void testSave() {
        final Tournament expectedTournament = getExpectedTournament();

        final Mono<Tournament> tournamentMono = tournamentService.save(expectedTournament);

        create(tournamentMono)
                .expectNext(expectedTournament)
                .expectComplete()
                .verify();

        final List<TournamentDocument> tournamentDocumentList = mongoTemplate.findAll(TournamentDocument.class);
        assertThat(tournamentDocumentList.size(), is(1));
    }

    @Test
    public void testSaveTwiceTheSameTournamentOnlySavesOneDocument() {
        final Tournament expectedTournament = getExpectedTournament();

        final Mono<Tournament> tournamentMono = tournamentService.save(expectedTournament);
        final Mono<Tournament> tournamentMonoTwice = tournamentService.save(expectedTournament);

        create(tournamentMono)
                .expectNext(expectedTournament)
                .expectComplete()
                .verify();

        create(tournamentMonoTwice)
                .expectNext(expectedTournament)
                .expectComplete()
                .verify();

        final List<TournamentDocument> tournamentDocumentList = mongoTemplate.findAll(TournamentDocument.class);
        assertThat(tournamentDocumentList.size(), is(1));
    }

    @Test
    public void testGetTournamentsWhenCollectionIsEmpty() {
        final Flux<Tournament> tournamentFlux = tournamentService.getTournaments();

        create(tournamentFlux).expectComplete().verify();
    }

    @Test
    public void testGetTournamentsAfterSavingOne() {
        final Tournament expectedTournament = tournamentService.save(getExpectedTournament()).block();

        final Flux<Tournament> tournamentFlux = tournamentService.getTournaments();

        create(tournamentFlux)
                .expectNextMatches(tournament -> tournamentMatchesExpected(tournament, expectedTournament))
                .expectComplete()
                .verify();
    }
}