package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ptit.example.bachhoaxanhbackend.model.Cart;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/30/2022 10:46 PM
 * Desc:
 */
@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
}
