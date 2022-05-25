package ee.mihkel.webshop.controller.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ExceptionResponse {
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private Date timestamp;
    private String message;
}
