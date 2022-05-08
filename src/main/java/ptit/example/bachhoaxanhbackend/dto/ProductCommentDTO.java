package ptit.example.bachhoaxanhbackend.dto;

import lombok.Data;

@Data
public class ProductCommentDTO {
    String userId;
    String content;
    long date;
    long starNumber;

    public ProductCommentDTO(String userID, String content, long date, long starNumber) {
        this.userId = userID;
        this.content = content;
        this.date = date;
        this.starNumber = starNumber;
    }
}
