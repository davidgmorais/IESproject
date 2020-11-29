package ies.project.bilheteira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @author Wei
 * @date 2020/11/29 12:53
 */
@Data
@AllArgsConstructor
public class Film implements Serializable {
    private final String title;
    private final String movieId;
    private final Integer year;
    private final Date released;
    private final Integer runtime;
    private final String director;
    private final List<Country> countries;   //? veremos depois
}
