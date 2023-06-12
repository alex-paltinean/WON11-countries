package org.fasttrackit.countries.model.country;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Country {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "country_name")
    private String name;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private City capital;
    @Column
    private long population;
    @Column
    private long area;
    @Column(name = "country_continent")
    private String continent;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Country> neighbours;

//    @ManyToMany(mappedBy = "neighbours")
//    private List<Country> neighboursOf;

    @OneToMany(mappedBy = "country", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<City> cities;

    public Country(long id) {
        this.id = id;
    }
}
