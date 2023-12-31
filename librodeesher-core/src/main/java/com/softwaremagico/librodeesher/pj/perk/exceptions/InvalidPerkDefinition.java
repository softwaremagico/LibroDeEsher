package com.softwaremagico.librodeesher.pj.perk.exceptions;

public class InvalidPerkDefinition extends Exception {
	private static final long serialVersionUID = -6108109605475345331L;

	public InvalidPerkDefinition(String message) {
		super(message);
	}

	public InvalidPerkDefinition(String message, Exception e) {
		super(message);
	}

	public InvalidPerkDefinition(Exception e) {
		super(e);
	}
}
