package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.example.bachhoaxanhbackend.model.Alert;
import ptit.example.bachhoaxanhbackend.repository.AlertRepository;
import ptit.example.bachhoaxanhbackend.utils.RespondCode;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/10/2022 2:50 PM
 * Desc:
 */
@RestController
@RequestMapping("/alerts/")
public class AlertController {

    @Autowired
    private AlertRepository alertRepository;

    @GetMapping("all/{userID}")
    public ResponseEntity<?> all(@PathVariable("userID") String userID) {
        if (Objects.equals(userID, "0")) {
            return new ResponseEntity<>(this.alertRepository.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.alertRepository.findAllByUserID(userID), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody Alert alert) {
        return new ResponseEntity<>(this.alertRepository.save(alert), HttpStatus.OK);
    }

    @GetMapping("update/{alertID}")
    public ResponseEntity<?> update(@PathVariable("alertID") String alertID) {
        Optional<Alert> tempAlert = this.alertRepository.findById(alertID);
        if (tempAlert.isPresent()) {
            Alert alert = tempAlert.get();
            alert.setIsRead(Alert.AlertStatus.TRUE.name());
            return new ResponseEntity<>(this.alertRepository.save(alert), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_EXISTS, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("clear-alert/{userID}")
    public ResponseEntity<?> clearAlert(@PathVariable("userID") String userID) {
        List<Alert> alertList = this.alertRepository.findAllByUserID(userID);
        for (Alert item : alertList) {
            if (item.getIsRead().equals(Alert.AlertStatus.FALSE.name())) {
                item.setIsRead(Alert.AlertStatus.TRUE.name());
                this.alertRepository.save(item);
            }
        }
        return new ResponseEntity<>(RespondCode.SUCCESS, HttpStatus.OK);
    }
}
