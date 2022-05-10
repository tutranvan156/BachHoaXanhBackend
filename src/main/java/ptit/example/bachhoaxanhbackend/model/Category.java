package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/10/2022 8:32 AM
 * Desc:
 */
@Data
@Document(collection = "category")
public class Category {

    @Id
    private String categoryID;
    @NotEmpty
    private String name;
    private String typeStatus = CategoryStatus.ENABLE.name();
    public enum CategoryStatus {
        ENABLE,
        DISABLE
    }
}
