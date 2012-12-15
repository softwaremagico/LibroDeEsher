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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenu {
	private JMenuItem newMenuItem, loadMenuItem, saveMenuItem, exportToTextMenuItem, exportToPDFMenuItem,
			exitMenuItem;
	private JMenuItem aboutMenuItem, cultureMenuItem, charactMenuItem, trainingMenuItem, skillsMenuItem,
			perksMenuItem, historyMenuItem;
	private JMenuItem randomName;
	private JFrame parentWindow;

	public JMenuBar createMenu(JFrame parentWindow) {
		this.parentWindow=parentWindow;
		JMenuBar mainMenu = new JMenuBar();

		mainMenu.add(createFileMenu());
		mainMenu.add(createCharacterMenu());
		mainMenu.add(createRandomMenu());
		mainMenu.add(createHelpMenu());

		addExitMenuItemListener();
		return mainMenu;
	}

	private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("Archivo");
		fileMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_A);
		fileMenu.getAccessibleContext().setAccessibleDescription("Gestion de ficheros.");

		newMenuItem = new JMenuItem("Nuevo", KeyEvent.VK_SHIFT + KeyEvent.VK_N);
		newMenuItem.getAccessibleContext().setAccessibleDescription("Crear un personaje nuevo.");
		fileMenu.add(newMenuItem);

		loadMenuItem = new JMenuItem("Cargar", KeyEvent.VK_SHIFT + KeyEvent.VK_C);
		loadMenuItem.getAccessibleContext().setAccessibleDescription(
				"Cargar un personaje creado anteriormente.");
		fileMenu.add(loadMenuItem);

		saveMenuItem = new JMenuItem("Salvar", KeyEvent.VK_SHIFT + KeyEvent.VK_S);
		saveMenuItem.getAccessibleContext().setAccessibleDescription(
				"Guardar un personaje creado anteriormente.");
		fileMenu.add(saveMenuItem);

		JMenu exportMenu = new JMenu("Exportar");
		exportMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_E);
		exportMenu.getAccessibleContext().setAccessibleDescription("Exportar a otros formatos.");
		fileMenu.add(exportMenu);

		exportToTextMenuItem = new JMenuItem("Texto", KeyEvent.VK_SHIFT + KeyEvent.VK_T);
		exportToTextMenuItem.getAccessibleContext().setAccessibleDescription(
				"Exporta a un fichero de texto plano.");
		exportMenu.add(exportToTextMenuItem);

		exportToPDFMenuItem = new JMenuItem("PDF", KeyEvent.VK_SHIFT + KeyEvent.VK_P);
		exportToPDFMenuItem.getAccessibleContext().setAccessibleDescription(
				"Exporta a un fichero de texto plano.");
		exportMenu.add(exportToPDFMenuItem);

		exitMenuItem = new JMenuItem("Salir", KeyEvent.VK_SHIFT + KeyEvent.VK_R);
		exitMenuItem.getAccessibleContext().setAccessibleDescription("Cierra el programa.");
		fileMenu.add(exitMenuItem);

		return fileMenu;
	}

	private JMenu createCharacterMenu() {
		JMenu createMenu = new JMenu("Generar personaje");
		createMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_G);
		createMenu.getAccessibleContext().setAccessibleDescription("Crear paso a paso un personaje.");

		charactMenuItem = new JMenuItem("Caracteristicas", KeyEvent.VK_SHIFT + KeyEvent.VK_C);
		charactMenuItem.getAccessibleContext().setAccessibleDescription("Características del personaje.");
		createMenu.add(charactMenuItem);

		cultureMenuItem = new JMenuItem("Cultura", KeyEvent.VK_SHIFT + KeyEvent.VK_R);
		cultureMenuItem.getAccessibleContext().setAccessibleDescription("Definir la cultura del personaje.");
		createMenu.add(cultureMenuItem);

		trainingMenuItem = new JMenuItem("Adiestramientos", KeyEvent.VK_SHIFT + KeyEvent.VK_D);
		trainingMenuItem.getAccessibleContext().setAccessibleDescription("Adquirir adiestramientos.");
		createMenu.add(trainingMenuItem);

		skillsMenuItem = new JMenuItem("Habilidades", KeyEvent.VK_SHIFT + KeyEvent.VK_H);
		skillsMenuItem.getAccessibleContext().setAccessibleDescription("Gastar puntos de desarrollo.");
		createMenu.add(skillsMenuItem);

		perksMenuItem = new JMenuItem("Talentos", KeyEvent.VK_SHIFT + KeyEvent.VK_T);
		perksMenuItem.getAccessibleContext().setAccessibleDescription("Adquirir Talentos.");
		createMenu.add(perksMenuItem);

		historyMenuItem = new JMenuItem("Historial", KeyEvent.VK_SHIFT + KeyEvent.VK_L);
		historyMenuItem.getAccessibleContext().setAccessibleDescription("Adquirir Talentos.");
		createMenu.add(historyMenuItem);

		return createMenu;
	}
	
	private JMenu createRandomMenu() {
		JMenu randomMenu = new JMenu("Aleatorio");
		randomMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_A);
		randomMenu.getAccessibleContext().setAccessibleDescription("Generar aleatoriamente.");

		randomName = new JMenuItem("Nombre Aleatorio", KeyEvent.VK_SHIFT + KeyEvent.VK_N);
		randomName.getAccessibleContext().setAccessibleDescription("Genera un nombre para el personaje.");
		randomMenu.add(randomName);

		return randomMenu;
	}

	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu("Ayuda");
		helpMenu.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription("Más información.");

		aboutMenuItem = new JMenuItem("Acerca de...", KeyEvent.VK_SHIFT + KeyEvent.VK_A);
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

	private void addExitMenuItemListener(){
		exitMenuItem.addActionListener(new CloseListener());
	}
	
	public void addRandomNameListener(ActionListener al){
		randomName.addActionListener(al);
	}
	
	class CloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			parentWindow.dispose();
		}
	}
}
