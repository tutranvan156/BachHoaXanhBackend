package ptit.example.bachhoaxanhbackend.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import ptit.example.bachhoaxanhbackend.service.UserService;
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
public class UserServiceImpl implements UserService {

    private final Path userLocation;

    public UserServiceImpl(StorageProperties properties) {
        this.userLocation = Paths.get(properties.getLocation()).resolve("users");
    }

    @Override
    public void updateUserImage(MultipartFile file, String userId) {
//        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.userLocation.resolve(Paths.get(userId)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.userLocation.toAbsolutePath())) {
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
//        return userId + fileExtension;
    }

    @Override
    public Path getUserImageLocation(String filename) {
        return this.userLocation.resolve(filename);
    }

    @Override
    public Resource getUserImage(String filename) {
        try {
            Path file = this.userLocation.resolve(filename);
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
    public void deleteUserImage(String filename) {
        FileSystemUtils.deleteRecursively(this.userLocation.resolve(filename).toFile());
    }
}
