package ies.project.bilheteira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Wei
 * @date 2020/11/29 12:56
 */
@Data
@AllArgsConstructor
public class Country implements Serializable {
    private final String countryName;
}
