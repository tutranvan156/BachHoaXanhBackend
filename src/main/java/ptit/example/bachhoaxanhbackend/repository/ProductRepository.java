package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ptit.example.bachhoaxanhbackend.model.Product;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 3/2/2022 9:56 PM
 * Desc:
 */
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAll();
}
