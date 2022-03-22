package it.fabrick.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DemoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	private String description;

	public DemoException() {
		super();
	}

	public DemoException(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static DemoException throwException(String code, String description) {
		throw new DemoException(code, description);
	}
}
