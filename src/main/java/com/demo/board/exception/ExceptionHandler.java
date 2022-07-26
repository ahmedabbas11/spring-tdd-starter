package com.demo.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {


	@org.springframework.web.bind.annotation.ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleApiExceptions(BaseException baseException, WebRequest request) {

		log.info("handleApiExceptions Exception {} ", baseException);

		return new ResponseEntity(baseException.getMessage(), baseException.getStatusCode());
	}

}
