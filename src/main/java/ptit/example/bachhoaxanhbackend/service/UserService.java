package ptit.example.bachhoaxanhbackend.service;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.mongodb.CollectionControl;
import ptit.example.bachhoaxanhbackend.mongodb.MongoUtils;
import ptit.example.bachhoaxanhbackend.repository.UserRepository;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 11:42 PM
 * Desc:
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User addUser(User user) {
        return this.userRepository.save(user);
    }

    public void removeUser(String id) {
        MongoCollection<Document> userCollection = MongoUtils.getDataBase().getCollection("user");
        Bson filter = eq("_id", new ObjectId(id));
        userCollection.deleteOne(filter);
    }
}
