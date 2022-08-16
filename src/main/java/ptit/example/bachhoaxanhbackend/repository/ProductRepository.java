package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ptit.example.bachhoaxanhbackend.model.Product;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/28/2022 9:58 AM
 * Desc:
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllByStatus(String status);
    List<Product> findAllByCategoryName(String categoryName);
}
