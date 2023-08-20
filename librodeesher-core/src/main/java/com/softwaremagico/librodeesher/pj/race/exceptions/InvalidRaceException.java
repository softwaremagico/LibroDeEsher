package com.softwaremagico.librodeesher.pj.race.exceptions;

public class InvalidRaceException extends Exception {
	private static final long serialVersionUID = -8641195265985327213L;

	public InvalidRaceException(String message) {
		super(message);
	}
	
	public InvalidRaceException(String message, Exception e) {
		super(message, e);
	}
	
	public InvalidRaceException(Exception e) {
		super(e);
	}
	
}
