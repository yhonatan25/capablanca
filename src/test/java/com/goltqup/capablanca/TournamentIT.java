package com.goltqup.capablanca;

import com.goltqup.capablanca.domain.api.Tournament;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static com.goltqup.capablanca.TournamentAssert.getExpectedTournament;
import static com.goltqup.capablanca.TournamentAssert.tournamentMatchesExpected;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TournamentIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetTournamentsContainsOneTournament() {
        final FluxExchangeResult<Tournament> tournamentFluxExchangeResult = webTestClient.get()
                .uri("/tournaments").accept(APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_UTF8)
                .returnResult(Tournament.class);

        StepVerifier.create(tournamentFluxExchangeResult.getResponseBody())
                .expectNextMatches(tournament -> tournamentMatchesExpected(tournament, getExpectedTournament()))
                .thenCancel().verify();
    }

}
