package ptit.example.bachhoaxanhbackend.service;

import com.sun.xml.internal.ws.handler.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.example.bachhoaxanhbackend.dto.ProductCart;
import ptit.example.bachhoaxanhbackend.model.Order;
import ptit.example.bachhoaxanhbackend.repository.OrderRepository;
import ptit.example.bachhoaxanhbackend.repository.ProductRepository;

import java.util.List;

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

    private Order addOrder(Order currentOrder) throws HandlerException {

        List<ProductCart> productCartList = currentOrder.getListProductCart();
        double tempSum = 0;
        for (ProductCart item : productCartList) {

            tempSum += item.getPrice();
        }
        currentOrder.setTotalMoney(tempSum);
        return null;
    }
}
