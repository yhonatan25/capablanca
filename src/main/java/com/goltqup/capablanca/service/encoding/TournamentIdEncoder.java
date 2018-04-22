package com.goltqup.capablanca.service.encoding;

import org.springframework.stereotype.Component;

import static java.util.Base64.getEncoder;

@Component
public class TournamentIdEncoder {

    public String encode(final String name, final String place, final int year) {
        final String idToEncode = new StringBuilder(name).append(place).append(year).toString();
        return getEncoder().encodeToString(idToEncode.getBytes());
    }

}
