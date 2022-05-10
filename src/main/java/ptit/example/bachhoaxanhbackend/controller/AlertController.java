package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import ptit.example.bachhoaxanhbackend.model.Alert;
import ptit.example.bachhoaxanhbackend.model.Voucher;
import ptit.example.bachhoaxanhbackend.repository.AlertRepository;
import ptit.example.bachhoaxanhbackend.repository.UserRepository;
import ptit.example.bachhoaxanhbackend.repository.VoucherRepository;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/10/2022 2:50 PM
 * Desc:
 */
@RestController
@RequestMapping("/alert/")
public class AlertController {

    @Autowired
    private AlertRepository alertRepository;

    @GetMapping("all/{userID}")
    public ResponseEntity<?> all(@PathVariable("userID") String userID) {
        return new ResponseEntity<>(this.alertRepository.findAllByUserID(userID), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody Alert alert) {
        return new ResponseEntity<>(this.alertRepository.save(alert), HttpStatus.OK);
    }

}
