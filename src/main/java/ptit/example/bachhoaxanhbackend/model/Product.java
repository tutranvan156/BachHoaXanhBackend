package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 3/2/2022 9:53 PM
 * Desc:
 */
@Data
@Document(collection = "product")
public class Product {
    @Id
    private String productID;
    @NotEmpty
    private String name;
    @Positive
    private int price;
    @Positive
    private int quantity;
    private String typeID;
    private double discountPercent;
    private long dateMFG;
    private long dateEXP;
    private String description;
    private String branch;
    private String origin;
    private String ingredient;
    private long dateDiscountStart;
    private long dateDiscountEnd;
    @NotNull
    private enum productStatus {
        ENABLE,
        DISABLE
    }
}
