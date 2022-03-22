package it.fabrick.demo.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, DemoException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "This should be application specific";
		if (ex instanceof DemoException) {
			DemoException de = (DemoException) ex;

			Error error = new Error();
			error.setCode(de.getCode());
			error.setDescription(de.getDescription());

			return handleExceptionInternal(ex, error, new org.springframework.http.HttpHeaders(),
					HttpStatus.BAD_REQUEST, request);
		}
		return handleExceptionInternal(ex, bodyOfResponse, new org.springframework.http.HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//		Map<String, Object> body = new LinkedHashMap<>();
//		body.put("timestamp", DateUtils.getNow());
//		body.put("status", status.value());
//
//		// Get all errors
//		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
//				.collect(Collectors.toList());
//
//		body.put("errors", errors);
//
//		return new ResponseEntity<>(body, headers, status);
//
//	}

}