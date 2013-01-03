package com.softwaremagico.librodeesher.config;

public class Config {
	private static boolean maximized = false;

	public static boolean isMaximized() {
		return maximized;
	}

	public static void setMaximized(boolean maximized) {
		Config.maximized = maximized;
	}
}
