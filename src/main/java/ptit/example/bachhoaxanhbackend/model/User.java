package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 9:54 PM
 * Desc:
 */
@Data
@Document(collection = "user")
public class User {
    @Id
    private String userID;
    private String fullName;
    private String username;
    private String password;
    private String address;
    private String phoneNumber;
    private enum userType {
        USER,
        EMP
    }
    private String shippingAddress;
    private enum userStatus {
        LOGIN,
        LOGOUT
    }
    private List<Cart> userListCart;
    private List<Voucher> userListVoucher;
}
