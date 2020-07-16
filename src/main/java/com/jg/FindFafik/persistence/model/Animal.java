package com.jg.FindFafik.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column()
    private String animalName;

    @Column()
    private Species species;

    @Column()
    private Long chip;

    @Column()
    private String race;

    @Column()
    private int weight;

    @Column()
    private String specialCharacters;

}
