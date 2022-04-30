package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.service.UserService;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 11:43 PM
 * Desc:
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/find-all")
    private ResponseEntity<User> findAll() {
        return null;
    }



//    @PostMapping("/users/add")
//    private ResponseEntity<?> addUser(@RequestBody User user) {
//        return new ResponseEntity<>(this.userService.addUser(user), HttpStatus.OK);
//    }

//    @GetMapping("/users/remove/{id}")
//    public ResponseEntity<?> removeUser(@PathVariable("id") String id) {
////        if (this.userRepository.findById(id) != null) {
//////            this.userService.removeUser(id);
////            return new ResponseEntity<>(id, HttpStatus.OK);
////        } else {
////        }
//            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
//    }
}
