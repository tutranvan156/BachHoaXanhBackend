package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.example.bachhoaxanhbackend.model.Product;
import ptit.example.bachhoaxanhbackend.service.CategoryStorageService;
import ptit.example.bachhoaxanhbackend.service.ProductService;
import ptit.example.bachhoaxanhbackend.service.UserService;
import ptit.example.bachhoaxanhbackend.storage.StorageFileNotFoundException;
import ptit.example.bachhoaxanhbackend.storage.StorageService;

import java.io.IOException;

@RestController
@RequestMapping("/images/")
public class ImageController {
    /*
    * Gồm có:
    * /all - xem danh sách tất cả các file tại thư mục upload-dir/
    * /files/{filename:.+} - xem file tại thư mục upload-dir/
    * /save-files/{filename:.+} - lưu file tại thư mục upload-dir/
    * /add - (file:...) - thêm file tại thư mục upload-dir/
    *
    * /files/users - xem danh sách tất cả các file tại thư mục upload-dir/users/
    * /files/users/{filename:.+} - xem file tại thư mục upload-dir/users/
    * /save-files/users/{filename:.+} - lưu file tại thư mục upload-dir/users/ về máy
    * /add/users - (file:...) - thêm file vào thư mục upload-dir/users/
    *
    * files/products - xem danh sách tất cả các file tại thư mục upload-dir/products/
    * /files/products/{filename:.+} - xem file tại thư mục upload-dir/products/
    * /save-files/products/{filename:.+} - lưu file tại thư mục upload-dir/products/ về máy
    * /add/products - (file:...) - thêm file vào thư mục upload-dir/products
    * */

    @Autowired
    private StorageService storageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryStorageService categoryStorageService;

    /*
    * control file in upload-dir
    * */

    @GetMapping("/all")
    public ResponseEntity<?> listUploadedFiles() throws IOException {

        return new ResponseEntity<>(
                storageService.loadAll().map(path -> MvcUriComponentsBuilder.fromMethodName(ImageController.class,"serveFile", path.getFileName().toString()).build().toUri().toString()), HttpStatus.OK);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<?> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @GetMapping("/save-files/{filename:.+}")
    public ResponseEntity<?> serveFile2(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/add")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*
    * Control file in upload-dir/users
    * */

    @GetMapping("/files/users")
    public ResponseEntity<?> listUploadedUsersFiles() throws IOException {

        return new ResponseEntity<>(
                storageService.loadAllUsers().map(path -> MvcUriComponentsBuilder.fromMethodName(ImageController.class,"serveUserImageFile", path.getFileName().toString()).build().toUri().toString()), HttpStatus.OK);
    }

    @GetMapping("/files/users/{filename:.+}")
    public ResponseEntity<?> serveUserImageFile(@PathVariable String filename) {

        Resource file = this.userService.getUserImage(filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @GetMapping("/save-files/users/{filename:.+}")
    public ResponseEntity<?> serveFileUser(@PathVariable String filename) {

        Resource file = this.userService.getUserImage(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/add/users/{id}")
    public ResponseEntity<?> handleUserImageFileUpload(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {

        this.userService.updateUserImage(file, id);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*
    * Control file in upload-dir/products
    * */

    @GetMapping("files/products")
    public ResponseEntity<?> listUploadedProductsFiles() throws IOException {

        return new ResponseEntity<>(
                storageService.loadAllProducts().map(path -> MvcUriComponentsBuilder.fromMethodName(ImageController.class,"serveProductImageFile", path.getFileName().toString()).build().toUri().toString()), HttpStatus.OK);
    }

    @GetMapping("/files/products/{filename:.+}")
    public ResponseEntity<?> serveProductImageFile(@PathVariable String filename) {

        Resource file = this.productService.getProductImage(filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @GetMapping("/save-files/products/{filename:.+}")
    public ResponseEntity<?> serveFileProduct(@PathVariable String filename) {

        Resource file = this.productService.getProductImage(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/add/products/{id}")
    public ResponseEntity<?> handleProductImageFileUpload(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {

        this.productService.updateProductImage(file, id);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*
    * Control file in upload-dir/categories
    * */

    @GetMapping("files/categories")
    public ResponseEntity<?> listUploadedCategoryFiles() throws IOException {

        return new ResponseEntity<>(
                this.categoryStorageService.loadAllCategoriesLocation().map(path -> MvcUriComponentsBuilder.fromMethodName(ImageController.class,"serveCategoryImageFile", path.getFileName().toString()).build().toUri().toString()), HttpStatus.OK);
    }

    @GetMapping("/files/categories/{filename:.+}")
    public ResponseEntity<?> serveCategoryImageFile(@PathVariable String filename) {

        Resource file = this.categoryStorageService.getCategoryImage(filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @GetMapping("/save-files/categories/{filename:.+}")
    public ResponseEntity<?> serveFileCategory(@PathVariable String filename) {

        Resource file = this.categoryStorageService.getCategoryImage(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/add/categories/{id}")
    public ResponseEntity<?> handleCategoryImageFileUpload(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {

        this.categoryStorageService.updateCategoryImage(file, id);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*
    * Exception handler
    * */

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
