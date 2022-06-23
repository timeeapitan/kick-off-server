package com.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String matchTitle;
    private LocalDate date;
    private Time startTime;
    private int duration;
    private double locationLat;
    private double locationLng;
    private String locationNotes;
    private int noOfTeams;
    private int noOfPlayersPerTeam;
    private String cost;
    private Boolean soloPlayersMode;
    private Integer availableSpots;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id")
    private User admin;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_match",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> players = new HashSet<>();

    public void addPlayer(User player) {
        this.players.add(player);
        player.getMatches().add(this);
    }

    public void removePlayer(User player) {
        this.players.remove(player);
        player.getMatches().remove(this);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", matchTitle='" + matchTitle + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", locationLat=" + locationLat +
                ", locationLng=" + locationLng +
                ", noOfTeams=" + noOfTeams +
                ", noOfPlayersPerTeam=" + noOfPlayersPerTeam +
                ", cost=" + cost +
                ", soloPlayersMode=" + soloPlayersMode +
                '}';
    }
}
