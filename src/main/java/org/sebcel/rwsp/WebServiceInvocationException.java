package org.sebcel.rwsp;

public class WebServiceInvocationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WebServiceInvocationException(String message, Exception cause) {
		super(message, cause);
	}
}