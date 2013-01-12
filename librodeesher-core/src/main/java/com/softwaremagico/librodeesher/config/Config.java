package com.softwaremagico.librodeesher.config;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import com.softwaremagico.files.RolemasterFolderStructure;

public class Config {
	private static final String DEFAULT_SEPARATION_CHAR = ";";
	private static final String MAXIMIZE_WINDOW_PROPERTY = "maximizeWindow";
	private static final String CATEGORY_MAX_COST = "categoryMaxCost";
	private static final String FIREARMS_ALLOWED = "firearmsAllowed";
	private static final String DARK_SPELLS_AS_BASIC_LIST = "darkSpellsAsBasicList";
	private static final String CHI_POWERS = "chiPowers";
	private static final String OTHER_REALM_TRAINING_SPELLS = "otherRealmTrainingSpells";
	private static final String DISABLED_MODULES = "disabledModules";
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
		configuration.setProperty(DISABLED_MODULES, getDisabledModules());
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
			otherRealmtrainingSpells = Boolean.parseBoolean(configuration
					.getProperty(OTHER_REALM_TRAINING_SPELLS));
			loadDisabledModules(configuration.getProperty(DISABLED_MODULES));
		} catch (Exception e) {
			// File not exist, first time program is executed;
		}
	}

	public static void storeConfiguration() {
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

	private static String getDisabledModules() {
		String result = "";
		for (String moduleDisabled : RolemasterFolderStructure.getDisabledModules()) {
			result += moduleDisabled + DEFAULT_SEPARATION_CHAR;
		}
		return result;
	}

	private static void loadDisabledModules(String line) {
		String[] values = line.split(DEFAULT_SEPARATION_CHAR);
		RolemasterFolderStructure.setDisabledModules(new ArrayList<String>(Arrays.asList(values)));
	}

}