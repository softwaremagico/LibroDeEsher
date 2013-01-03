package com.softwaremagico.librodeesher.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.softwaremagico.files.RolemasterFolderStructure;

public class Config {
	private static final String MAXIMIZE_WINDOW_PROPERTY = "maximizeWindow";
	private static final String CATEGORY_MAX_COST = "categoryMaxCost";
	private static Properties configuration = new Properties();
	private static Boolean maximized = false;
	private static Integer categoryMaxCost = 50;

	static {
		loadConfiguration();
	}

	public static boolean isMaximized() {
		return maximized;
	}

	public static void setMaximized(boolean maximized) {
		if (Config.maximized != maximized) {
			Config.maximized = maximized;
			storeConfiguration();
		}
	}

	private static void setProperties() {
		configuration.setProperty(MAXIMIZE_WINDOW_PROPERTY, maximized.toString());
		configuration.setProperty(CATEGORY_MAX_COST, categoryMaxCost.toString());
	}

	private static void loadConfiguration() {
		// load a properties file
		try {
			configuration.load(new FileInputStream(RolemasterFolderStructure.getConfigurationFilePath()));
			maximized = Boolean.parseBoolean(configuration.getProperty(MAXIMIZE_WINDOW_PROPERTY));
			categoryMaxCost = Integer.parseInt(configuration.getProperty(CATEGORY_MAX_COST));
		} catch (Exception e) {
			// File not exist, first time program is executed;
		}
	}

	private static void storeConfiguration() {
		setProperties();
		try {
			configuration.store(new FileOutputStream(RolemasterFolderStructure.getConfigurationFilePath()),
					null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Integer getCategoryMaxCost() {
		return categoryMaxCost;
	}

	public static void setCategoryMaxCost(Integer categoryMaxCost) {
		Config.categoryMaxCost = categoryMaxCost;
	}

}
