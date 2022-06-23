package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String phone;
    private String country;
    private Date dateOfBirth;

    @Column(columnDefinition = "varchar(255) default 'Not provided yet'")
    private String tablePictureSrc;

    @Column(columnDefinition = "varchar(255) default 'Not provided yet'")
    private String tablePosition;

    @JsonIgnore
    @ManyToMany(mappedBy = "players", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Team> teams = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;

    @OneToOne
    @JoinColumn(name = "body_structure_id", referencedColumnName = "id")
    private BodyStructure bodyStructure;

    @OneToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private PictureSrc pictureSrc;

    @JsonIgnore
    @ManyToMany(mappedBy = "players", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Match> matches = new HashSet<>();
}