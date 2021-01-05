package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.RegisterRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/5 17:30
 */
public interface RegisterRequestRepository extends PagingAndSortingRepository<RegisterRequest, Integer> {

    @Query(nativeQuery = true, value = "SELECT COUNT(1) FROM registerrequest WHERE processed = 0")
    int getNumberOfRequestsNotProcessed();

    boolean existsByUserEmail(String email);

    RegisterRequest getRegisterRequestById(int id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE registerrequest SET processed = 1 AND accepted = 1 WHERE id = :id")
    boolean accept(int id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE registerrequest SET processed = 1 AND accepted = 0 WHERE id = :id")
    boolean refuse(int id);
}
