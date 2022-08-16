package ptit.example.bachhoaxanhbackend.dto;

import lombok.Data;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/1/2022 12:56 PM
 * Desc:
 */
@Data
public class UserPasswordDTO {
    private String id;
    private String otp;
    private String emailAddress;
    private String password;

    @Override
    public String toString() {
        return "UserPasswordDTO{" +
                "id='" + id + '\'' +
                ", otp='" + otp + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
