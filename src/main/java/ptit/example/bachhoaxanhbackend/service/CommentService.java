package ptit.example.bachhoaxanhbackend.service;

import org.springframework.stereotype.Service;
import ptit.example.bachhoaxanhbackend.dto.ProductCommentDTO;
import ptit.example.bachhoaxanhbackend.dto.UserCommentDTO;
import ptit.example.bachhoaxanhbackend.model.Comment;

import java.util.List;

@Service
public interface CommentService {

    List<Comment> findAll();

    Comment insert(Comment comment);

//    String update(Comment comment);

    void delete(String commentId);

    Comment findById(String id);

    List<UserCommentDTO> findAllByUserId(String id);

    List<ProductCommentDTO> findAllByProductId(String id);
}
