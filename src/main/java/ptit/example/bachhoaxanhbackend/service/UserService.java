package ptit.example.bachhoaxanhbackend.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 11:42 PM
 * Desc:
 */
@Service
public interface UserService {

    void updateUserImage(MultipartFile file, String userId);

    Path getUserImageLocation(String filename);

    Resource getUserImage(String filename);

    void deleteUserImage(String filename);
}
