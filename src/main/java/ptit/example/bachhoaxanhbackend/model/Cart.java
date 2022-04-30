package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.GetMapping;
import ptit.example.bachhoaxanhbackend.dto.ProductCart;

import java.util.Date;
import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 11:17 PM
 * Desc:
 */
@Getter
@Setter
@Document(collection = "cart")
public class Cart {
    @Id
    private String cartID;
    private String userID;
    private enum orderStatus {
        PROCESS,
        DELIVERY,
        DONE
    }
    private Date dateCreate;
    private Date dateDelivery;
    private String currentPosition;
    private String shippingAddress;
    private List<ProductCart> listProductCart;
    private Double totalMoney;
}
