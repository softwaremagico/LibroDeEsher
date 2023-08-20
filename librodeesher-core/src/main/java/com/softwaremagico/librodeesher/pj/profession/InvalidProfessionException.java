package com.softwaremagico.librodeesher.pj.profession;

public class InvalidProfessionException extends Exception {
	private static final long serialVersionUID = 1279971245000439916L;

	public InvalidProfessionException(String message) {
		super(message);
	}

	public InvalidProfessionException(String message, Exception e) {
		super(message, e);
	}

	public InvalidProfessionException(Exception e) {
		super(e);
	}
}
