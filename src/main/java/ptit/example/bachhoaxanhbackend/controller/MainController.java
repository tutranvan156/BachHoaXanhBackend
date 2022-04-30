package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ptit.example.bachhoaxanhbackend.model.Product;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.mongodb.CollectionControl;
import ptit.example.bachhoaxanhbackend.mongodb.MongoUtils;
import ptit.example.bachhoaxanhbackend.service.ProductService;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 3/2/2022 10:01 PM
 * Desc:
 */
@RestController
public class MainController {

    @Autowired
    private ProductService productServiceImpl;

    CollectionControl<User> collectionControl = new CollectionControl<>();

    @GetMapping("/products")
    List<Product> findProduct() throws IllegalAccessException {
//        MongoUtils.createCollection("order");
//        return this.productServiceImpl.findProduct();
        return null;
    }
}
