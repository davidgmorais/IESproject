package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.FilmByCountry;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Wei
 * @date 2021/1/2 17:43
 */
public interface CountryRepository extends PagingAndSortingRepository<FilmByCountry, String> {
}
