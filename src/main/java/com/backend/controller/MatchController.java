package com.backend.controller;

import com.backend.model.Match;
import com.backend.dto.MatchDTO;
import com.backend.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
@CrossOrigin
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/getAll")
    public List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

    @GetMapping("/getAllByAdmin")
    public List<Match> getAllMatchesById(@RequestParam Integer adminId){
        return matchService.getMatchesById(adminId);
    }

    @PostMapping("/addMatch")
    public Match add(@RequestBody Match match){
        matchService.addMatch(match);
        return match;
    }

    @DeleteMapping("/deleteMatch")
    public String delete(@RequestParam Integer id){
        return matchService.deleteMatch(id);
    }

    @PostMapping("/addUserToMatch")
    public String addPlayerToMatch(@RequestBody MatchDTO requestBody){
        return matchService.addPlayerToMatch(requestBody.getMatchId(), requestBody.getUserId());
    }

    @GetMapping("/getPlayersFromMatch")
    public List<Object> getPlayersFromMatch(@RequestParam Integer matchId){
        return matchService.getAllPlayers(matchId);
    }
}
