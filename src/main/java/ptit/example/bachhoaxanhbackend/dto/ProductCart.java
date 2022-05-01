package ptit.example.bachhoaxanhbackend.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

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
    @NotEmpty
    private Double price;
    private Double priceDiscount;
    private long addDate;
    private int quantity;
    private String status = ProductCartStatus.SELECTED.name();

    public enum ProductCartStatus {
        SELECTED,
        NOT_SELECTED
    }
}
