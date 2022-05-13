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

        User tempUser = this.userRepository.findById(currentOrder.getUserID()).get();
        List<ProductCart> productOrderList = currentOrder.getListProductCart();
        List<ProductCart> productCartList = tempUser.getUserListCart();
        if (productOrderList == null) {
            throw new ValidationException("listProductCart:  must_not_empty");
        }

        /**
         * This block of code is using to check if product is out of stock
         */
        for (ProductCart item : productOrderList) {
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
        /**
         * This block of code is using to update voucher status when user using it in order
         */
        if (currentOrder.getVoucherID() != null) {
            List<Voucher> voucherList = tempUser.getUserListVoucher();
            for (Voucher item : voucherList) {
                if (item.getVoucherID().equals(currentOrder.getVoucherID())) {
                    item.setIsUse(Voucher.IsUse.TRUE.name());
                    tempUser.setUserListVoucher(voucherList);
                    break;
                }
            }
        }
        /**
         * End of update userVoucherList
         */

        /**
         * after check if we can go through all without exception,
         * then we will decrease quantity value of each product
         * after check then I will decrease value of each product
         */

        for (ProductCart item : productOrderList) {
            Optional<Product> temp = this.productRepository.findById(item.getProductID());
            if (temp.isPresent()) {
                Product product = temp.get();
                int remainProduct = product.getQuantity();
                product.setQuantity(remainProduct - item.getQuantity());
                this.productRepository.save(product);
            } else {
                throw new ValidationException(RespondCode.NOT_EXISTS);
            }
            /**
             * This block of code using to desc value of product in cartList of user when user create order
             */
            for (int i = 0; i < productCartList.size(); i++) {
                ProductCart itemCartList = productCartList.get(i);
                if (item.getProductID().equals(itemCartList.getProductID())) {
                    productCartList.remove(i);
                    /**
                     * Then break
                     */
                    break;
                }
            }
        }
        /**
         * After adjust productCartList then save it again into database
         */
        tempUser.setUserListCart(productCartList);
        this.userRepository.save(tempUser);
        //update order in database
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
                    throw new ValidationException(item.getProductID() + " " + RespondCode.NOT_EXISTS);
                }
            }
            /**
             * This block of code using to roll back voucher when user terminate order process
             */
//            String userID = order.getUserID();
//            Optional<User> tempUser = this.userRepository.findById(userID);
//
//            User user = tempUser.get();
//            List<Voucher> voucherList = user.getUserListVoucher();
            //todo: check this one again because current user in database didn't have listVoucher
//            if (voucherList != null) {
//                for (Voucher item : voucherList) {
//                    if (item.getVoucherID().equals(order.getVoucher().getVoucherID())) {
//                        item.setStatus(Voucher.VoucherStatus.ENABLE.name());
//                        break;
//                    }
//                }
//                //update voucher status in voucher list of user
//                this.userRepository.save(user);
//            }
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

