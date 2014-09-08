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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;

import com.itextpdf.text.DocumentException;
import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicsWindow;
import com.softwaremagico.librodeesher.gui.culture.CultureWindow;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowForPdf;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowForPdfWithOptions;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowForRlm;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowForTxt;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowsWithOptionsListener;
import com.softwaremagico.librodeesher.gui.files.PdfFilter;
import com.softwaremagico.librodeesher.gui.history.HistoryWindow;
import com.softwaremagico.librodeesher.gui.perk.PerkWindow;
import com.softwaremagico.librodeesher.gui.persistence.LoadCharacterListener;
import com.softwaremagico.librodeesher.gui.persistence.LoadCharacterPlayerWindow;
import com.softwaremagico.librodeesher.gui.profession.ProfessionWindow;
import com.softwaremagico.librodeesher.gui.random.RandomCharacterUpdatedListener;
import com.softwaremagico.librodeesher.gui.random.RandomSplashScreen;
import com.softwaremagico.librodeesher.gui.random.RandomWindow;
import com.softwaremagico.librodeesher.gui.skills.SkillWindow;
import com.softwaremagico.librodeesher.gui.training.TrainingWindow;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.export.json.CharacterJsonManager;
import com.softwaremagico.librodeesher.pj.export.json.LevelJsonManager;
import com.softwaremagico.librodeesher.pj.export.json.exceptions.InvalidCharacterException;
import com.softwaremagico.librodeesher.pj.export.json.exceptions.InvalidLevelException;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfCombinedSheet;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfStandardSheet;
import com.softwaremagico.librodeesher.pj.export.txt.TxtSheet;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;
import com.softwaremagico.librodeesher.pj.random.RandomFeedbackListener;
import com.softwaremagico.log.EsherLog;
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

	private boolean actionsEnables = true;

	public Controller() {
		characters = new ArrayList<>();
		selectedCharacter = new CharacterPlayer();
		characters.add(selectedCharacter);
		mainGui = new MainWindow();
		mainGui.setCharacter(selectedCharacter);
		mainGui.updateSkills();
		mainGui.setVisible(true);
		addMainMenuActionListeners();
		updateCharacterListToMenu();
	}

	private void addCharacterPlayer(CharacterPlayer characterPlayer) {
		if (!characters.contains(characterPlayer)) {
			characters.add(characterPlayer);
			selectedCharacter = characterPlayer;
			mainGui.setCharacter(selectedCharacter);
			mainGui.updateFrame();
			updateCharacterListToMenu();
		}
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
		mainGui.getMainMenu().addStandardTxtActionListener(new ExportToStandardTxt());
		mainGui.getMainMenu().addAbbreviatedTxtActionListener(new ExportAbbreviatedToTxt());
		mainGui.getMainMenu().addSaveActionListener(new SaveCharacterPlayer());
		mainGui.getMainMenu().addLoadActionListener(new LoadCharacterPlayer());
		mainGui.getMainMenu().addExportCharacterListener(new ExportCharacter());
		mainGui.getMainMenu().addImportCharacterListener(new ImportCharacter());
		mainGui.getMainMenu().addExportLevelListener(new ExportLevel());
		mainGui.getMainMenu().addImportLevelListener(new ImportLevel());
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
					addCharacterPlayer(characterPlayer);
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
				EsherLog.errorMessage(Controller.class.getName(), exception);
				MessageManager
						.basicErrorMessage(
								Controller.class.getName(),
								"Error almacenando el personaje, parece que algo ha ido mal.\n Comprueba el log del programa para obtener más información al respecto.",
								"Guardar.");
			}
		}
	}

	class ExportLevel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForRlm selectJson = new ExploreWindowForRlm("Personaje.rlm");
			String path = selectJson.exploreWindows("Exportar Nivel", JFileChooser.FILES_ONLY,
					getCharacterNameFormatted() + ".rlm");
			String jsonText = LevelJsonManager.toJson(selectedCharacter);
			// store in a file.
			PrintWriter out;
			try {
				out = new PrintWriter(path);
				out.println(jsonText);
				out.close();
				MessageManager.infoMessage(Controller.class.getName(), "Nivel exportado correctamente.", "RLM");
				return;
			} catch (FileNotFoundException e1) {
			}
			MessageManager.basicErrorMessage(Controller.class.getName(), "Error al exportar el nivel!", "RLM");
		}
	}

	class ImportLevel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForRlm selectTxt = new ExploreWindowForRlm("Personaje.rlm");
			String path = selectTxt.exploreWindows("Importar Nivel", JFileChooser.FILES_ONLY,
					getCharacterNameFormatted().substring(0, getCharacterNameFormatted().length() - 2)
							+ (selectedCharacter.getCurrentLevelNumber() + 1) + ".rlm");

			BufferedReader br = null;
			String jsonText = "";
			try {
				String sCurrentLine;
				br = new BufferedReader(new FileReader(path));
				while ((sCurrentLine = br.readLine()) != null) {
					jsonText += sCurrentLine;
				}
				try {
					LevelJsonManager.fromJson(selectedCharacter, jsonText);
					MessageManager.infoMessage(Controller.class.getName(), "Nivel importado correctamente.", "RLM");
				} catch (InvalidCharacterException ice) {
					MessageManager
							.basicErrorMessage(
									Controller.class.getName(),
									"Nivel invalido para importar. El nivel está definido para un personaje distinto al actualmente seleccionado.'",
									"RLM");
				} catch (InvalidLevelException ile) {
					MessageManager
							.basicErrorMessage(
									Controller.class.getName(),
									"Nivel inválido para importar. Probablemente sea un fichero que contenga un nivel antiguo o erróneo.",
									"RLM");
				}
			} catch (Exception exc) {
				EsherLog.errorMessage(Controller.class.getName(), exc);
				MessageManager.basicErrorMessage(Controller.class.getName(), "Error al importar el nivel!", "RLM");
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException ex) {
					// Continue
				}
			}
		}
	}

	class ExportCharacter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForRlm selectJson = new ExploreWindowForRlm("Personaje.rlm");
			String path = selectJson.exploreWindows("Exportar Personaje", JFileChooser.FILES_ONLY,
					getCharacterNameFormatted() + ".rlm");
			String jsonText = CharacterJsonManager.toJson(selectedCharacter);
			// store in a file.
			PrintWriter out;
			try {
				out = new PrintWriter(path);
				out.println(jsonText);
				out.close();
				MessageManager.infoMessage(Controller.class.getName(), "Personaje exportado correctamente.", "RLM");
				return;
			} catch (FileNotFoundException e1) {
			}
			MessageManager.basicErrorMessage(Controller.class.getName(), "Error al exportar el personaje!", "RLM");
		}
	}

	class ImportCharacter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForRlm selectTxt = new ExploreWindowForRlm("Personaje.rlm");
			String path = selectTxt.exploreWindows("Importar Personaje", JFileChooser.FILES_ONLY, "Personaje.rlm");

			BufferedReader br = null;
			String jsonText = "";
			try {
				String sCurrentLine;
				br = new BufferedReader(new FileReader(path));
				while ((sCurrentLine = br.readLine()) != null) {
					jsonText += sCurrentLine;
				}
				CharacterPlayer characterPlayer = CharacterJsonManager.fromJson(jsonText);
				MessageManager.infoMessage(Controller.class.getName(), "Persoanje importado correctamente.", "RLM");
				addCharacterPlayer(characterPlayer);
			} catch (Exception exc) {
				EsherLog.errorMessage(Controller.class.getName(), exc);
				MessageManager.basicErrorMessage(Controller.class.getName(), "Error al importar el personaje!", "RLM");
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException ex) {
					// Continue
				}
			}
		}
	}

	class ExportToStandardTxt implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForTxt selectTxt = new ExploreWindowForTxt("Ficha.txt");
			String path = selectTxt.exploreWindows("Hoja en Txt", JFileChooser.FILES_ONLY, getCharacterNameFormatted()
					+ ".txt");
			new TxtSheet(selectedCharacter).exportSheet(path);
			MessageManager.infoMessage(Controller.class.getName(), "Ficha creada correctamente.", "TXT");
		}
	}

	class ExportAbbreviatedToTxt implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForTxt selectTxt = new ExploreWindowForTxt("Ficha.pdf");
			String path = selectTxt.exploreWindows("Hoja en Txt", JFileChooser.FILES_ONLY, getCharacterNameFormatted()
					+ ".txt");
			TxtSheet.exportCharacterAbbreviature(selectedCharacter, path);
			MessageManager.infoMessage(Controller.class.getName(), "Ficha creada correctamente.", "TXT");
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
			mainGui.updateCharacterInfo();
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
				RandomSplashScreen splashScreen = null;

				@Override
				public void updatedCharacter(final CharacterPlayer character) {
					// Create splashWindow.
					final Runnable splashRunner = new Runnable() {
						public void run() {
							splashScreen = new RandomSplashScreen();
							splashScreen.setVisible(true);
						}
					};

					final Runnable randomRunner = new Runnable() {
						public void run() {
							// Create random character.
							characters.remove(selectedCharacter);
							try {
								RandomCharacterPlayer randomCharacter = new RandomCharacterPlayer(character,
										randomWindow.getFinalLevel());
								randomCharacter.addFeedbackListener(new RandomFeedbackListener() {

									@Override
									public void feedBackMessage(final String message) {
										Runnable updateSplashScreen = new Runnable() {
											public void run() {
												if (splashScreen != null) {
													splashScreen.updateText(message);
												}
											}
										};

										try {
											SwingUtilities.invokeAndWait(updateSplashScreen);
										} catch (InvocationTargetException | InterruptedException e) {
											EsherLog.errorMessage(Controller.class.getName(), e);
										}
									}
								});
								randomCharacter.createRandomValues();
								selectedCharacter = randomCharacter.getCharacterPlayer();
							} catch (MagicDefinitionException | InvalidProfessionException e) {
								ShowMessage.showErrorMessage(e.getMessage(), "Error");
							}
							characters.add(selectedCharacter);
							// update GUI
							mainGui.setCharacter(selectedCharacter);
							mainGui.updateFrame();
							updateCharacterListToMenu();
							randomWindow.dispose();
							if (splashScreen != null) {
								splashScreen.dispose();
							}
						}
					};

					Thread splashThread = new Thread(splashRunner, "SplashThread");
					splashThread.start();
					Thread randomThread = new Thread(randomRunner, "RandomThread");
					randomThread.start();
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
		actionsEnables = false;
		for (CharacterPlayer character : characters) {
			CharacterMenuItem characterMenu = new CharacterMenuItem(character, selectedCharacter);
			characterMenu.addActionListener(new SelectedCharacterListener(characterMenu));
			characterListMenu.add(characterMenu);
		}
		actionsEnables = true;
	}

	class SelectedCharacterListener implements ActionListener {
		CharacterMenuItem menu;

		SelectedCharacterListener(CharacterMenuItem menu) {
			this.menu = menu;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (actionsEnables) {
				selectedCharacter = menu.getCharacter();
				mainGui.setCharacter(selectedCharacter);
				mainGui.updateFrame();
				updateCharacterListToMenu();
			}
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
