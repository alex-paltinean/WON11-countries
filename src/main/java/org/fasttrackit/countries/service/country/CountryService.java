package org.fasttrackit.countries.service.country;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.countries.exception.ResourceNotFoundException;
import org.fasttrackit.countries.model.country.City;
import org.fasttrackit.countries.model.country.Country;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryReader countryReader;

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    @PostConstruct
    public void init() {
        System.out.println("Post construct in Country Service");
        List<Country> countries = countryReader.readCountries();
        countryRepository.saveAll(countries);
        List<Country> countryList = countryRepository.findAll().stream().toList();
        for (Country country : countryList) {
            country.getCapital().setCountry(country);
        }
        cityRepository.saveAll(countryList.stream().map(Country::getCapital).toList());
    }

    public List<Country> getAllCountries(String continent, Long minPopulation, Long maxPopulation, String name) {
        return countryRepository.filterCountries(continent, minPopulation, maxPopulation, name);
    }

    public List<Country> getByContinent(String continent) {
        return countryRepository.findByContinentJPQL(continent);
    }

    public Country getById(long id) {
        return countryRepository.findById(id)
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

    public Country addCityToCountry(Long countryId, City city) {
        Country country = getById(countryId);
        city.setCountry(country);
        country.getCities().add(city);
        return countryRepository.save(country);
    }

    public Country addNeighbourToCountry(Long id, Long neighbourId) {
        Country country = getById(id);
        Country neighbour = getById(neighbourId);
        country.getNeighbours().add(neighbour);
        return countryRepository.save(country);
    }
}
