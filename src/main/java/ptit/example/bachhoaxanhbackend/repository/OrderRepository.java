package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ptit.example.bachhoaxanhbackend.model.Order;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/8/2022 2:22 PM
 * Desc:
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findOrderByUserID(String userID);
    List<Order> findAllByOrderStatus(String orderStatus);
}
