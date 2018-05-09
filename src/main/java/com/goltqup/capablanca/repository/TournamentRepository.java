package com.goltqup.capablanca.repository;

import com.goltqup.capablanca.domain.mongo.TournamentDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TournamentRepository extends ReactiveMongoRepository<TournamentDocument, String> {

    Mono<TournamentDocument> findOneByEncodedId(final String encodedId);

}
