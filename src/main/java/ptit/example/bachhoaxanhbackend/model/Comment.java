package ptit.example.bachhoaxanhbackend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;

@Getter
@Setter
@Document(collection = "comment")
public class Comment {
    @Id
    private String commentID;
    @NotBlank
    private String userID;
    @NotBlank
    private String productID;
    private String content;
//    @PastOrPresent
    private long date;
    @Positive
    @Max(value = 5,message = "must be less than or equal to 5")
    private long starNumber;
}
