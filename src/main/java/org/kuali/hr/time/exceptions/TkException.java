package org.kuali.hr.time.exceptions;

import javax.xml.ws.WebFault;

@WebFault(name = "TkException")
public class TkException extends Exception {

	public TkException() {

	}

	public TkException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -678663202716721429L;

}
