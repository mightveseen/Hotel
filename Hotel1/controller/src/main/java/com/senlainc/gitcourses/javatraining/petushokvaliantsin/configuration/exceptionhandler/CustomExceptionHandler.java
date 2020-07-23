package com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.exceptionhandler;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementAlreadyExistsException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotAvailableException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotFoundException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.IncorrectDataException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.NoMatchException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.dao.CreateQueryException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.dao.DeleteQueryException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.dao.ReadQueryException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.dao.UpdateQueryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<Object> elementNotFoundResponse(ElementNotFoundException e) {
        logger.info(e);
        return new ResponseEntity<>(new ExceptionTemplate(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ElementNotAvailableException.class)
    public ResponseEntity<Object> elementNotAvailableResponse(ElementNotAvailableException e) {
        logger.info(e);
        return new ResponseEntity<>(new ExceptionTemplate(e.getMessage()), HttpStatus.LOCKED);
    }

    @ExceptionHandler(ElementAlreadyExistsException.class)
    public ResponseEntity<Object> elementAlreadyExistsResponse(ElementAlreadyExistsException e) {
        logger.info(e);
        return new ResponseEntity<>(new ExceptionTemplate(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ReadQueryException.class, CreateQueryException.class, UpdateQueryException.class, DeleteQueryException.class})
    public ResponseEntity<Object> failedQueryResponse(RuntimeException e) {
        logger.warn(e);
        return new ResponseEntity<>(new ExceptionTemplate(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgument(IllegalArgumentException e) {
        logger.warn(e);
        return new ResponseEntity<>(new ExceptionTemplate(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoMatchException.class)
    public ResponseEntity<Object> noMathResponse(NoMatchException e) {
        logger.info(e);
        return new ResponseEntity<>(new ExceptionTemplate(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<Object> illegalArgument(IncorrectDataException e) {
        logger.warn(e);
        return new ResponseEntity<>(new ExceptionTemplate(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
