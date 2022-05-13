package ptit.example.bachhoaxanhbackend.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 3/2/2022 10:19 PM
 * Desc:
 */
@Service
public interface ProductService {
    void updateProductImage(MultipartFile file, String productId);

    Path getProductImageLocation(String filename);

    Resource getProductImage(String filename);

    void deleteProductImage(String filename);
    void deleteProduct(String productID);
}
