package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 3/2/2022 9:53 PM
 * Desc:
 */
@Document(collection = "product")
@Data
public class Product {
    @Id
    private String id;
    @Field(value = "productName")
    private String productName;
}
