package ptit.example.bachhoaxanhbackend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/28/2022 11:27 AM
 * Desc:
 */
public interface RespondCode {
    String NOT_EXISTS = "not_exists";
    String INVALID = "invalid";
    String EXISTED = "existed";
    String SUCCESS = "success";
    String NOT_EQUALS = "not_equals";
    String OUT_OF_STOCK = "out_of_stock";
    public static final ResponseEntity<?> NOT_FOUND = new ResponseEntity<>(RespondCode.NOT_EXISTS, HttpStatus.OK);
}
