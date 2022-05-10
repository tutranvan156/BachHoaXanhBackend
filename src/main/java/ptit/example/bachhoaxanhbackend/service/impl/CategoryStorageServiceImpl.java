package ptit.example.bachhoaxanhbackend.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import ptit.example.bachhoaxanhbackend.service.CategoryStorageService;
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

@Service
public class CategoryStorageServiceImpl implements CategoryStorageService {
    private final Path categoryLocation;

    public CategoryStorageServiceImpl(StorageProperties properties) {
        this.categoryLocation = Paths.get(properties.getLocation()).resolve("products");
    }

    @Override
    public void updateCategoryImage(MultipartFile file, String categoryId) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.categoryLocation.resolve(Paths.get(categoryId)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.categoryLocation.toAbsolutePath())) {
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
    public Path getCategoryImageLocation(String filename) {
        return this.categoryLocation.resolve(filename);
    }

    @Override
    public Resource getCategoryImage(String filename) {
        try {
            Path file = this.categoryLocation.resolve(filename);
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
    public void deleteCategoryImage(String filename) {
        FileSystemUtils.deleteRecursively(this.categoryLocation.resolve(filename).toFile());
    }
}
