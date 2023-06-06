package org.fasttrackit.countries.controller.country;

import lombok.RequiredArgsConstructor;
import org.fasttrackit.countries.model.country.Country;
import org.fasttrackit.countries.service.country.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("countries") // http://host:port/countries
public class CountryController {

    private final CountryService countryService;

    @GetMapping // GET http://host:port/countries
    public List<Country> getAll() {
        return countryService.getAllCountries();
    }

    @GetMapping("/first") // GET http://host:port/countries/first
    public Country getFirst() {
        return countryService.getAllCountries().get(0);
    }

    @GetMapping("/{id}") // GET http://host:port/countries/46
    public Country getById(@PathVariable long id) {
        return countryService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Country deleteById(@PathVariable long id) {
        return countryService.delete(id);
    }

    @PostMapping
    public Country addNewCountry(@RequestBody Country country) {
        return countryService.add(country);
    }

    @PutMapping("/{id}")
    public Country updateCountry(@RequestBody Country country, @PathVariable long id) {
        return countryService.update(country, id);
    }

}
