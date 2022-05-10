package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ptit.example.bachhoaxanhbackend.model.ProductType;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/10/2022 8:32 AM
 * Desc:
 */
@Repository
public interface ProductTypeRepository extends MongoRepository<ProductType, String> {
    List<ProductType> findAllByTypeStatus(String status);
}
