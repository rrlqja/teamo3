package song.teamo3.domain.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import song.teamo3.domain.common.dto.ExceptionResponse;
import song.teamo3.domain.common.exception.TeamoException;
import song.teamo3.domain.common.exception.user.UserException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(exception = {UserException.class})
    public ExceptionResponse<Void> userException(UserException e) {

        return ExceptionResponse.<Void>builder()
                .success(false)
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ExceptionResponse<Object> argumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("type", exception.getClass().getSimpleName());
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            exceptionMap.put(fieldName, errorMessage);
        });

        return ExceptionResponse.builder()
                .success(false)
                .data(exceptionMap)
                .build();
    }

    @ExceptionHandler({TeamoException.class})
    public String teamoException(TeamoException e) {
        log.error(e.getMessage());
        return "redirect:/";
    }
}
