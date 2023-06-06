package org.fasttrackit.countries.service.country;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.countries.exception.ResourceNotFoundException;
import org.fasttrackit.countries.model.country.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryReader countryReader;

    private final CountryRepository countryRepository;

    @PostConstruct
    public void init() {
        System.out.println("Post construct in Country Service");
        List<Country> countries = countryReader.readCountries();
        countryRepository.saveAll(countries);
    }

    public List<Country> getAllCountries() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false).toList();
    }

    public List<Country> getByContinent(String continent) {
        return getAllCountries().stream()
                .filter(country -> country.getContinent().equals(continent))
                .toList();
    }

    public Country getById(long id) {
        return getAllCountries().stream()
                .filter(country -> country.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("country not found", id));
    }

    public Country delete(long id) {
        Country country = getById(id);
        countryRepository.deleteById(id);
        return country;
    }

    public Country add(Country country) {
        countryRepository.save(country);
        return country;
    }

    public Country update(Country country, long id) {
        Country existingCountry = delete(id);
        return add(Country.builder()
                .id(id)
                .name(existingCountry.getName())
                .neighbours(existingCountry.getNeighbours())
                .capital(country.getCapital())
                .population(country.getPopulation())
                .area(country.getArea())
                .continent(existingCountry.getContinent())
                .build());
    }
}
