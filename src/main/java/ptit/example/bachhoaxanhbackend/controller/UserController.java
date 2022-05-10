package ptit.example.bachhoaxanhbackend.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ptit.example.bachhoaxanhbackend.dto.UserPasswordDTO;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.mongodb.MongoUtils;
import ptit.example.bachhoaxanhbackend.repository.UserRepository;
import ptit.example.bachhoaxanhbackend.service.UserService;
import ptit.example.bachhoaxanhbackend.utils.JavaMailSender;
import ptit.example.bachhoaxanhbackend.utils.RespondCode;
import ptit.example.bachhoaxanhbackend.utils.Utils;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 11:43 PM
 * Desc:
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    private ResponseEntity<?> findAll() {
        return new ResponseEntity<>(this.userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/load/{id}")
    private ResponseEntity<?> load(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.userRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        user.setStatus(User.UserStatus.ENABLE.name());
        user.setUserListCart(new ArrayList<>());
        return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") String id) {

        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setStatus(User.UserStatus.DISABLE.name());
            return new ResponseEntity<>(this.userRepository.save(user.get()), HttpStatus.OK);
        } else {
            return RespondCode.NOT_FOUND;
        }
    }

    @PutMapping("update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody User user) {
        try {
            User currentUser = this.userRepository.findById(id).get();
            //which field we allow user to update then change this under here
            currentUser.setFullName(user.getFullName());
            currentUser.setShippingAddress(user.getShippingAddress());
            currentUser.setAddress(user.getAddress());
            currentUser.setPhoneNumber(user.getPhoneNumber());
            currentUser.setStatus(user.getStatus());
            return new ResponseEntity<>(this.userRepository.save(currentUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method will generate otp number, It will update this in database,
     * And then send this otp to userEmail
     *
     * @param
     * @return
     */
    @PostMapping("/forgot-password")
    private ResponseEntity<?> updateOTP(@RequestBody UserPasswordDTO userPasswordDTO) {
        //Get otp code
        String otp = Utils.generateOTP();
        //Send to customer email
        if (userPasswordDTO.getEmailAddress() == null) {
            return new ResponseEntity<>("emailAddress_not_found", HttpStatus.NOT_FOUND);
        } else {
            try {
                JavaMailSender.sendMail(otp, userPasswordDTO.getEmailAddress());
                //update otp code in database
                MongoDatabase database = MongoUtils.getInstance();
                BasicDBObject search = new BasicDBObject();
                search.append("_id", new ObjectId(userPasswordDTO.getId()));
                BasicDBObject update = new BasicDBObject();
                update.append("$set", new BasicDBObject().append("otp", otp));
                database.getCollection("user").updateOne(search, update);

                //return success result
                return new ResponseEntity<>(RespondCode.SUCCESS, HttpStatus.OK);
            } catch (MessagingException | IOException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }

        }
    }

    /**
     * This method need id, otp and new password
     *
     * @param userPasswordDTO
     * @return
     */
    @PostMapping("/update-password")
    private ResponseEntity<?> updatePassword(@RequestBody UserPasswordDTO userPasswordDTO) {
        Optional<User> temp = this.userRepository.findById(userPasswordDTO.getId());
        if (temp.isPresent()) {
            User user = temp.get();
            if (user.getOtp().equals(userPasswordDTO.getOtp())) {
                user.setPassword(userPasswordDTO.getPassword());
                return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(RespondCode.NOT_EQUALS, HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return RespondCode.NOT_FOUND;
        }
    }

//    @PostMapping("/update-shipping-address/{userID}")
//    private ResponseEntity<?> updatePassword(@PathVariable("userID") String userID, List<String> ) {
//        Optional<User> temp = this.userRepository.findById(userID);
//        if (temp.isPresent()) {
//            User user = temp.get();
//            List<String> shippingAddress = user.getShippingAddress();
//
//
//
//            if (user.getOtp().equals(userPasswordDTO.getOtp())) {
//                user.setPassword(userPasswordDTO.getPassword());
//                return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(RespondCode.NOT_EQUALS, HttpStatus.NOT_ACCEPTABLE);
//            }
//        } else {
//            return RespondCode.NOT_FOUND;
//        }
//    }


    /*
    * User register
    * */

    @PostMapping("register")
    public ResponseEntity<?> register() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*
    * Update photo
    * */

    @GetMapping("/load-photo/{id}")
    public ResponseEntity<?> getUserImage(@PathVariable("id") String id){

        return new ResponseEntity<>(userService.getUserImage(id), HttpStatus.OK);
    }

    @PostMapping("/upload-photo/{id}")
    public ResponseEntity<?> uploadUserImage(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {
        userService.updateUserImage(file, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-image/{id}")
    public ResponseEntity<?> deleteUserImage(@PathVariable("id") String id){
        userService.deleteUserImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
