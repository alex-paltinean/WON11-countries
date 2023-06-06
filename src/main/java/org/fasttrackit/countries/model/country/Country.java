package org.fasttrackit.countries.model.country;

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
    @Column
    private String name;
    @Column
    private String capital;
    @Column
    private long population;
    @Column
    private long area;
    @Column
    private String continent;
    @Transient
    private List<String> neighbours;
}
