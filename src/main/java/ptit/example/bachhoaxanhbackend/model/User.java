package ptit.example.bachhoaxanhbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 9:54 PM
 * Desc:
 */
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
@Data
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String hashPassword;
    private String address;
    private String shippingAddress;
}
