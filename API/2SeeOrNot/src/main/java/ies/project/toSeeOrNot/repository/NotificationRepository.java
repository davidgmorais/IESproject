package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/10 10:38
 */
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Integer> {
    Page<Notification> findAllByReceiver(Integer id, Pageable page);

    @Query(nativeQuery = true, value = "SELECT COUNT(1) FROM notification WHERE receiver = :user and read = 0")
    int getNumberOfUnreadNotificationsByUser(int user);

}
