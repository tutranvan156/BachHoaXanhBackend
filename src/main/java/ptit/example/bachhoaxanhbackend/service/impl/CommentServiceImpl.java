package ptit.example.bachhoaxanhbackend.service.impl;

import com.sun.org.apache.bcel.internal.generic.ATHROW;
import org.graalvm.compiler.hotspot.replacements.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.webjars.NotFoundException;
import ptit.example.bachhoaxanhbackend.dto.ProductCommentDTO;
import ptit.example.bachhoaxanhbackend.dto.UserCommentDTO;
import ptit.example.bachhoaxanhbackend.model.Comment;
import ptit.example.bachhoaxanhbackend.model.Product;
import ptit.example.bachhoaxanhbackend.repository.CommentRepository;
import ptit.example.bachhoaxanhbackend.service.CommentService;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    /*
    * Utils
    * */

    private boolean check(Comment comment) {
        boolean res = true;

        return res;
    }

    private Comment clean(Comment comment) {
        return comment;
    }

    /*
    * Implement Service method
    * */

    @Override
    public List<Comment> findAll() {
        return this.commentRepository.findAll();
    }

    @Override
    public Comment insert(Comment comment) {
        comment = this.clean(comment);
        if(this.check(comment))
            return this.commentRepository.insert(comment);
        else
            throw new InvalidParameterException("Thông tin nhập vào không chính xác");
    }

    @Override
    public void delete(String commentId) {
        if(this.commentRepository.existsById(commentId)) {
            this.commentRepository.deleteById(commentId);
            Log.println("CommentService.delete with commentId=" + commentId + " success");
            return;
        }
        throw new NotFoundException("CommentId not found");
    }

    @Override
    public Comment findById(String id) {
        try {
            return this.commentRepository.findById(id).get();
        } catch (NotFoundException e) {
            Log.println("Database not found comment has id=" + id);
            throw new NotFoundException("Comment has id=" + id + " note found");
        }
    }

    @Override
    public List<UserCommentDTO> findAllByUserId(String id) {
        List<UserCommentDTO> res = new ArrayList<>();
        for(Comment i: this.commentRepository.findCommentsByUserId(id)) {
            res.add(new UserCommentDTO(i.getProductID(), i.getContent(), i.getDate(), i.getStarNumber()));
        }
        return res;
    }

    @Override
    public List<ProductCommentDTO> findAllByProductId(String id) {
        List<ProductCommentDTO> res = new ArrayList<>();
        for(Comment i: this.commentRepository.findCommentsByProductId(id)) {
            res.add(new ProductCommentDTO(i.getUserID(), i.getContent(), i.getDate(), i.getStarNumber()));
        }
        return res;
    }
}
