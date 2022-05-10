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
@Document(collection = "type")
public class ProductType {

    @Id
    private String typeID;
    @NotEmpty
    private String typeName;
    private String description;
    private String typeStatus = TypeStatus.ENABLE.name();
    public enum TypeStatus {
        ENABLE,
        DISABLE
    }
}
