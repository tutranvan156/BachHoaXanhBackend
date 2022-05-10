package ptit.example.bachhoaxanhbackend.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ptit.example.bachhoaxanhbackend.model.Order;
import ptit.example.bachhoaxanhbackend.repository.OrderRepository;
import ptit.example.bachhoaxanhbackend.service.OrderService;

import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/9/2022 11:45 PM
 * Desc:
 */
@Configuration
@EnableScheduling
public class OrderSchedule {

    private static final Logger logger = LoggerFactory.getLogger(OrderSchedule.class);
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    /**
     * This method will restore all product in order due to user did not
     * @throws ValidationException
     */
    @Scheduled(cron = "0 0 0 * * ?")
    private void filterOrderOfUser() throws ValidationException {
        long currentTime = System.currentTimeMillis();

        logger.info("Start filter expire order in create stage at : " + currentTime);
        List<Order> orderList = this.orderRepository.findAllByOrderStatus(Order.OrderStatus.CREATE.name());
        final long TIME_ONE_DAY = 86400000;
        for (Order item : orderList) {
            long dateCreateAfterThreeDay = item.getDateCreate() + TIME_ONE_DAY * 3;
            if (dateCreateAfterThreeDay < currentTime) {
                this.orderService.terminateOrder(item.getOrderID());
                logger.info(Order.OrderStatus.TERMINATED.name() + " order: " + item.getOrderID() + " " + item.getDateCreate());
            }
        }
        logger.info("Finish filter expire order in create stage");

    }


}
