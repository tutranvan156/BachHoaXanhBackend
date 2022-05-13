package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ptit.example.bachhoaxanhbackend.model.Alert;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/10/2022 2:46 PM
 * Desc:
 */
@Repository
public interface AlertRepository extends MongoRepository<Alert, String> {
    List<Alert> findAllByUserID(String userID);
}
