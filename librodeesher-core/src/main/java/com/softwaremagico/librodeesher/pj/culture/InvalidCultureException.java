package com.softwaremagico.librodeesher.pj.culture;

public class InvalidCultureException extends Exception {
	private static final long serialVersionUID = -8092175436783593408L;

	public InvalidCultureException(String message) {
		super(message);
	}

	public InvalidCultureException(String message, Exception e) {
		super(message, e);
	}

	public InvalidCultureException(Exception e) {
		super(e);
	}
}
