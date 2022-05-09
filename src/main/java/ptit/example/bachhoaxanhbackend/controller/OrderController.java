package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.example.bachhoaxanhbackend.model.Order;
import ptit.example.bachhoaxanhbackend.repository.OrderRepository;
import ptit.example.bachhoaxanhbackend.utils.RespondCode;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 11:25 PM
 * Desc:
 */
@RestController
@RequestMapping("/orders/")
public class OrderController {


    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("load/{orderID}")
    private ResponseEntity<?> load(@PathVariable("orderID") String orderID) {
        Optional<Order> order = this.orderRepository.findById(orderID);
        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("add")
    private ResponseEntity<?> add(@Valid @RequestBody Order order) {
        return new ResponseEntity<>(this.orderRepository.save(order), HttpStatus.OK);
    }


}
