package ptit.example.bachhoaxanhbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/10/2022 2:42 PM
 * Desc:
 */
@Data
@Document(collection = "alert")
public class Alert {
    @Id
    private String alertID;
    @NotEmpty
    private String userID;
    private String subject;
    private String content;
    private boolean isRead;
    private long date;
}
