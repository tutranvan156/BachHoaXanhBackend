package ptit.example.bachhoaxanhbackend.dto;

import lombok.Data;

@Data
public class UserCommentDTO {
    String productId;
    String content;
    long date;
    long starNumber;

    public UserCommentDTO(String productID, String content, long date, long starNumber) {
        this.productId = productID;
        this.content = content;
        this.date = date;
        this.starNumber = starNumber;
    }
}
