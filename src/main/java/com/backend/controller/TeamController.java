package com.backend.controller;

import com.backend.model.Team;
import com.backend.model.User;
import com.backend.dto.TeamDTO;
import com.backend.service.TeamService;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/team")
@CrossOrigin
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAllTeams")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping("/addTeam")
    public Team addTeam(@RequestBody Team team) {
        Team teamToAdd = Team.builder()
                .admin(team.getAdmin())
                .name(team.getName())
                .color(team.getColor())
                .shirt("http://127.0.0.1:8887/t-shirts/" + team.getColor() + ".png")
                .build();
        return teamService.addTeam(teamToAdd);
    }

    @DeleteMapping("/deleteTeam")
    public String deleteTeam(@RequestParam Integer id) {
        teamService.deleteTeam(id);
        return "Deleted team with id " + id + ".";
    }

    @DeleteMapping("/deleteTeamByName")
    public String deleteTeamByName(@RequestParam String name) {
        teamService.deleteTeamByName(name);
        return "Deleted team with name " + name + ".";
    }

    @GetMapping("/getTeamById")
    public Team getTeamById(@RequestParam Integer id) {
        return teamService.getTeam(id);
    }


    @PostMapping("/addUserToTeam")
    public String addPlayerToTeam(@RequestBody TeamDTO requestBody){
        return teamService.addPlayerToTeam(requestBody.getTeamId(), requestBody.getUserId());
    }

    @GetMapping("/getPlayersFromTeam")
    public Set<User> getPlayersFromTeam(@RequestParam Integer teamId){
        return teamService.getAllPlayersFromTeam(teamId);
    }

    @GetMapping("/getTeamsByAdmin")
    public List<Team> getTeamsByAdminId(@RequestParam Integer adminId){
        return teamService.getTeamsByAdminId(adminId);
    }

    @DeleteMapping("deletePlayerFromTeam")
    public String deletePlayerFromTeam(@RequestBody TeamDTO requestBody){
        return teamService.deletePlayerFromTeam(requestBody.getUserId(), requestBody.getTeamId());
    }

    @DeleteMapping("deletePlayerFromTeamWithParams")
    public String deletePlayerFromTeamWithParams(@RequestParam Integer userId, @RequestParam Integer teamId){
        return teamService.deletePlayerFromTeam(userId, teamId);
    }

}
