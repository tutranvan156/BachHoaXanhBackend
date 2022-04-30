package ptit.example.bachhoaxanhbackend.dto;

import lombok.Data;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/28/2022 10:17 AM
 * Desc:
 */
@Data
public class ProductCart {
    private String productID;
    private String quantity;
    private String price;
}
