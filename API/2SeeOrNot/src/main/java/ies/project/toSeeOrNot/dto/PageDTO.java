package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/6 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<V> implements Serializable {
    private Set<V> data;

    private int totalPages;

    private long totalElements;

}
