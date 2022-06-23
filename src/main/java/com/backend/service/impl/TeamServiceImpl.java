package com.backend.service.impl;

import com.backend.model.Team;
import com.backend.model.User;
import com.backend.repository.TeamRepository;
import com.backend.repository.UserRepository;
import com.backend.service.ChatService;
import com.backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatService chatService;

    @Override
    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team getTeam(Integer id) {
        return teamRepository.findById(id).get();
    }

    @Override
    public void deleteTeam(Integer id) {
        Team team = teamRepository.findByTeamId(id);
        if(team.getId() != null){
            chatService.deleteChat(team.getId());
        }
        if(team.getPlayers().size() > 0){
           team.getPlayers().removeAll(team.getPlayers());
        }
        teamRepository.deleteById(id);
    }

    @Override
    public void deleteTeamByName(String name) {
        Team team = teamRepository.findTeamByName(name);
        if(team.getId() != null){
            chatService.deleteChat(team.getId());
        }
        if (team.getPlayers().size() > 0) {
            team.getPlayers().removeAll(team.getPlayers());
        }
        teamRepository.deleteUserByTeam(name);
    }

    @Override
    public String addPlayerToTeam(Integer teamId, Integer playerId) {
        Team team = teamRepository.findByTeamId(teamId);
        User user = userRepository.findByUserId(playerId);
        if (!team.getPlayers().contains(user)) {
            team.getPlayers().add(user);
            teamRepository.save(team);
            user.getTeams().add(team);
            userRepository.save(user);
            return "User added successfully to the team!";
        }

        return "User is already a member of this team!";
    }

    @Override
    public Set<User> getAllPlayersFromTeam(Integer teamId) {
        Team team = teamRepository.findByTeamId(teamId);
        return team.getPlayers();
    }

    @Override
    public List<Team> getTeamsByAdminId(Integer adminId) {
        List<Team> teamsByAdmin = teamRepository.getTeamsByUserId(adminId);
        List<Team> teamsByUser = teamRepository.findAllByAdmin_Id(adminId);
        teamsByAdmin.addAll(teamsByUser);
        return teamsByAdmin;
    }


    @Override
    public String deletePlayerFromTeam(Integer playerId, Integer teamId) {
        User player = userRepository.findByUserId(playerId);
        Team team = teamRepository.findByTeamId(teamId);
        team.removePlayer(player);
        teamRepository.save(team);
        return "Player deleted successfully from the team!";
    }
}
