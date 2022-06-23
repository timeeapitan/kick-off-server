package com.backend.service;

import com.backend.model.Team;
import com.backend.model.User;

import java.util.List;
import java.util.Set;

public interface TeamService {
    Team addTeam(Team team);

    List<Team> getAllTeams();

    Team getTeam(Integer id);

    void deleteTeam(Integer id);

    void deleteTeamByName(String team);

    String addPlayerToTeam(Integer teamId, Integer playerId);

    Set<User> getAllPlayersFromTeam(Integer teamId);

    List<Team> getTeamsByAdminId(Integer adminId);

    String deletePlayerFromTeam(Integer playerId, Integer teamId);
}
