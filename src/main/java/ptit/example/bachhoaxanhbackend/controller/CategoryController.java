package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.example.bachhoaxanhbackend.model.Category;
import ptit.example.bachhoaxanhbackend.repository.CategoryRepository;
import ptit.example.bachhoaxanhbackend.utils.RespondCode;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/10/2022 8:35 AM
 * Desc:
 */
@RestController
@RequestMapping("/categories/")
public class CategoryController {

    @Autowired
    private CategoryRepository productTypeRepository;
    @GetMapping("all")
    private ResponseEntity<?> getAll() {
        return new ResponseEntity<>(this.productTypeRepository.findAllByTypeStatus(Category.CategoryStatus.ENABLE.name()), HttpStatus.OK);
    }

    @PostMapping("add")
    private ResponseEntity<?> addProduct(@Valid @RequestBody Category productType) {
        return new ResponseEntity<>(this.productTypeRepository.save(productType), HttpStatus.OK);
    }

    @GetMapping("load/{id}")
    private ResponseEntity<?> load(@PathVariable("id") String id) {
        Optional<Category> tempProductType = this.productTypeRepository.findById(id);
        if (tempProductType.isPresent()) {
            return new ResponseEntity<>(tempProductType.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_EXISTS, HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("update/{id}")
//    private ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody ProductType productType) {
//        Optional<ProductType> tempProductType = this.productTypeRepository.findById(id);
//        if (tempProductType.isPresent()) {
//            ProductType currentProductType = tempProductType.get();
//            currentProductType.setTypeName(productType.getTypeName());
//            return new ResponseEntity<>(this.productTypeRepository.save(currentProductType), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(RespondCode.NOT_EXISTS, HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") String id) {
        Optional<Category> tempProductType = this.productTypeRepository.findById(id);
        if (tempProductType.isPresent()) {
            Category currentProductType = tempProductType.get();
            currentProductType.setTypeStatus(Category.CategoryStatus.DISABLE.name());
            return new ResponseEntity<>(this.productTypeRepository.save(currentProductType), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_EXISTS, HttpStatus.NOT_FOUND);
        }
    }
}
