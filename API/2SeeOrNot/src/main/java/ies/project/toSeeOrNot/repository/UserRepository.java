package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Wei
 * @date 02/12/2020 16:49
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User getUserByUserEmail(String email);
    User findUserById(Integer id);
}