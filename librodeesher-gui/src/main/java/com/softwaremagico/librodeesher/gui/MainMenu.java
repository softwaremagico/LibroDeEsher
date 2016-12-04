package com.softwaremagico.librodeesher.gui;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
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

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class MainMenu {
	private JMenuItem newMenuItem, closeMenuItem, loadMenuItem, saveMenuItem, exportToTextMenuItem,
			exportAbbreviatedToTextMenuItem, exportToPdfStandardMenuItem, exportToPdfCombined2ColumnsMenuItem,
			exportToPdfCombined1ColumnMenuItem, exportCharacterMenuItem, exportLevelMenuItem, importCharacterMenuItem,
			importLevelMenuItem, exitMenuItem;
	private JMenuItem aboutMenuItem, cultureMenuItem, professionMenuItem, charactMenuItem, trainingMenuItem,
			skillsMenuItem, perksMenuItem, backgroundMenuItem, historyMenuItem, levelUpMenuItem;
	private JMenuItem insertMagicObject, insertSkillRanks;
	private JMenuItem randomName, randomCharacter;
	private JMenuItem optionsMenu;
	private JCheckBoxMenuItem enableDebug;
	private JFrame parentWindow;
	private JMenu characterListMenu, exportMenu, importMenu;
	private CharacterPlayer character;

	public JMenuBar createMenu(JFrame parentWindow) {
		this.parentWindow = parentWindow;
		JMenuBar mainMenu = new JMenuBar();

		mainMenu.add(createFileMenu());
		mainMenu.add(createCharacterMenu());
		mainMenu.add(insertCharacterMenu());
		mainMenu.add(createRandomMenu());
		mainMenu.add(createCharacterListMenu());
		mainMenu.add(createConfigMenu());
		mainMenu.add(createHelpMenu());

		addExitMenuItemListener();
		return mainMenu;
	}

	private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("Archivo");
		fileMenu.setMnemonic(KeyEvent.VK_A);
		fileMenu.setIcon((Icon) getIcon("folder.png"));
		fileMenu.getAccessibleContext().setAccessibleDescription("Gestion de ficheros.");

		newMenuItem = new JMenuItem("Nuevo", KeyEvent.VK_N);
		newMenuItem.getAccessibleContext().setAccessibleDescription("Crear un personaje nuevo.");
		newMenuItem.setIcon((Icon) getIcon("new_character.png"));
		fileMenu.add(newMenuItem);

		closeMenuItem = new JMenuItem("Cerrar", KeyEvent.VK_C);
		closeMenuItem.getAccessibleContext().setAccessibleDescription("Cerrar el personaje actualmente abierto.");
		closeMenuItem.setIcon((Icon) getIcon("close_character.png"));
		fileMenu.add(closeMenuItem);

		loadMenuItem = new JMenuItem("Cargar", KeyEvent.VK_A);
		loadMenuItem.setIcon((Icon) getIcon("load.png"));
		loadMenuItem.getAccessibleContext().setAccessibleDescription("Cargar un personaje creado anteriormente.");
		fileMenu.add(loadMenuItem);

		saveMenuItem = new JMenuItem("Salvar", KeyEvent.VK_S);
		saveMenuItem.setIcon((Icon) getIcon("save.png"));
		saveMenuItem.getAccessibleContext().setAccessibleDescription("Guardar un personaje creado anteriormente.");
		fileMenu.add(saveMenuItem);

		exportMenu = new JMenu("Exportar...");
		exportMenu.setMnemonic(KeyEvent.VK_E);
		exportMenu.setIcon((Icon) getIcon("export.png"));
		exportMenu.getAccessibleContext().setAccessibleDescription("Exportar a otros formatos.");
		fileMenu.add(exportMenu);

		exportCharacterMenuItem = new JMenuItem("Personaje", KeyEvent.VK_P);
		exportCharacterMenuItem.setIcon((Icon) getIcon("character-export.png"));
		exportCharacterMenuItem.getAccessibleContext().setAccessibleDescription(
				"Exporta para importar en otro ordenador.");
		exportMenu.add(exportCharacterMenuItem);

		exportLevelMenuItem = new JMenuItem("Subida de Nivel", KeyEvent.VK_P);
		exportLevelMenuItem.setIcon((Icon) getIcon("level-export.png"));
		exportLevelMenuItem.getAccessibleContext().setAccessibleDescription("Exporta para importar en otro ordenador.");
		exportMenu.add(exportLevelMenuItem);

		exportToTextMenuItem = new JMenuItem("Texto", KeyEvent.VK_T);
		exportToTextMenuItem.setIcon((Icon) getIcon("text.png"));
		exportToTextMenuItem.getAccessibleContext().setAccessibleDescription("Exporta a un fichero de texto plano.");
		exportMenu.add(exportToTextMenuItem);

		exportAbbreviatedToTextMenuItem = new JMenuItem("Texto (Resumen)", KeyEvent.VK_T);
		exportAbbreviatedToTextMenuItem.setIcon((Icon) getIcon("text.png"));
		exportAbbreviatedToTextMenuItem.getAccessibleContext().setAccessibleDescription(
				"Exporta un resumen a un fichero de texto plano.");
		exportMenu.add(exportAbbreviatedToTextMenuItem);

		exportToPdfStandardMenuItem = new JMenuItem("PDF Estándar", KeyEvent.VK_P);
		exportToPdfStandardMenuItem.setIcon((Icon) getIcon("pdf.png"));
		exportToPdfStandardMenuItem.getAccessibleContext().setAccessibleDescription(
				"Exporta a un fichero PDF (Hoja Estándar).");
		exportMenu.add(exportToPdfStandardMenuItem);

		exportToPdfCombined1ColumnMenuItem = new JMenuItem("PDF Combinada", KeyEvent.VK_P);
		exportToPdfCombined1ColumnMenuItem.setIcon((Icon) getIcon("pdf.png"));
		exportToPdfCombined1ColumnMenuItem.getAccessibleContext().setAccessibleDescription(
				"Exporta a un fichero PDF (Hoja Combinada).");
		exportMenu.add(exportToPdfCombined1ColumnMenuItem);

		exportToPdfCombined2ColumnsMenuItem = new JMenuItem("PDF Combinada (2 columnas)", KeyEvent.VK_P);
		exportToPdfCombined2ColumnsMenuItem.setIcon((Icon) getIcon("pdf.png"));
		exportToPdfCombined2ColumnsMenuItem.getAccessibleContext().setAccessibleDescription(
				"Exporta a un fichero PDF (Hoja Combinada, 2 columnas por hoja).");
		exportMenu.add(exportToPdfCombined2ColumnsMenuItem);

		importMenu = new JMenu("Importar...");
		importMenu.setMnemonic(KeyEvent.VK_I);
		importMenu.setIcon((Icon) getIcon("import.png"));
		importMenu.getAccessibleContext().setAccessibleDescription("Importar de otros formatos.");
		fileMenu.add(importMenu);

		importCharacterMenuItem = new JMenuItem("Personaje");
		importCharacterMenuItem.setIcon((Icon) getIcon("character-import.png"));
		importCharacterMenuItem.getAccessibleContext().setAccessibleDescription("Importar de otro ordenador.");
		importMenu.add(importCharacterMenuItem);

		importLevelMenuItem = new JMenuItem("Nivel");
		importLevelMenuItem.setIcon((Icon) getIcon("level-import.png"));
		importLevelMenuItem.getAccessibleContext().setAccessibleDescription("Importar de otro ordenador.");
		importMenu.add(importLevelMenuItem);

		exitMenuItem = new JMenuItem("Salir", KeyEvent.VK_R);
		exitMenuItem.setIcon((Icon) getIcon("exit.png"));
		exitMenuItem.getAccessibleContext().setAccessibleDescription("Cierra el programa.");
		fileMenu.add(exitMenuItem);

		return fileMenu;
	}

	private JMenu createCharacterMenu() {
		JMenu createMenu = new JMenu("Generar");
		createMenu.setIcon((Icon) getIcon("character_male.png"));
		createMenu.setMnemonic(KeyEvent.VK_G);
		createMenu.getAccessibleContext().setAccessibleDescription("Crear paso a paso un personaje.");

		charactMenuItem = new JMenuItem("Características", KeyEvent.VK_C);
		charactMenuItem.setIcon((Icon) getIcon("characteristics.png"));
		charactMenuItem.getAccessibleContext().setAccessibleDescription("Características del personaje.");
		createMenu.add(charactMenuItem);

		cultureMenuItem = new JMenuItem("Cultura", KeyEvent.VK_U);
		cultureMenuItem.setIcon((Icon) getIcon("culture.png"));
		cultureMenuItem.getAccessibleContext().setAccessibleDescription("Definir la cultura del personaje.");
		createMenu.add(cultureMenuItem);

		professionMenuItem = new JMenuItem("Profesión", KeyEvent.VK_P);
		professionMenuItem.setIcon((Icon) getIcon("profession.png"));
		professionMenuItem.getAccessibleContext().setAccessibleDescription("Definir la profesión del personaje.");
		createMenu.add(professionMenuItem);

		trainingMenuItem = new JMenuItem("Adiestramientos", KeyEvent.VK_R);
		trainingMenuItem.setIcon((Icon) getIcon("training.png"));
		trainingMenuItem.getAccessibleContext().setAccessibleDescription("Adquirir adiestramientos.");
		createMenu.add(trainingMenuItem);

		skillsMenuItem = new JMenuItem("Habilidades", KeyEvent.VK_H);
		skillsMenuItem.getAccessibleContext().setAccessibleDescription("Gastar puntos de desarrollo.");
		skillsMenuItem.setIcon((Icon) getIcon("skills.png"));
		createMenu.add(skillsMenuItem);

		perksMenuItem = new JMenuItem("Talentos", KeyEvent.VK_T);
		perksMenuItem.getAccessibleContext().setAccessibleDescription("Adquirir Talentos.");
		perksMenuItem.setIcon((Icon) getIcon("perk.png"));
		createMenu.add(perksMenuItem);

		backgroundMenuItem = new JMenuItem("Historial", KeyEvent.VK_L);
		backgroundMenuItem.getAccessibleContext().setAccessibleDescription("Puntos de historial.");
		backgroundMenuItem.setIcon((Icon) getIcon("history.png"));
		createMenu.add(backgroundMenuItem);		
		

		levelUpMenuItem = new JMenuItem("Subir Nivel");
		levelUpMenuItem.getAccessibleContext().setAccessibleDescription("Subir un nivel.");
		levelUpMenuItem.setIcon((Icon) getIcon("level_up.png"));
		createMenu.add(levelUpMenuItem);

		return createMenu;
	}

	private JMenu insertCharacterMenu() {
		JMenu createMenu = new JMenu("Insertar");
		createMenu.setIcon((Icon) getIcon("insert_character.png"));
		createMenu.setMnemonic(KeyEvent.VK_I);
		createMenu.getAccessibleContext().setAccessibleDescription("Insertar elementos al personaje.");

		insertMagicObject = new JMenuItem("Insertar Objeto Mágico", KeyEvent.VK_M);
		insertMagicObject.setIcon((Icon) getIcon("potion.png"));
		insertMagicObject.getAccessibleContext().setAccessibleDescription("Insertar un objeto mágico.");
		createMenu.add(insertMagicObject);

		insertSkillRanks = new JMenuItem("Añadir Rangos", KeyEvent.VK_R);
		insertSkillRanks.setIcon((Icon) getIcon("addRanks.png"));
		insertSkillRanks.getAccessibleContext().setAccessibleDescription("Insertar un objeto mágico.");
		createMenu.add(insertSkillRanks);
		
		
		historyMenuItem = new JMenuItem("Trasfondo", KeyEvent.VK_L);
		historyMenuItem.getAccessibleContext().setAccessibleDescription("Puntos de historial.");
		historyMenuItem.setIcon((Icon) getIcon("book.png"));
		createMenu.add(historyMenuItem);

		// insertEquipment = new JMenuItem("Insertar Equipo", KeyEvent.VK_E);
		// insertEquipment.setIcon((Icon) getIcon("horse.png"));
		// insertEquipment.getAccessibleContext().setAccessibleDescription("Insertar equipo.");
		// createMenu.add(insertEquipment);

		return createMenu;
	}

	private JMenu createRandomMenu() {
		JMenu randomMenu = new JMenu("Aleatorio");
		randomMenu.setMnemonic(KeyEvent.VK_L);
		randomMenu.getAccessibleContext().setAccessibleDescription("Generar aleatoriamente.");
		randomMenu.setIcon((Icon) getIcon("random_character.png"));

		randomName = new JMenuItem("Nombre Aleatorio", KeyEvent.VK_N);
		randomName.setIcon((Icon) getIcon("rename.png"));
		randomName.getAccessibleContext().setAccessibleDescription("Genera un nombre para el personaje.");
		randomMenu.add(randomName);

		randomMenu.addSeparator();

		randomCharacter = new JMenuItem("Pesonaje Aleatorio", KeyEvent.VK_P);
		randomCharacter.setIcon((Icon) getIcon("random_character.png"));
		randomCharacter.getAccessibleContext().setAccessibleDescription(
				"Genera un personaje de forma completamente aleatoria.");
		randomMenu.add(randomCharacter);

		return randomMenu;
	}

	private JMenu createConfigMenu() {
		JMenu configMenu = new JMenu("Configuración");
		configMenu.setMnemonic(KeyEvent.VK_C);
		configMenu.setIcon((Icon) getIcon("config.png"));
		configMenu.getAccessibleContext().setAccessibleDescription("Opciones de configuración.");

		optionsMenu = new JMenuItem("Opciones", KeyEvent.VK_O);
		optionsMenu.setIcon((Icon) getIcon("options.png"));
		optionsMenu.getAccessibleContext().setAccessibleDescription("Opciones de configuración.");
		configMenu.add(optionsMenu);

		enableDebug = new JCheckBoxMenuItem("Debug");
		enableDebug.setIcon((Icon) getIcon("debug.png"));
		enableDebug.getAccessibleContext().setAccessibleDescription("Visualiza los errores de la aplicación.");
		enableDebug.setSelected(Config.isDebugEnabled());
		configMenu.add(enableDebug);

		return configMenu;
	}

	private JMenu createCharacterListMenu() {
		characterListMenu = new JMenu("Personajes");
		characterListMenu.setMnemonic(KeyEvent.VK_P);
		characterListMenu.setIcon((Icon) getIcon("character_list.png"));
		characterListMenu.getAccessibleContext().setAccessibleDescription("Personajes abiertos.");
		characterListMenu.add(characterListMenu);

		return characterListMenu;
	}

	public JMenu getCharacterListMenu() {
		return characterListMenu;
	}

	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu("Ayuda");
		helpMenu.setMnemonic(KeyEvent.VK_Y);
		helpMenu.setIcon((Icon) getIcon("help.png"));
		helpMenu.getAccessibleContext().setAccessibleDescription("Más información.");

		aboutMenuItem = new JMenuItem("Acerca de...", KeyEvent.VK_A);
		aboutMenuItem.setIcon((Icon) getIcon("about.png"));
		aboutMenuItem.getAccessibleContext().setAccessibleDescription("Sobre el programa.");
		helpMenu.add(aboutMenuItem);

		return helpMenu;
	}

	public void addAboutMenuItemListener(ActionListener al) {
		aboutMenuItem.addActionListener(al);
	}

	public void addCharacteristicsWindowMenuItemListener(ActionListener al) {
		charactMenuItem.addActionListener(al);
	}

	private void addExitMenuItemListener() {
		exitMenuItem.addActionListener(new CloseListener());
	}

	public void addRandomNameListener(ActionListener al) {
		randomName.addActionListener(al);
	}

	public void addRandomCharacterListener(ActionListener al) {
		randomCharacter.addActionListener(al);
	}

	public void addCultureListener(ActionListener al) {
		cultureMenuItem.addActionListener(al);
	}

	public void addSkillsAndCategoriesListener(ActionListener al) {
		skillsMenuItem.addActionListener(al);
	}

	public void addNewCharacterListener(ActionListener al) {
		newMenuItem.addActionListener(al);
	}

	public void addCloseCharacterListener(ActionListener al) {
		closeMenuItem.addActionListener(al);
	}

	public void addOptionsWindowListener(ActionListener al) {
		optionsMenu.addActionListener(al);
	}

	public void addBackgroundWindowListener(ActionListener al) {
		backgroundMenuItem.addActionListener(al);
	}
	
	public void addHistoryWindowListener(ActionListener al){
		historyMenuItem.addActionListener(al);
	}

	public void addPerksWindowListener(ActionListener al) {
		perksMenuItem.addActionListener(al);
	}

	public void addTrainingWindowListener(ActionListener al) {
		trainingMenuItem.addActionListener(al);
	}

	public void addProfessionWindowListener(ActionListener al) {
		professionMenuItem.addActionListener(al);
	}

	public void addLevelUpActionListener(ActionListener al) {
		levelUpMenuItem.addActionListener(al);
	}

	public void addStandardSheetPdfActionListener(ActionListener al) {
		exportToPdfStandardMenuItem.addActionListener(al);
	}

	public void addCombinedSheetPdf1ColumnActionListener(ActionListener al) {
		exportToPdfCombined1ColumnMenuItem.addActionListener(al);
	}

	public void addCombinedSheetPdf2ColumnsActionListener(ActionListener al) {
		exportToPdfCombined2ColumnsMenuItem.addActionListener(al);
	}

	public void addStandardTxtActionListener(ActionListener al) {
		exportToTextMenuItem.addActionListener(al);
	}

	public void addAbbreviatedTxtActionListener(ActionListener al) {
		exportAbbreviatedToTextMenuItem.addActionListener(al);
	}

	public void addSaveActionListener(ActionListener al) {
		saveMenuItem.addActionListener(al);
	}

	public void addLoadActionListener(ActionListener al) {
		loadMenuItem.addActionListener(al);
	}

	public void addExportCharacterListener(ActionListener al) {
		exportCharacterMenuItem.addActionListener(al);
	}

	public void addImportCharacterListener(ActionListener al) {
		importCharacterMenuItem.addActionListener(al);
	}

	public void addExportLevelListener(ActionListener al) {
		exportLevelMenuItem.addActionListener(al);
	}

	public void addImportLevelListener(ActionListener al) {
		importLevelMenuItem.addActionListener(al);
	}

	public void addInsertMagicItemListener(ActionListener al) {
		insertMagicObject.addActionListener(al);
	}

	public void addEnableDebugItemListener(ActionListener al) {
		enableDebug.addActionListener(al);
	}

	public void addRanksItemListener(ActionListener al) {
		insertSkillRanks.addActionListener(al);
	}

	class CloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			parentWindow.dispose();
		}
	}

	public void setCharacter(CharacterPlayer character) {
		this.character = character;
	}

	public void update() {
		boolean enable = character.areCharacteristicsConfirmed();
		// charactMenuItem.setEnabled(!enable);
		cultureMenuItem.setEnabled(enable && character.getLevelUps().size() == 1);
		professionMenuItem.setEnabled(enable
				&& character.getLevelUps().size() == 1
				&& (!character.getProfession().getProfessionalSkillsToChoose().isEmpty()
						|| !character.getProfession().getCommonSkillsToChoose().isEmpty() || !character.getProfession()
						.getRestrictedSkillsToChoose().isEmpty()));
		trainingMenuItem.setEnabled(enable);
		perksMenuItem.setEnabled(enable && character.getLevelUps().size() == 1);
		skillsMenuItem.setEnabled(enable);
		backgroundMenuItem.setEnabled(enable && character.getLevelUps().size() == 1);
		historyMenuItem.setEnabled(enable);
		insertSkillRanks.setEnabled(enable);
		insertMagicObject.setEnabled(enable);
		isCharacterWellFormed();
	}

	public boolean isDebugEnabled() {
		return enableDebug.isSelected();
	}

	private void isCharacterWellFormed() {
		Integer points = character.getRemainingDevelopmentPoints();
		if (points < 0 || !character.areCharacteristicsConfirmed()) {
			enableCharacterExports(false);
		} else {
			enableCharacterExports(true);
		}
	}

	private void enableCharacterExports(boolean value) {
		exportMenu.setEnabled(value);
		levelUpMenuItem.setEnabled(value);
		saveMenuItem.setEnabled(value);
	}

	public static ImageIcon getIcon(String iconName) {
		try {
			ImageIcon icon = new ImageIcon(MainMenu.class.getResource("/icons/" + iconName));
			return new ImageIcon(icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Icon not found: " + iconName, "Main menu");
			return null;
		}
	}
}
