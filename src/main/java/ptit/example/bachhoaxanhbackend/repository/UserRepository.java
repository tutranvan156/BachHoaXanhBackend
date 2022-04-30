package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ptit.example.bachhoaxanhbackend.model.User;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/27/2022 9:58 PM
 * Desc:
 */

public interface UserRepository extends MongoRepository<User, String> {

}
