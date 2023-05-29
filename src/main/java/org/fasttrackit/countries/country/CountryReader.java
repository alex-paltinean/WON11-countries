package org.fasttrackit.countries.country;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class CountryReader {

    public List<Country> readCountries() {
        List<Country> countries = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/countries.txt"));
            long id = 1;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                countries.add(fromLine(line, id++));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return countries;
    }

    private Country fromLine(String line, long id) {
        System.out.println(line);
        String[] tokens = line.split("\\|");
        Country.CountryBuilder countryBuilder = Country.builder()
                .id(id)
                .name(tokens[0])
                .capital(tokens[1])
                .population(Long.parseLong(tokens[2]))
                .area(Long.parseLong(tokens[3]))
                .continent(tokens[4]);
        if (tokens.length == 6) {
            countryBuilder.neighbours(Arrays.asList(tokens[5].split("~")));
        }
        return countryBuilder.build();
    }
}
