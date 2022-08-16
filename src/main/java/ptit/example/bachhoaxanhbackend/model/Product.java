package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;

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
    private String productImage;
    @NotEmpty
    private String name;
    @Positive
    private double price;
    @Min(0)
    @Max(100)
    private double discountPercent;
    @Min(0)
    private int quantity;
    private String categoryID;
    private String categoryName;
    private long dateMFG;
    private long dateEXP;
    private String description;
    private String branch;
    private String origin;
    private String ingredient;
    private long dateDiscountStart;
    private long dateDiscountEnd;
    private String status = ProductStatus.ENABLE.name();
    public enum ProductStatus {
        ENABLE,
        DISABLE
    }
}
