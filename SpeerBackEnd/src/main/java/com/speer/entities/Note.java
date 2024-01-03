package com.speer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "note")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer note_id;

    @Column(name = "note_body")
    private String note_body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;
}
