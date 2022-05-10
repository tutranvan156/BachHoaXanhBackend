package ptit.example.bachhoaxanhbackend.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
public interface CategoryStorageService {

    void updateCategoryImage(MultipartFile file, String categoryId);

    Path getCategoryImageLocation(String filename);

    Resource getCategoryImage(String filename);

    void deleteCategoryImage(String filename);
}
