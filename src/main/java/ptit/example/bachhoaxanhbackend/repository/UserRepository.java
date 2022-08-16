package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ptit.example.bachhoaxanhbackend.model.User;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/27/2022 9:58 PM
 * Desc:
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllByStatus(String status);
}
