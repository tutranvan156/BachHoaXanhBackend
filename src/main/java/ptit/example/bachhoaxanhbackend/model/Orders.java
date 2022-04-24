package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 11:18 PM
 * Desc:
 */
@Data
@Document(collection = "orders")
public class Orders {
    private String id;
    private String customerName;
    private String paymentStatus;
    private String status;
    private List<Product> items;
    private String totalMoney;
    private String shippingAddress;
}
