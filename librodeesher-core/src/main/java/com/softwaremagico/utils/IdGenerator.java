package com.softwaremagico.utils;

import java.util.UUID;

public class IdGenerator {
	public static String createId() {
		UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString();
	}
}
