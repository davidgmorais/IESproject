package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.FilmByCountry;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Wei
 * @date 2021/1/2 17:43
 */
public interface CountryRepository extends PagingAndSortingRepository<FilmByCountry, String> {
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO country VALUES(:country)")
    void saveCountry(String country);

    @Query(nativeQuery = true, value = "SELECT count(1) FROM country WHERE country_name = :country")
    int getCountry(String country);
}
