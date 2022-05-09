package ptit.example.bachhoaxanhbackend.controller;

import com.google.api.Http;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.example.bachhoaxanhbackend.storage.StorageFileNotFoundException;
import ptit.example.bachhoaxanhbackend.storage.StorageService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images/")
public class ImageController {
    @Autowired
    private StorageService storageService;

    @GetMapping("")
    public ResponseEntity<?> listUploadedFiles() throws IOException {

        return new ResponseEntity<>(
                storageService.loadAll().map(path -> MvcUriComponentsBuilder.fromMethodName(ImageController.class,"serveFile", path.getFileName().toString()).build().toUri().toString()), HttpStatus.OK);
    }

    @GetMapping("files/users")
    public ResponseEntity<?> listUploadedUsersFiles() throws IOException {

        return new ResponseEntity<>(
                storageService.loadAllUsers().map(path -> MvcUriComponentsBuilder.fromMethodName(ImageController.class,"serveUserImageFile", path.getFileName().toString()).build().toUri().toString()), HttpStatus.OK);
    }

    @GetMapping("files/products")
    public ResponseEntity<?> listUploadedProductsFiles() throws IOException {

        return new ResponseEntity<>(
                storageService.loadAllProducts().map(path -> MvcUriComponentsBuilder.fromMethodName(ImageController.class,"serveProductImageFile", path.getFileName().toString()).build().toUri().toString()), HttpStatus.OK);
    }

    @GetMapping("/files/users/{filename:.+}")
    public ResponseEntity<?> serveUserImageFile(@PathVariable String filename) {

        Resource file = storageService.loadUserImage(filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @GetMapping("/files/products/{filename:.+}")
    public ResponseEntity<?> serveProductImageFile(@PathVariable String filename) {

        Resource file = storageService.loadProductImage(filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
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
    @GetMapping("/save-files/users/{filename:.+}")
    public ResponseEntity<?> serveFileProduct(@PathVariable String filename) {

        Resource file = storageService.loadUserImage(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.storeUserImage(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
