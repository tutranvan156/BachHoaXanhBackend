package ptit.example.bachhoaxanhbackend.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(MultipartFile file);

	void storeUserImage(MultipartFile file);

	void storeProductImage(MultipartFile file);

	Stream<Path> loadAll();

	Stream<Path> loadAllUsers();

	Stream<Path> loadAllProducts();

	Path load(String filename);

	Resource loadAsResource(String filename);

	Resource loadUserImage(String filename);

	Resource loadProductImage(String filename);

	void deleteAll();

}
