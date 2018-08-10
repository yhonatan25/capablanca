package com.goltqup.capablanca;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.goltqup.capablanca.domain.api.Tournament;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class TournamentProvider {

    public static <T> T getObjectFromJson(final String filePath, final Class<T> resourceClass) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final ClassPathResource classPathResource = new ClassPathResource(filePath);
        return objectMapper.readValue(classPathResource.getInputStream(), resourceClass);
    }

    public static Tournament getExpectedTournamentFromJson() throws IOException {
        return getObjectFromJson("/expected/tournament.json", Tournament.class);
    }
}
