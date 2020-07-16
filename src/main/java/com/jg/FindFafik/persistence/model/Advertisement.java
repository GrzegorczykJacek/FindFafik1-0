package com.jg.FindFafik.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column (nullable = false)
    private String city;

    @Column (nullable = false)
    private String contents;

    @Column (nullable = false)
    private Category category;

    @Column
    private Species species;

    @Column
    private String icon;

    @Column ()
    private LocalDateTime dateOfPublication;

    @Column ()
    private LocalDateTime EndDate;

    @Column ()
    private double latitude;

    @Column ()
    private double longitude;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Advertisement(String title, String city, String contents,double latitude, double longitude) {
        this.title=title;
        this.city=city;
        this.contents=contents;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public void setIconBySpecies(Species species){
        this.setIcon(
                "assets/img/marker-icons/"
                        + species.toString().toLowerCase()
                + ".png"
        );
    }

}
