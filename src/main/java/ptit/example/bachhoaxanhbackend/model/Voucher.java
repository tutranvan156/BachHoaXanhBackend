package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
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
    @Indexed(unique = true)
    private String code;

    private String description;
    @Positive
    private Double discountPercent;

    @Positive
    private Double maxDiscountValue;

    /**
     * two field below must get in format with millisecond
     */
    @Positive
    private long dateStart;
    @Positive
    private long dateEnd;

    @Min(0)
    private int quantity;

    private String status = VoucherStatus.ENABLE.name();

    public enum VoucherStatus {
        ENABLE,
        DISABLE
    }
}
