package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/28/2022 10:04 AM
 * Desc:
 */
@Data
@Document(collection = "voucher")
public class Voucher {
    @Id
    private String voucherID;
    private String code;
    private int quantity;
    private String description;
    private Double discountValue;
    private Date dateStart;
    private Date dateEnd;
    private String productID;
    private String maxDiscountValue;
    private enum voucherStatus {
        ENABLE,
        DISABLE
    }
}
