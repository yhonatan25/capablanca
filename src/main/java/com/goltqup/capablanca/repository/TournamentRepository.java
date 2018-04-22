package com.goltqup.capablanca.repository;

import com.goltqup.capablanca.domain.mongo.Tournament;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TournamentRepository extends ReactiveMongoRepository<Tournament, String> {
}
