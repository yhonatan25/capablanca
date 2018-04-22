package com.goltqup.capablanca.config;

import com.goltqup.capablanca.domain.api.Tournament;
import com.goltqup.capablanca.service.TournamentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class CapablancaConfiguration {

    @Bean
    RouterFunction<?> routerFunction(final TournamentService tournamentService) {
        return route(GET("/tournaments").and(accept(APPLICATION_JSON_UTF8)),
                req -> ok().contentType(APPLICATION_JSON_UTF8)
                        .body(tournamentService.getTournaments(), Tournament.class));
    }

}
