package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ptit.example.bachhoaxanhbackend.model.Product;
import ptit.example.bachhoaxanhbackend.service.impl.ProductServiceImpl;

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
    private ProductServiceImpl productServiceImpl;

    @GetMapping("/products")
    List<Product> findProduct() {
        return this.productServiceImpl.findProduct();
    }
}
