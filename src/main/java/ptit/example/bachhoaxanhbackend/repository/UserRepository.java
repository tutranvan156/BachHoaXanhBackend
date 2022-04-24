package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ptit.example.bachhoaxanhbackend.model.User;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 11:40 PM
 * Desc:
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
