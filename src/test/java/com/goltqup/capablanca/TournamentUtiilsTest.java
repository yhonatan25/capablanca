package com.goltqup.capablanca;

import com.goltqup.capablanca.domain.api.Group;
import com.goltqup.capablanca.domain.api.Match;
import com.goltqup.capablanca.domain.api.Team;
import org.junit.Test;

import java.util.Set;

import static com.goltqup.capablanca.TournamentProvider.getExpectedTournament;

public class TournamentUtiilsTest {

    @Test
    public void printGroupsCode() {
        getExpectedTournament().getGroupSet()
                .forEach(this::printGroupCode);
    }

    private void printGroupCode(final Group group) {
        System.out.print("tournamentGroupsMap.put(\"" + group.getId() + " "
                + group.getName() + "\", new GroupDTO(");
        printTeamSetCode(group.getTeamSet());
        printMatchSetCode(group.getMatchSet());
        System.out.println("));");
    }

    private void printTeamSetCode(final Set<Team> teamSet) {
        System.out.print("asList(");
        teamSet.forEach(team -> System.out.print("\"" + team.getId() + " " + team.getName() + "\", "));
        System.out.println("), ");
    }

    private void printMatchSetCode(final Set<Match> matchSet) {
        System.out.print("asList(");
        matchSet.forEach(match -> System.out.println("\"" + match.getId() + " "
                + match.getStadium() + " "
                + match.getSchedule() + " "
                + match.getLocalTeam() + " "
                + match.getVisitorTeam() + "\", "));
        System.out.print(")");
    }

}
