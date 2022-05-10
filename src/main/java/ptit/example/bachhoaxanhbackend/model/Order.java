package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ptit.example.bachhoaxanhbackend.dto.ProductCart;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 11:18 PM
 * Desc:
 */
@Data
@Document(collection = "order")
public class Order {
    @Id
    private String orderID;
    @NotEmpty
    private String userID;
    private String orderStatus = OrderStatus.CREATE.name();
    public enum OrderStatus {
        CREATE,
        PROCESS,
        TERMINATED,
        DONE
    }
    private long dateCreate = System.currentTimeMillis();
    private long dateDelivery;
    @NotEmpty
    private String shippingAddress;
    private List<ProductCart> listProductCart;
    private Voucher voucher;
    @Positive
    private Double totalMoney;
}
