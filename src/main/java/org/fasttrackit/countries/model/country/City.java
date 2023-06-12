package org.fasttrackit.countries.model.country;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class City {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @JsonIgnore
    @OneToOne(mappedBy = "capital")
    private Country capitalOf;

    @JsonIgnore
    @ManyToOne //COUNTRY_ID -> FK -> COUNTRY(ID)
    private Country country;

    public City(String name) {
        this.name = name;
    }
}
