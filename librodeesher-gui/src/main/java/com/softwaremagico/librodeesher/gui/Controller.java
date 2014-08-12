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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;

import com.itextpdf.text.DocumentException;
import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicsWindow;
import com.softwaremagico.librodeesher.gui.culture.CultureWindow;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowForPdf;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowForPdfWithOptions;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowsWithOptionsListener;
import com.softwaremagico.librodeesher.gui.files.PdfFilter;
import com.softwaremagico.librodeesher.gui.history.HistoryWindow;
import com.softwaremagico.librodeesher.gui.perk.PerkWindow;
import com.softwaremagico.librodeesher.gui.persistence.LoadCharacterListener;
import com.softwaremagico.librodeesher.gui.persistence.LoadCharacterPlayerWindow;
import com.softwaremagico.librodeesher.gui.profession.ProfessionWindow;
import com.softwaremagico.librodeesher.gui.random.RandomCharacterUpdatedListener;
import com.softwaremagico.librodeesher.gui.random.RandomWindow;
import com.softwaremagico.librodeesher.gui.skills.SkillWindow;
import com.softwaremagico.librodeesher.gui.training.TrainingWindow;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.pdf.PdfCombinedSheet;
import com.softwaremagico.librodeesher.pj.pdf.PdfStandardSheet;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;
import com.softwaremagico.log.Log;
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerDao;

public class Controller {
	private MainWindow mainGui;
	private AboutWindow aboutWindow;
	private CharacteristicsWindow characteristicWindow;
	private CultureWindow cultureWindow;
	private SkillWindow skillWindow;
	private List<CharacterPlayer> characters;
	private CharacterPlayer selectedCharacter;
	private OptionsWindow optionsWindow;
	private HistoryWindow historyWindow;
	private PerkWindow perksWindow;
	private ProfessionWindow professionWindow;
	private TrainingWindow trainingWindow;
	private RandomWindow randomWindow;
	private LoadCharacterPlayerWindow loadWindow;

	public Controller() {
		characters = new ArrayList<>();
		selectedCharacter = new CharacterPlayer();
		characters.add(selectedCharacter);
		mainGui = new MainWindow();
		mainGui.setCharacter(selectedCharacter);
		mainGui.setVisible(true);
		addMainMenuActionListeners();
		updateCharacterListToMenu();
	}

	private void addMainMenuActionListeners() {
		mainGui.getMainMenu().addNewCharacterListener(new NewCharacterListener());
		mainGui.getMainMenu().addCloseCharacterListener(new CloseCharacterListener());
		mainGui.getMainMenu().addAboutMenuItemListener(new AboutBoxListener());
		mainGui.getMainMenu().addCharacteristicsWindowMenuItemListener(new CharacteristicWindowsListener());
		mainGui.getMainMenu().addRandomNameListener(new RandomNameListener());
		mainGui.getMainMenu().addRandomCharacterListener(new RandomCharacterListener());
		mainGui.getMainMenu().addCultureListener(new CultureWindowsListener());
		mainGui.getMainMenu().addSkillsAndCategoriesListener(new SkillsAndCategoriesWindowsListener());
		mainGui.getMainMenu().addOptionsWindowListener(new OptionsWindowsListener());
		mainGui.getMainMenu().addHistoryWindowListener(new HistoryWindowsListener());
		mainGui.getMainMenu().addPerksWindowListener(new PerksWindowsListener());
		mainGui.getMainMenu().addProfessionWindowListener(new ProfessionWindowsListener());
		mainGui.getMainMenu().addTrainingWindowListener(new TrainingWindowsListener());
		mainGui.getMainMenu().addLevelUpActionListener(new IncreaseLevelActionListener());
		mainGui.getMainMenu().addStandardSheetPdfActionListener(new ExportToStandardPdf());
		mainGui.getMainMenu().addCombinedSheetPdfActionListener(new ExportToCombinedPdf());
		mainGui.getMainMenu().addSaveActionListener(new SaveCharacterPlayer());
		mainGui.getMainMenu().addLoadActionListener(new LoadCharacterPlayer());
	}

	class LoadCharacterPlayer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				loadWindow.dispose();
			} catch (NullPointerException npe) {
			}
			loadWindow = new LoadCharacterPlayerWindow();
			loadWindow.AddLoadCharacterListener(new LoadCharacterListener() {

				@Override
				public void addCharacter(CharacterPlayer characterPlayer) {
					if (!characters.contains(characterPlayer)) {
						characters.add(characterPlayer);
					}
					selectedCharacter = characterPlayer;
					mainGui.setCharacter(selectedCharacter);
					mainGui.updateFrame();
					updateCharacterListToMenu();
				}
			});
			loadWindow.setVisible(true);
		}
	}

	class SaveCharacterPlayer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CharacterPlayerDao.getInstance().makePersistent(selectedCharacter);
				MessageManager.infoMessage(Controller.class.getName(), "Personaje guardado con éxito!", "Salvar.");
			} catch (Exception exception) {
				Log.errorMessage(Controller.class.getName(), exception);
				MessageManager
						.basicErrorMessage(
								Controller.class.getName(),
								"Error almacenando el personaje, parece que algo ha ido mal.\n Comprueba el log del programa para obtener más información al respecto.",
								"Guardar.");
			}
		}
	}

	class ExportToStandardPdf implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForPdfWithOptions selectPdf = new ExploreWindowForPdfWithOptions("Hoja en PDF",
					JFileChooser.FILES_ONLY, getCharacterNameFormatted() + ".pdf", new PdfFilter(), null, "");
			selectPdf.setVisible(true);
			selectPdf.addAcceptListener(new ExploreWindowsWithOptionsListener() {
				@Override
				public void accept(String path, boolean sortSkills) {
					try {
						new PdfStandardSheet(selectedCharacter, path, sortSkills);
						MessageManager.infoMessage(Controller.class.getName(), "Ficha creada correctamente.", "PDF");
					} catch (DocumentException | IOException ex) {
						MessageManager.basicErrorMessage(Controller.class.getName(), "Error al crear el PDF.", "PDF");
						ex.printStackTrace();
					}
				}
			});
		}
	}

	class ExportToCombinedPdf implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForPdf selectPdf = new ExploreWindowForPdf("RMFComb.pdf");
			String path = selectPdf.exploreWindows("Hoja en PDF", JFileChooser.FILES_ONLY, getCharacterNameFormatted()
					+ ".pdf");
			try {
				new PdfCombinedSheet(selectedCharacter, path);
				MessageManager.infoMessage(Controller.class.getName(), "Ficha creada correctamente.", "PDF");
			} catch (DocumentException | IOException ex) {
				MessageManager.basicErrorMessage(Controller.class.getName(), "Error al crear el PDF.", "PDF");
				ex.printStackTrace();
			}
		}
	}

	class NewCharacterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			selectedCharacter = new CharacterPlayer();
			characters.add(selectedCharacter);
			mainGui.setCharacter(selectedCharacter);
			mainGui.updateFrame();
			updateCharacterListToMenu();
		}
	}

	class CloseCharacterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			characters.remove(selectedCharacter);
			if (characters.size() == 0) {
				selectedCharacter = new CharacterPlayer();
				characters.add(selectedCharacter);
			} else {
				selectedCharacter = characters.get(0);
			}
			mainGui.setCharacter(selectedCharacter);
			mainGui.updateFrame();
			updateCharacterListToMenu();
		}
	}

	class RandomNameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			selectedCharacter.setName(selectedCharacter.getRace().getRandonName(selectedCharacter.getSex()));
			mainGui.updateFrame();
			updateCharacterListToMenu();
		}
	}

	class RandomCharacterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				randomWindow.dispose();
			} catch (NullPointerException npe) {
			}
			randomWindow = new RandomWindow(selectedCharacter);
			randomWindow.addRandomCharacterUpdatedListeners(new RandomCharacterUpdatedListener() {
				@Override
				public void updatedCharacter(CharacterPlayer character) {
					characters.remove(selectedCharacter);
					try {
						selectedCharacter = new RandomCharacterPlayer(character, randomWindow.getFinalLevel())
								.getCharacterPlayer();
					} catch (MagicDefinitionException | InvalidProfessionException e) {
						ShowMessage.showErrorMessage(e.getMessage(), "Error");
					}
					characters.add(selectedCharacter);
					// update GUI
					mainGui.setCharacter(selectedCharacter);
					mainGui.updateFrame();
					updateCharacterListToMenu();
					randomWindow.dispose();
				}
			});
			randomWindow.setVisible(true);
		}
	}

	class CharacteristicWindowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				characteristicWindow.dispose();
			} catch (NullPointerException npe) {
			}
			characteristicWindow = new CharacteristicsWindow();
			characteristicWindow.setCharacter(selectedCharacter);
			characteristicWindow.setVisible(true);
		}
	}

	class CultureWindowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				cultureWindow.dispose();
			} catch (NullPointerException npe) {
			}
			cultureWindow = new CultureWindow(selectedCharacter);
			cultureWindow.setVisible(true);
		}
	}

	class SkillsAndCategoriesWindowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				skillWindow.dispose();
			} catch (NullPointerException npe) {
			}
			skillWindow = new SkillWindow(selectedCharacter);
			skillWindow.setVisible(true);
		}
	}

	class AboutBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				aboutWindow.dispose();
			} catch (NullPointerException npe) {
			}
			aboutWindow = new AboutWindow();
			aboutWindow.setVisible(true);
		}
	}

	public void updateCharacterListToMenu() {
		JMenu characterListMenu = mainGui.getMainMenu().getCharacterListMenu();
		characterListMenu.removeAll();
		for (CharacterPlayer character : characters) {
			CharacterMenuItem characterMenu = new CharacterMenuItem(character, selectedCharacter);
			characterMenu.addActionListener(new SelectedCharacterListener(characterMenu));
			characterListMenu.add(characterMenu);
		}
	}

	class SelectedCharacterListener implements ActionListener {
		CharacterMenuItem menu;

		SelectedCharacterListener(CharacterMenuItem menu) {
			this.menu = menu;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedCharacter = menu.getCharacter();
			mainGui.setCharacter(selectedCharacter);
			mainGui.updateFrame();
			updateCharacterListToMenu();
		}
	}

	class OptionsWindowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				optionsWindow.dispose();
			} catch (NullPointerException npe) {
			}
			optionsWindow = new OptionsWindow(selectedCharacter);
			optionsWindow.setVisible(true);
		}
	}

	class HistoryWindowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				historyWindow.dispose();
			} catch (NullPointerException npe) {
			}
			historyWindow = new HistoryWindow(selectedCharacter);
			historyWindow.setVisible(true);
		}
	}

	class PerksWindowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				perksWindow.dispose();
			} catch (NullPointerException npe) {
			}
			perksWindow = new PerkWindow(selectedCharacter);
			perksWindow.setVisible(true);
		}
	}

	class ProfessionWindowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				professionWindow.dispose();
			} catch (NullPointerException npe) {
			}
			professionWindow = new ProfessionWindow(selectedCharacter);
			professionWindow.setVisible(true);
		}
	}

	class TrainingWindowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				trainingWindow.dispose();
			} catch (NullPointerException npe) {
			}
			trainingWindow = new TrainingWindow(selectedCharacter);
			trainingWindow.setVisible(true);
		}
	}

	class IncreaseLevelActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedCharacter.increaseLevel();
			mainGui.updateFrame();
		}
	}

	private String getCharacterNameFormatted() {
		return selectedCharacter.getName().replace(" ", "_") + "_N" + selectedCharacter.getCurrentLevelNumber();
	}

}
