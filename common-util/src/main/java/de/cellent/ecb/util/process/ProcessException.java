package de.cellent.ecb.util.process;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ProcessException extends Exception {
	private static final long serialVersionUID = 1L;

	public ProcessException() {
		super("Process could not be finished!");
	}

	public ProcessException(String message) {
		super(message);
	}

	public ProcessException(Throwable cause) {
		super(cause);
	}

	public ProcessException(String message, Throwable cause) {
		super(message, cause);
	}
}