package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ptit.example.bachhoaxanhbackend.dto.ProductCart;
import ptit.example.bachhoaxanhbackend.model.Product;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.repository.ProductRepository;
import ptit.example.bachhoaxanhbackend.repository.UserRepository;
import ptit.example.bachhoaxanhbackend.service.ProductService;
import ptit.example.bachhoaxanhbackend.service.impl.ProductServiceImpl;
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
    ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("all")
    private ResponseEntity<?> getAll() {
        return new ResponseEntity<>(this.productRepository.findAllByStatus(Product.ProductStatus.ENABLE.name()), HttpStatus.OK);
    }

    @PostMapping("add")
    private ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        this.productRepository.save(product);
        return new ResponseEntity<>(this.productRepository.findAllByStatus(Product.ProductStatus.ENABLE.name()), HttpStatus.OK);
    }

    @GetMapping("load/{id}")
    private ResponseEntity<?> load(@PathVariable("id") String id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_EXISTS, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody Product product) {
//        Product currentProduct = this.productRepository.findById(id).get();
        //which field we allow user to update then change this under here
//        currentProduct.setName(product.getName());
//        currentProduct.setPrice(product.getPrice());
//        currentProduct.setQuantity(product.getQuantity());
//        currentProduct.setDescription(product.getDescription());
//        currentProduct.setBranch(product.getBranch());
//        currentProduct.setIngredient(product.getIngredient());
        this.productRepository.save(product);
        return new ResponseEntity<>(this.productRepository.findAllByStatus(Product.ProductStatus.ENABLE.name()), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") String id) {
        Optional<Product> tempProduct = this.productRepository.findById(id);
        if (tempProduct.isPresent()) {
            Product product = tempProduct.get();
            product.setStatus(Product.ProductStatus.DISABLE.name());
            this.productRepository.save(product);
            this.productService.deleteProduct(id);
            return new ResponseEntity<>(this.productRepository.findAllByStatus(Product.ProductStatus.ENABLE.name()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_EXISTS, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Each user will have one cart, in this cart we will store
     *
     * @param userID      is userId, using userId for more easy
     * @param productCart
     * @return
     * @// TODO: 5/1/2022 If have time, I will get userId in code, but now just let front provide userId for more easy
     */
    @PutMapping("add-to-cart/{userID}")
    private ResponseEntity<?> addProductToCart(@PathVariable("userID") String userID, @Valid @RequestBody ProductCart productCart) {
        Optional<User> tempUser = this.userRepository.findById(userID);
        Optional<Product> tempProduct = this.productRepository.findById(productCart.getProductID());
        if (tempUser.isPresent() && tempProduct.isPresent()) {
            Product product = tempProduct.get();
            if (product.getStatus().equals(Product.ProductStatus.DISABLE.name())) {
                return new ResponseEntity<>(RespondCode.INVALID, HttpStatus.BAD_REQUEST);
            }
            User user = tempUser.get();
            List<ProductCart> tempList = user.getUserListCart();
            tempList.add(productCart);
            return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_EXISTS, HttpStatus.NOT_FOUND);
        }
    }
    /*
     * Update photo
     * */

    @GetMapping("/load-photo/{id}")
    public ResponseEntity<?> getUserImage(@PathVariable("id") String id) {

        return new ResponseEntity<>(this.productService.getProductImage(id), HttpStatus.OK);
    }

    @PostMapping("/upload-photo/{id}")
    public ResponseEntity<?> uploadUserImage(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {
        this.productService.updateProductImage(file, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-image/{id}")
    public ResponseEntity<?> deleteUserImage(@PathVariable("id") String id) {
        this.productService.deleteProductImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get all product base on productTypeName
     *
     * @param categoryName
     * @return
     */
    @GetMapping("get-by-category/{productTypeName}")
    private ResponseEntity<?> getAllByTypeName(@PathVariable("productTypeName") String categoryName) {
        return new ResponseEntity<>(this.productRepository.findAllByCategoryName(categoryName), HttpStatus.OK);
    }
}

