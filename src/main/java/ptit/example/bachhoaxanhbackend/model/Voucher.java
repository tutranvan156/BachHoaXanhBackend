package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

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
    @NotEmpty
    private String code;
    @Positive
    private int quantity;
    private String description;
    @Positive
    private Double discountValue;
    @Positive
    private long dateStart;
    @Positive
    private long dateEnd;
    private String maxDiscountValue;
    private String status = VoucherStatus.ENABLE.name();

    public enum VoucherStatus {
        ENABLE,
        DISABLE
    }
}
