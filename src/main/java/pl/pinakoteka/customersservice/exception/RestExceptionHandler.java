package pl.pinakoteka.customersservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler({EntityNotFoundException.class})
//    public ErrorDto handleNotFoundException(EntityNotFoundException e){
//        log.error("Exception handled", e);
//        ErrorDto error=new ErrorDto();
//        error.setExceptionClass(e.getClass().getCanonicalName());
//        error.setMessage(e.getMessage());
//        return error;
//    }
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler({BindException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
//    public ErrorDto handleValidationException(Exception e){
//        log.error("Exception handled", e);
//        ErrorDto error=new ErrorDto();
//        error.setExceptionClass(e.getClass().getCanonicalName());
//        error.setMessage(e.getMessage());
//        return error;
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({Exception.class})
//    public ErrorDto handleGeneralException(Exception e){
//        log.error("Exception handled", e);
//        ErrorDto error=new ErrorDto();
//        error.setExceptionClass(e.getClass().getCanonicalName());
//        error.setMessage(e.getMessage());
//        return error;
//    }
}
