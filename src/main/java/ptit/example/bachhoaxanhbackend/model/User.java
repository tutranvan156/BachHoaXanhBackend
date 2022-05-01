package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ptit.example.bachhoaxanhbackend.dto.ProductCart;

import javax.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String emailAddress;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String address;
    private String phoneNumber;
    private String status;
    @NotEmpty
    private String userType;
    private String otp;
    private enum userType {
        USER,
        EMP
    }
    @NotEmpty
    private String shippingAddress;
    public enum UserStatus {
        DISABLE,
        ENABLE
    }
    public enum UserLoginStatus {
        LOGIN,
        LOGOUT
    }
    private List<ProductCart> userListCart;
    private List<Voucher> userListVoucher;
}
