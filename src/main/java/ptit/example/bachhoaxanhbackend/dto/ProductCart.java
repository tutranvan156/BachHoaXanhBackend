package ptit.example.bachhoaxanhbackend.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/28/2022 10:17 AM
 * Desc:
 */
@Data
public class ProductCart {
    @NotEmpty
    private String productID;
    private String picture;
    @NotEmpty
    private String productName;
    @Positive
    private Double price;
    @Positive
    private Double priceDiscount;
    @Positive
    private Double discountPercent;

    private long addDate = System.currentTimeMillis();
    @Positive
    private int quantity = 1;

}
