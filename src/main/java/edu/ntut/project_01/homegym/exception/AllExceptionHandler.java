package edu.ntut.project_01.homegym.exception;

import edu.ntut.project_01.homegym.exception.category.LoginException;
import edu.ntut.project_01.homegym.exception.category.RegistrationException;
import edu.ntut.project_01.homegym.exception.category.VerificationMailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AllExceptionHandler {

    //404
    @ExceptionHandler(VerificationMailException.class)
    public ResponseEntity<String> handle(VerificationMailException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    //202
    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<String> handle(RegistrationException exception) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(exception.getMessage());
    }

    //401
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> handle(LoginException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

}
