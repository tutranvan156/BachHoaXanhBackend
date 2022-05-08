package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ptit.example.bachhoaxanhbackend.model.Comment;
import ptit.example.bachhoaxanhbackend.model.Product;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.repository.CommentRepository;
import ptit.example.bachhoaxanhbackend.service.CommentService;
import ptit.example.bachhoaxanhbackend.utils.RespondCode;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/comments/")
public class CommentController {

    @Autowired
    CommentService commentService;
    /*
    * Gồm có
    * - GetMethod - xem tất cả
    * - /commentID - xem 1 bình luận
    * - PostMethod - thêm một bình luận (userID, productID, content, date, startNumber)
    * - không có chỉnh sửa bình luận
    * - DeleteMethod/commentID - xóa 1 bình luận
    * *****
    * - load-by-product/productID - xem tất cả bình luận của một sản phẩm
    * - load-by-user/userID - xem tất cả bình luận của một người dùng
    * */

    @GetMapping
    private ResponseEntity<?> getAll() {
        return new ResponseEntity<>(this.commentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    private ResponseEntity<?> getProductById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(this.commentService.findById(id), HttpStatus.OK);
        } catch (NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    private ResponseEntity<?> addProduct(@Valid @RequestBody Comment comment) {
        return new ResponseEntity<>(this.commentService.insert(comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            this.commentService.delete(id);
            System.out.println("success");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/load-by-product/{productId}")
    private ResponseEntity<?> getCommentsByProduct(@PathVariable("productId") String id) {
        return new ResponseEntity<>(this.commentService.findAllByProductId(id), HttpStatus.OK);
    }

    @GetMapping("/load-by-user/{userId}")
    private ResponseEntity<?> getCommentsByUser(@PathVariable("userId") String id) {
        return new ResponseEntity<>(this.commentService.findAllByUserId(id), HttpStatus.OK);
    }
}
