package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.example.bachhoaxanhbackend.model.Order;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.repository.OrderRepository;
import ptit.example.bachhoaxanhbackend.repository.UserRepository;
import ptit.example.bachhoaxanhbackend.service.OrderService;
import ptit.example.bachhoaxanhbackend.utils.RespondCode;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;
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

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("load-order-user/{userID}")
    private ResponseEntity<?> loadOrderUser(@PathVariable("userID") String userID) {
        List<Order> orderList = this.orderRepository.findOrderByUserID(userID);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

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
        Optional<User> temp = this.userRepository.findById(order.getUserID());
        if (temp.isPresent()) {
            if (order.getShippingAddress() == null) {
                order.setShippingAddress(temp.get().getShippingAddress());
            }
            //add order
            try {
                return new ResponseEntity<>(this.orderService.addOrder(order), HttpStatus.OK);
            } catch (ValidationException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(RespondCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("purchase/{orderID}")
    private ResponseEntity<?> purchase(@PathVariable("orderID") String orderID) {
        Optional<Order> temp = this.orderRepository.findById(orderID);
        if (temp.isPresent()) {
            Order order = temp.get();
            order.setOrderStatus(Order.OrderStatus.DELIVERING.name());
            return new ResponseEntity<>(this.orderRepository.save(order), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("terminate/{orderID}")
    private ResponseEntity<?> delete(@PathVariable("orderID") String orderID) {
        Optional<Order> temp = this.orderRepository.findById(orderID);
        if (temp.isPresent()) {
            Order order = temp.get();
            try {
                this.orderService.terminateOrder(orderID);
            } catch (ValidationException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(this.orderRepository.save(order), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("approval/{orderID}")
    private ResponseEntity<?> approval(@PathVariable("orderID") String orderID) {
        Optional<Order> temp = this.orderRepository.findById(orderID);
        if (temp.isPresent()) {
            Order order = temp.get();
            order.setOrderStatus(Order.OrderStatus.RECEIVED.name());
            return new ResponseEntity<>(this.orderRepository.save(order), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
