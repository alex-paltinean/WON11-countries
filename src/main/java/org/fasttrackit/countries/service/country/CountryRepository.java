package org.fasttrackit.countries.service.country;

import org.fasttrackit.countries.model.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    // select * from country where continent=?1
    List<Country> findByContinent(String continent);

    @Query("select c from Country c where c.continent=:continent")
    List<Country> findByContinentJPQL(@Param("continent") String continent);

    @Query(value = "select c from country c where c.country_continent=:continent", nativeQuery = true)
    List<Country> findByContinentNativeQuery(@Param("continent") String continent);

    @Query("select c from Country c where (:continent = null or c.continent=:continent) " +
            "and (:minPopulation = null or c.population>=:minPopulation) " +
            "and (:maxPopulation = null or c.population<=:maxPopulation) " +
            "and (:name = null or lower(c.name) like lower(concat('%',:name,'%')))")
    List<Country> filterCountries(@Param("continent") String continent,
                                  @Param("minPopulation") Long minPopulation,
                                  @Param("maxPopulation") Long maxPopulation,
                                  @Param("name") String name);
}
