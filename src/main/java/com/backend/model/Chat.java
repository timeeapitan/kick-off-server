package com.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private LocalTime time;
    private String message;

    @OneToOne
    @JoinColumn(name = "sender", referencedColumnName = "user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team teamId;
}
