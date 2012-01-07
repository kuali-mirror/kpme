package org.kuali.hr.time.exceptions;

public class InvalidTimezoneException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -26480629389554206L;

    public InvalidTimezoneException() {

    }

    public InvalidTimezoneException(String message) {
	super(message);

    }

    public InvalidTimezoneException(Throwable cause) {
	super(cause);

    }

    public InvalidTimezoneException(String message, Throwable cause) {
	super(message, cause);

    }

}
