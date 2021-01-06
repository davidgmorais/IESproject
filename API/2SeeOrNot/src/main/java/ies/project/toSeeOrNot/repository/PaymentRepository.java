package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Payment;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Wei
 * @date 2021/1/5 18:30
 */
public interface PaymentRepository extends PagingAndSortingRepository<Payment, Integer> {
}
