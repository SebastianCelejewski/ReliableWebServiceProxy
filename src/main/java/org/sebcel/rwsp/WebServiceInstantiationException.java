package org.sebcel.rwsp;

public class WebServiceInstantiationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WebServiceInstantiationException(String message) {
		super(message);
	}

	public WebServiceInstantiationException(String message, Exception cause) {
		super(message, cause);
	}
}