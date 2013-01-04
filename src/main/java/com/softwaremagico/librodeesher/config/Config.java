package com.softwaremagico.librodeesher.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.softwaremagico.files.RolemasterFolderStructure;

public class Config {
	private static final String MAXIMIZE_WINDOW_PROPERTY = "maximizeWindow";
	private static final String CATEGORY_MAX_COST = "categoryMaxCost";
	private static final String FIREARMS_ALLOWED = "firearmsAllowed";
	private static final String DARK_SPELLS_AS_BASIC_LIST = "darkSpellsAsBasicList";
	private static final String CHI_POWERS = "chiPowers";
	private static final String OTHER_REALM_TRAINING_SPELLS = "otherRealmTrainingSpells";
	private static Properties configuration = new Properties();
	private static Boolean maximized, fireArmsActivated, darkSpellsAsBasic, chiPowersAllowed,
			otherRealmtrainingSpells = false;
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
		configuration.setProperty(FIREARMS_ALLOWED, getFireArmsActivated().toString());
		configuration.setProperty(DARK_SPELLS_AS_BASIC_LIST, getDarkSpellsAsBasic().toString());
		configuration.setProperty(CHI_POWERS, getChiPowersAllowed().toString());
		configuration.setProperty(OTHER_REALM_TRAINING_SPELLS, getOtherRealmtrainingSpells().toString());
	}

	private static void loadConfiguration() {
		// load a properties file
		try {
			configuration.load(new FileInputStream(RolemasterFolderStructure.getConfigurationFilePath()));
			maximized = Boolean.parseBoolean(configuration.getProperty(MAXIMIZE_WINDOW_PROPERTY));
			categoryMaxCost = Integer.parseInt(configuration.getProperty(CATEGORY_MAX_COST));
			fireArmsActivated = Boolean.parseBoolean(configuration.getProperty(FIREARMS_ALLOWED));
			darkSpellsAsBasic = Boolean.parseBoolean(configuration.getProperty(DARK_SPELLS_AS_BASIC_LIST));
			chiPowersAllowed = Boolean.parseBoolean(configuration.getProperty(CHI_POWERS));
			otherRealmtrainingSpells = Boolean.parseBoolean(configuration.getProperty(OTHER_REALM_TRAINING_SPELLS));
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
		storeConfiguration();
	}

	public static Boolean getFireArmsActivated() {
		return fireArmsActivated;
	}

	public static void setFireArmsActivated(Boolean fireArmsActivated) {
		Config.fireArmsActivated = fireArmsActivated;
		storeConfiguration();
	}

	public static Boolean getDarkSpellsAsBasic() {
		return darkSpellsAsBasic;
	}

	public static void setDarkSpellsAsBasic(Boolean darkSpellsAsBasic) {
		Config.darkSpellsAsBasic = darkSpellsAsBasic;
		storeConfiguration();
	}

	public static Boolean getChiPowersAllowed() {
		return chiPowersAllowed;
	}

	public static void setChiPowersAllowed(Boolean chiPowersAllowed) {
		Config.chiPowersAllowed = chiPowersAllowed;
		storeConfiguration();
	}

	public static Boolean getOtherRealmtrainingSpells() {
		return otherRealmtrainingSpells;
	}

	public static void setOtherRealmtrainingSpells(Boolean otherRealmtrainingSpells) {
		Config.otherRealmtrainingSpells = otherRealmtrainingSpells;
	}

}
