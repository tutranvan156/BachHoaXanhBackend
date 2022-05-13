package ptit.example.bachhoaxanhbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import ptit.example.bachhoaxanhbackend.dto.ProductCart;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.repository.UserRepository;
import ptit.example.bachhoaxanhbackend.service.ProductService;
import ptit.example.bachhoaxanhbackend.storage.StorageException;
import ptit.example.bachhoaxanhbackend.storage.StorageFileNotFoundException;
import ptit.example.bachhoaxanhbackend.storage.StorageProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private UserRepository userRepository;
    private final Path productLocation;

    public ProductServiceImpl(StorageProperties properties) {
        this.productLocation = Paths.get(properties.getLocation()).resolve("products");
    }

    @Override
    public void updateProductImage(MultipartFile file, String productId) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.productLocation.resolve(Paths.get(productId)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.productLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Path getProductImageLocation(String filename) {
        return this.productLocation.resolve(filename);
    }

    @Override
    public Resource getProductImage(String filename) {
        try {
            Path file = this.productLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteProductImage(String filename) {
        FileSystemUtils.deleteRecursively(this.productLocation.resolve(filename).toFile());
    }

    /**
     * Delete all product in userCartList when delete product
     * @param productID
     */
    public void  deleteProduct(String productID) {
        List<User> userList = this.userRepository.findAllByStatus(User.UserStatus.ENABLE.name());
        for (User itemUser : userList) {
            List<ProductCart> productCartList = itemUser.getUserListCart();
            for (int i = 0; i < productCartList.size(); i++) {
                if (productCartList.get(i).getProductID().equals(productID)) {
                    productCartList.remove(i);
                }
            }
            itemUser.setUserListCart(productCartList);
            this.userRepository.save(itemUser);
        }
    }
}
