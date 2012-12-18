package com.softwaremagico.librodeesher.gui;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
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

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class MainMenu {
	private JMenuItem newMenuItem, loadMenuItem, saveMenuItem, exportToTextMenuItem, exportToPDFMenuItem,
			exitMenuItem;
	private JMenuItem aboutMenuItem, cultureMenuItem, charactMenuItem, trainingMenuItem, skillsMenuItem,
			perksMenuItem, historyMenuItem;
	private JMenuItem insertCharacter;
	private JMenuItem randomName, randomCharacter;
	private JFrame parentWindow;
	private JMenu characterListMenu;

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
		fileMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_A);
		fileMenu.setIcon((Icon) getIcon("folder.png"));
		fileMenu.getAccessibleContext().setAccessibleDescription("Gestion de ficheros.");

		newMenuItem = new JMenuItem("Nuevo", KeyEvent.VK_SHIFT + KeyEvent.VK_N);
		newMenuItem.getAccessibleContext().setAccessibleDescription("Crear un personaje nuevo.");
		newMenuItem.setIcon((Icon) getIcon("new_character.png"));
		fileMenu.add(newMenuItem);

		loadMenuItem = new JMenuItem("Cargar", KeyEvent.VK_SHIFT + KeyEvent.VK_C);
		loadMenuItem.setIcon((Icon) getIcon("load.png"));
		loadMenuItem.getAccessibleContext().setAccessibleDescription(
				"Cargar un personaje creado anteriormente.");
		fileMenu.add(loadMenuItem);

		saveMenuItem = new JMenuItem("Salvar", KeyEvent.VK_SHIFT + KeyEvent.VK_S);
		saveMenuItem.setIcon((Icon) getIcon("save.png"));
		saveMenuItem.getAccessibleContext().setAccessibleDescription(
				"Guardar un personaje creado anteriormente.");
		fileMenu.add(saveMenuItem);

		JMenu exportMenu = new JMenu("Exportar a...");
		exportMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_E);
		exportMenu.setIcon((Icon) getIcon("export.png"));
		exportMenu.getAccessibleContext().setAccessibleDescription("Exportar a otros formatos.");
		fileMenu.add(exportMenu);

		exportToTextMenuItem = new JMenuItem("Texto", KeyEvent.VK_SHIFT + KeyEvent.VK_T);
		exportToTextMenuItem.setIcon((Icon) getIcon("text.png"));
		exportToTextMenuItem.getAccessibleContext().setAccessibleDescription(
				"Exporta a un fichero de texto plano.");
		exportMenu.add(exportToTextMenuItem);

		exportToPDFMenuItem = new JMenuItem("PDF", KeyEvent.VK_SHIFT + KeyEvent.VK_P);
		exportToPDFMenuItem.setIcon((Icon) getIcon("pdf.png"));
		exportToPDFMenuItem.getAccessibleContext().setAccessibleDescription(
				"Exporta a un fichero de texto plano.");
		exportMenu.add(exportToPDFMenuItem);

		exitMenuItem = new JMenuItem("Salir", KeyEvent.VK_SHIFT + KeyEvent.VK_R);
		exitMenuItem.setIcon((Icon) getIcon("exit.png"));
		exitMenuItem.getAccessibleContext().setAccessibleDescription("Cierra el programa.");
		fileMenu.add(exitMenuItem);

		return fileMenu;
	}

	private JMenu createCharacterMenu() {
		JMenu createMenu = new JMenu("Generar");
		createMenu.setIcon((Icon) getIcon("character.png"));
		createMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_G);
		createMenu.getAccessibleContext().setAccessibleDescription("Crear paso a paso un personaje.");

		charactMenuItem = new JMenuItem("Caracteristicas", KeyEvent.VK_SHIFT + KeyEvent.VK_C);
		charactMenuItem.setIcon((Icon) getIcon("characteristics.png"));
		charactMenuItem.getAccessibleContext().setAccessibleDescription("Características del personaje.");
		createMenu.add(charactMenuItem);

		cultureMenuItem = new JMenuItem("Cultura", KeyEvent.VK_SHIFT + KeyEvent.VK_R);
		cultureMenuItem.setIcon((Icon) getIcon("culture.png"));
		cultureMenuItem.getAccessibleContext().setAccessibleDescription("Definir la cultura del personaje.");
		createMenu.add(cultureMenuItem);

		trainingMenuItem = new JMenuItem("Adiestramientos", KeyEvent.VK_SHIFT + KeyEvent.VK_D);
		trainingMenuItem.setIcon((Icon) getIcon("training.png"));
		trainingMenuItem.getAccessibleContext().setAccessibleDescription("Adquirir adiestramientos.");
		createMenu.add(trainingMenuItem);

		skillsMenuItem = new JMenuItem("Habilidades", KeyEvent.VK_SHIFT + KeyEvent.VK_H);
		skillsMenuItem.getAccessibleContext().setAccessibleDescription("Gastar puntos de desarrollo.");
		skillsMenuItem.setIcon((Icon) getIcon("skills.png"));
		createMenu.add(skillsMenuItem);

		perksMenuItem = new JMenuItem("Talentos", KeyEvent.VK_SHIFT + KeyEvent.VK_T);
		perksMenuItem.getAccessibleContext().setAccessibleDescription("Adquirir Talentos.");
		perksMenuItem.setIcon((Icon) getIcon("perk.png"));
		createMenu.add(perksMenuItem);

		historyMenuItem = new JMenuItem("Historial", KeyEvent.VK_SHIFT + KeyEvent.VK_L);
		historyMenuItem.getAccessibleContext().setAccessibleDescription("Adquirir Talentos.");
		historyMenuItem.setIcon((Icon) getIcon("history.png"));
		createMenu.add(historyMenuItem);

		return createMenu;
	}

	private JMenu insertCharacterMenu() {
		JMenu createMenu = new JMenu("Insertar");
		createMenu.setIcon((Icon) getIcon("insert_character.png"));
		createMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_I);
		createMenu.getAccessibleContext().setAccessibleDescription("Insertar un personaje.");

		insertCharacter = new JMenuItem("Insertar personaje", KeyEvent.VK_SHIFT + KeyEvent.VK_P);
		insertCharacter.setIcon((Icon) getIcon("insert_character.png"));
		insertCharacter.getAccessibleContext().setAccessibleDescription("Insertar un personaje.");
		createMenu.add(insertCharacter);

		return createMenu;
	}

	private JMenu createRandomMenu() {
		JMenu randomMenu = new JMenu("Aleatorio");
		randomMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_A);
		randomMenu.getAccessibleContext().setAccessibleDescription("Generar aleatoriamente.");
		randomMenu.setIcon((Icon) getIcon("random_character.png"));

		randomName = new JMenuItem("Nombre Aleatorio", KeyEvent.VK_SHIFT + KeyEvent.VK_N);
		randomName.setIcon((Icon) getIcon("rename.png"));
		randomName.getAccessibleContext().setAccessibleDescription("Genera un nombre para el personaje.");
		randomMenu.add(randomName);

		randomCharacter = new JMenuItem("Pesonaje Aleatorio", KeyEvent.VK_SHIFT + KeyEvent.VK_P);
		randomCharacter.setIcon((Icon) getIcon("random_character.png"));
		randomCharacter.getAccessibleContext().setAccessibleDescription(
				"Genera un personaje de forma completamente aleatoria.");
		randomMenu.add(randomCharacter);

		return randomMenu;
	}

	private JMenu createConfigMenu() {
		JMenu configMenu = new JMenu("Configuración");
		configMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_C);
		configMenu.setIcon((Icon) getIcon("config.png"));
		configMenu.getAccessibleContext().setAccessibleDescription("Opciones de configuración.");

		aboutMenuItem = new JMenuItem("Opciones", KeyEvent.VK_SHIFT + KeyEvent.VK_O);
		aboutMenuItem.setIcon((Icon) getIcon("options.png"));
		aboutMenuItem.getAccessibleContext().setAccessibleDescription("Opciones de configuración.");
		configMenu.add(aboutMenuItem);

		return configMenu;
	}

	private JMenu createCharacterListMenu() {
		characterListMenu = new JMenu("Personajes");
		characterListMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_P);
		characterListMenu.setIcon((Icon) getIcon("character_list.png"));
		characterListMenu.getAccessibleContext().setAccessibleDescription("Personajes abiertos.");
		characterListMenu.add(characterListMenu);

		return characterListMenu;
	}

	public JMenu getCharacterListMenu(){
		return characterListMenu;
	}

	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu("Ayuda");
		helpMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_H);
		helpMenu.setIcon((Icon) getIcon("help.png"));
		helpMenu.getAccessibleContext().setAccessibleDescription("Más información.");

		aboutMenuItem = new JMenuItem("Acerca de...", KeyEvent.VK_SHIFT + KeyEvent.VK_A);
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

	public void addCultureListener(ActionListener al) {
		cultureMenuItem.addActionListener(al);
	}

	public void addNewCharacterListener(ActionListener al) {
		newMenuItem.addActionListener(al);
	}

	class CloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			parentWindow.dispose();
		}
	}

	private ImageIcon getIcon(String iconName) {
		try {
			ImageIcon icon = new ImageIcon(this.getClass().getResource("/icons/" + iconName));
			return new ImageIcon(icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Icon not found: " + iconName, "Main menu");
			return null;
		}
	}
}
