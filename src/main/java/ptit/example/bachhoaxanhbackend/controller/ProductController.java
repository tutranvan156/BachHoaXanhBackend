package ptit.example.bachhoaxanhbackend.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.example.bachhoaxanhbackend.dto.ProductCart;
import ptit.example.bachhoaxanhbackend.model.Product;
import ptit.example.bachhoaxanhbackend.mongodb.MongoUtils;
import ptit.example.bachhoaxanhbackend.repository.ProductRepository;
import ptit.example.bachhoaxanhbackend.service.ProductService;
import ptit.example.bachhoaxanhbackend.utils.RespondCode;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/28/2022 10:22 AM
 * Desc:
 */
@RestController
@RequestMapping("/products/")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("all")
    private ResponseEntity<?> getAll() {
        return new ResponseEntity<>(this.productRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("add")
    private ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(this.productRepository.save(product), HttpStatus.OK);
    }

    @GetMapping("load")
    private ResponseEntity<?> load(@PathVariable("id") String id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_EXISTS, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody Product product) {
        Product currentProduct = this.productRepository.findById(id).get();
        //which field we allow user to update then change this under here
        currentProduct.setName(product.getName());

        return new ResponseEntity<>(this.productRepository.save(currentProduct), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            this.productRepository.deleteById(id);
            return new ResponseEntity<>(RespondCode.SUCCESS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Each user will have one cart, in this cart we will store
     * @param id is userId, using userId for more easy
     * @// TODO: 5/1/2022 If have time, I will get userId in code, but now just let front provide userId for more easy
     * @param productCart
     * @return
     */
    @PostMapping("add-to-cart/{id}")
    private ResponseEntity<?> addProductToCart(@PathVariable("id") String id, @RequestBody ProductCart productCart) {
        return null;
    }
}
