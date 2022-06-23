package com.backend.service.impl;

import com.backend.model.Match;
import com.backend.model.User;
import com.backend.repository.MatchRepository;
import com.backend.repository.UserRepository;
import com.backend.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public Match addMatch(Match newMatch) {
        return matchRepository.save(newMatch);
    }

    @Transactional
    @Override
    public String deleteMatch(Integer id) {
        Match match = matchRepository.findById(id).get();
        if (match.getPlayers().size() > 0) {
            for (User player : match.getPlayers()) {
                match.removePlayer(player);
            }
        }
        matchRepository.deleteMatchById(id);
        return "Match deleted successfully!";
    }

    @Override
    public List<Match> getMatchesById(Integer adminId) {
        return matchRepository.getAllMatchesByAdmin(adminId);
    }

    @Override
    public String addPlayerToMatch(Integer matchId, Integer playerId) {
        Match match = matchRepository.findById(matchId).get();
        User user = userRepository.findByUserId(playerId);
        if(!match.getPlayers().contains(user)){
            match.getPlayers().add(user);
            match.setAvailableSpots(match.getAvailableSpots() - 1);
            matchRepository.save(match);
            user.getMatches().add(match);
            userRepository.save(user);
            return "User added successfully to the match!";
        }
        return "User already exists in the current match!";
    }

    @Override
    public List<Object> getAllPlayers(Integer matchId) {
        return matchRepository.getAllPlayersFromMatch(matchId);
    }
}
