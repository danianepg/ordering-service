package com.danianepg.productservice.exceptions;

/**
 * Exception thrown when the a requested element is not found.
 *
 * @author Daniane P. Gomes
 *
 */
public class InvalidDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidDataException(final String message) {
		super(message);
	}

}
