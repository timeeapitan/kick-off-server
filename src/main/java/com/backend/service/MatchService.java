package com.backend.service;

import com.backend.model.Match;
import com.backend.model.User;

import java.util.List;

public interface MatchService {
    List<Match> getAllMatches();

    Match addMatch(Match newMatch);

    String deleteMatch(Integer id);

    List<Match> getMatchesById(Integer id);

    String addPlayerToMatch(Integer matchId, Integer playerId);

    List<Object> getAllPlayers(Integer matchId);
}
