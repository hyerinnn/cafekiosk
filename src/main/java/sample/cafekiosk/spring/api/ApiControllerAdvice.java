package sample.cafekiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    //@ExceptionHandler : 예외가 발생하면 캐치를해서 요 메서드를통해 핸들링한다.
    ////@ExceptionHandler(BindException.class)  : @Vaild에서 예외발생하면 BindException이 발생
    @ResponseStatus(HttpStatus.BAD_REQUEST) //이 예외가 발생했을때 어떤 코드로 줄거야.를 명시함
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e) { //BindException이 발생하는 경우!
        return ApiResponse.of(
                HttpStatus.BAD_REQUEST, //400
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), //메세지
                null
        );
    }
}
