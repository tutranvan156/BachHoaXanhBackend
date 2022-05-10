package ptit.example.bachhoaxanhbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.example.bachhoaxanhbackend.dto.ProductCart;
import ptit.example.bachhoaxanhbackend.model.Order;
import ptit.example.bachhoaxanhbackend.model.Product;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.model.Voucher;
import ptit.example.bachhoaxanhbackend.repository.OrderRepository;
import ptit.example.bachhoaxanhbackend.repository.ProductRepository;
import ptit.example.bachhoaxanhbackend.repository.UserRepository;
import ptit.example.bachhoaxanhbackend.utils.RespondCode;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/8/2022 2:32 PM
 * Desc:
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Order addOrder(Order currentOrder) throws ValidationException {

        List<ProductCart> productCartList = currentOrder.getListProductCart();
        if (productCartList == null) {
            throw new ValidationException("listProductCart:  must_not_empty");
        }

        for (ProductCart item : productCartList) {
            Optional<Product> temp = this.productRepository.findById(item.getProductID());
            if (temp.isPresent()) {
                Product product = temp.get();
                int remainProduct = product.getQuantity();
                //check if product is out of stock
                if (item.getQuantity() > remainProduct) {
                    throw new ValidationException(product.getProductID() + " " + RespondCode.OUT_OF_STOCK);
                }
            } else {
                throw new ValidationException(RespondCode.NOT_EXISTS);
            }
        }

        //after check if we can go through all without exception, then we will decrease quantity value of each product
        //after check then I will decrease value of each product
        double tempSum = 0;
        for (ProductCart item : productCartList) {
            Optional<Product> temp = this.productRepository.findById(item.getProductID());
            if (temp.isPresent()) {
                Product product = temp.get();
                int remainProduct = product.getQuantity();
                tempSum += item.getPriceDiscount() * item.getQuantity();
                //check if product is out of stock
                product.setQuantity(remainProduct - item.getQuantity());
                this.productRepository.save(product);
            } else {
                throw new ValidationException(RespondCode.NOT_EXISTS);
            }
        }


        //calculate discount using voucher
        Voucher voucher = currentOrder.getVoucher();
        double discountVoucher = tempSum * voucher.getDiscountPercent() <= voucher.getMaxDiscountValue() ? tempSum * voucher.getDiscountPercent() : voucher.getMaxDiscountValue();

        Optional<User> tempUser = this.userRepository.findById(currentOrder.getUserID());
        User user = tempUser.get();
        List<Voucher> voucherList = user.getUserListVoucher();
        for (Voucher item : voucherList) {
            if (item.getVoucherID().equals(voucher.getVoucherID())) {
                item.setStatus(Voucher.VoucherStatus.DISABLE.name());
                break;
            }
        }
        //update voucher status in voucher list of user
        this.userRepository.save(user);
        tempSum -= discountVoucher;
        //check if user is voucher slayer =)))))
        if (tempSum <= 0) tempSum = 0;
        currentOrder.setTotalMoney(tempSum);
        return this.orderRepository.save(currentOrder);
    }

    public void terminateOrder(String orderID) throws ValidationException {
        Optional<Order> tempOrder = this.orderRepository.findById(orderID);
        if (tempOrder.isPresent()) {
            Order order = tempOrder.get();
            List<ProductCart> productCartList = order.getListProductCart();
            if (productCartList == null) {
                throw new ValidationException("listProductCart:  must_not_empty");
            }

            for (ProductCart item : productCartList) {
                Optional<Product> temp = this.productRepository.findById(item.getProductID());
                if (temp.isPresent()) {
                    Product product = temp.get();
                    int remainProduct = product.getQuantity();
                    //check if product is out of stock
                    product.setQuantity(remainProduct + item.getQuantity());
                    this.productRepository.save(product);
                } else {
                    throw new ValidationException(temp.get().getProductID() + " " + RespondCode.NOT_EXISTS);
                }
            }
            /**
             * This block of code using to roll back voucher when user terminate order process
             */
            String userID = order.getUserID();
            Optional<User> tempUser = this.userRepository.findById(userID);

            User user = tempUser.get();
            List<Voucher> voucherList = user.getUserListVoucher();
            //todo: check this one again because current user in database didn't have listVoucher
            if (voucherList != null) {
                for (Voucher item : voucherList) {
                    if (item.getVoucherID().equals(order.getVoucher().getVoucherID())) {
                        item.setStatus(Voucher.VoucherStatus.ENABLE.name());
                        break;
                    }
                }
                //update voucher status in voucher list of user
                this.userRepository.save(user);
            }
            /**
             * End of roll back voucher
             */
            order.setOrderStatus(Order.OrderStatus.TERMINATED.name());
            this.orderRepository.save(order);
        } else {
            throw new ValidationException("order" + " " + RespondCode.NOT_EXISTS);
        }
    }
}

