package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ptit.example.bachhoaxanhbackend.model.Comment;
import ptit.example.bachhoaxanhbackend.model.Voucher;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query("{'productID': ?0}")
    List<Comment> findCommentsByProductId(String id);

    @Query("{'userID':  ?0}")
    List<Comment> findCommentsByUserId(String id);
}
