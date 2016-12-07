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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;

import com.itextpdf.text.DocumentException;
import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.librodeesher.gui.background.BackgroundWindow;
import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicsWindow;
import com.softwaremagico.librodeesher.gui.components.CharacterMenuItem;
import com.softwaremagico.librodeesher.gui.components.SkillWindow;
import com.softwaremagico.librodeesher.gui.culture.CultureWindow;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowForPdf;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowForRlm;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowForRlmLvl;
import com.softwaremagico.librodeesher.gui.files.ExploreWindowForTxt;
import com.softwaremagico.librodeesher.gui.history.WriteHistoryWindow;
import com.softwaremagico.librodeesher.gui.perk.PerkWindow;
import com.softwaremagico.librodeesher.gui.persistence.LoadCharacterListener;
import com.softwaremagico.librodeesher.gui.persistence.LoadCharacterPlayerWindow;
import com.softwaremagico.librodeesher.gui.persistence.RemoveCharacterListener;
import com.softwaremagico.librodeesher.gui.profession.ProfessionWindow;
import com.softwaremagico.librodeesher.gui.random.RandomCharacterUpdatedListener;
import com.softwaremagico.librodeesher.gui.random.RandomSplashScreen;
import com.softwaremagico.librodeesher.gui.random.RandomWindow;
import com.softwaremagico.librodeesher.gui.skills.SkillRanksWindow;
import com.softwaremagico.librodeesher.gui.skills.insert.InsertRanksWindow;
import com.softwaremagico.librodeesher.gui.training.TrainingWindow;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.export.json.CharacterJsonManager;
import com.softwaremagico.librodeesher.pj.export.json.LevelJsonManager;
import com.softwaremagico.librodeesher.pj.export.json.exceptions.InvalidCharacterException;
import com.softwaremagico.librodeesher.pj.export.json.exceptions.InvalidLevelException;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfCombinedSheet1Column;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfCombinedSheet2Columns;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfStandardSheet;
import com.softwaremagico.librodeesher.pj.export.txt.TxtSheet;
import com.softwaremagico.librodeesher.pj.level.LevelUp;
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
	private WriteHistoryWindow historyWindow;
	private BackgroundWindow backgroundWindow;
	private PerkWindow perksWindow;
	private ProfessionWindow professionWindow;
	private TrainingWindow trainingWindow;
	private RandomWindow randomWindow;
	private LoadCharacterPlayerWindow loadWindow;
	//private InsertMagicItemWindow insertMagicItemWindow;
	private InsertRanksWindow insertRankWindows;

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
			characterPlayer.setDirty(false);
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
		mainGui.getMainMenu().addBackgroundWindowListener(new BackgroundWindowsListener());
		mainGui.getMainMenu().addHistoryWindowListener(new HistoryWindowsListener());
		mainGui.getMainMenu().addPerksWindowListener(new PerksWindowsListener());
		mainGui.getMainMenu().addProfessionWindowListener(new ProfessionWindowsListener());
		mainGui.getMainMenu().addTrainingWindowListener(new TrainingWindowsListener());
		mainGui.getMainMenu().addLevelUpActionListener(new IncreaseLevelActionListener());
		mainGui.getMainMenu().addStandardSheetPdfActionListener(new ExportToStandardPdf());
		mainGui.getMainMenu().addCombinedSheetPdf1ColumnActionListener(new ExportToCombinedPdf1Column());
		mainGui.getMainMenu().addCombinedSheetPdf2ColumnsActionListener(new ExportToCombinedPdf2Columns());
		mainGui.getMainMenu().addStandardTxtActionListener(new ExportToStandardTxt());
		mainGui.getMainMenu().addAbbreviatedTxtActionListener(new ExportAbbreviatedToTxt());
		mainGui.getMainMenu().addSaveActionListener(new SaveCharacterPlayer());
		mainGui.getMainMenu().addLoadActionListener(new LoadCharacterPlayer());
		mainGui.getMainMenu().addExportCharacterListener(new ExportCharacter());
		mainGui.getMainMenu().addImportCharacterListener(new ImportCharacter());
		mainGui.getMainMenu().addExportLevelListener(new ExportLevel());
		mainGui.getMainMenu().addImportLevelListener(new ImportLevel());
		mainGui.getMainMenu().addInsertMagicItemListener(new InsertMagicObject());
		mainGui.getMainMenu().addEnableDebugItemListener(new EnableDebugListener());
		mainGui.getMainMenu().addRanksItemListener(new AddRankListener());
	}

	class AddRankListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				insertRankWindows.dispose();
			} catch (NullPointerException npe) {
			}
			insertRankWindows = new InsertRanksWindow(selectedCharacter);
			insertRankWindows.setVisible(true);
		}
	}

	class EnableDebugListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Config.setEnableDebug(mainGui.getMainMenu().isDebugEnabled());
		}
	}

	class InsertMagicObject implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
//			try {
//				insertMagicItemWindow.dispose();
//			} catch (NullPointerException npe) {
//			}
//			insertMagicItemWindow = new InsertMagicItemWindow(selectedCharacter);
//			insertMagicItemWindow.setVisible(true);
		}
	}

	class LoadCharacterPlayer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				loadWindow.dispose();
			} catch (NullPointerException npe) {
			}
			loadWindow = new LoadCharacterPlayerWindow();
			loadWindow.addLoadCharacterListener(new LoadCharacterListener() {

				@Override
				public void addCharacter(CharacterPlayer characterPlayer) {
					addCharacterPlayer(characterPlayer);
					MessageManager.infoMessage(Controller.class.getName(), "Personaje cargado con éxito!", "Cargar");
				}
			});
			loadWindow.addRemoveCharacterListener(new RemoveCharacterListener() {

				@Override
				public void removeCharacter(CharacterPlayer characterPlayer) {
					if (characterPlayer != null) {
						if (!characterPlayer.getId().equals(selectedCharacter.getId())) {
							CharacterPlayerDao.getInstance().makeTransient(characterPlayer);
							MessageManager.infoMessage(this.getClass().getName(), "El personaje ha sido borrado con éxito.", "Borrar");
						} else {
							MessageManager
									.basicErrorMessage(this.getClass().getName(), "No se puede eliminar el personaje actualmente seleccionado.", "Borrar");
						}
					}
				}
			});
			loadWindow.setVisible(true);
		}
	}

	class SaveCharacterPlayer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				// Imported character can have the same id of an existing
				// character in database. Not allowed, must override.
				if (selectedCharacter.getId() == null && CharacterPlayerDao.getInstance().exists(selectedCharacter.getComparationId())) {
					if (!MessageManager.questionMessage(
							"Se va a sobreescrbir un personaje que ya ha sido guardado anteriormente en la base de datos. ¿Estas seguro de continuar?",
							"Salvar")) {
						return;
					}
				}
				CharacterPlayer characterPersisted = CharacterPlayerDao.getInstance().makePersistent(selectedCharacter);
				if (characterPersisted != null) {
					MessageManager.infoMessage(Controller.class.getName(), "Personaje guardado con éxito!", "Salvar");
				} else {
					MessageManager
							.basicErrorMessage(
									Controller.class.getName(),
									"Error almacenando el personaje, parece que algo ha ido mal.\n Comprueba el log del programa para obtener más información al respecto.",
									"Guardar");
				}
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
			ExploreWindowForRlmLvl selectJson = new ExploreWindowForRlmLvl("PersonajeLevel.rlmlvl");
			String path = selectJson.exploreWindows("Exportar Nivel", JFileChooser.FILES_ONLY, getCharacterNameFormatted() + ".rlmlvl");
			File file = new File(path);
			boolean create = true;
			if (file.exists() && !file.isDirectory()) {
				if (!MessageManager.questionMessage("Ya existe un fichero con ese nombre. ¿Desea sobreescribirlo?", "Save")) {
					create = false;
				}
			}
			if (create && path != null && path.length() > 0) {
				String jsonText = LevelJsonManager.toJson(selectedCharacter);
				// store in a file.
				try {
					File fileDir = new File(path);
					Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));

					out.append(jsonText);
					out.flush();
					out.close();
					MessageManager.infoMessage(Controller.class.getName(), "Nivel exportado correctamente.", "RLMLVL");
					return;
				} catch (IOException e1) {
				}
				MessageManager.basicErrorMessage(Controller.class.getName(), "Error al exportar el nivel!", "RLMLVL");
			}
		}
	}

	class ImportLevel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForRlmLvl selectTxt = new ExploreWindowForRlmLvl("Personaje.rlmlvl");
			String path = selectTxt.exploreWindows("Importar Nivel", JFileChooser.FILES_ONLY,
					getCharacterNameFormatted().substring(0, getCharacterNameFormatted().length() - 2) + (selectedCharacter.getCurrentLevelNumber() + 1)
							+ ".rlmlvl");

			BufferedReader bufferReader = null;
			String jsonText = "";
			try {
				// Read file.
				bufferReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
				String str;

				while ((str = bufferReader.readLine()) != null) {
					jsonText += str;
				}
				bufferReader.close();

				// Convert from json.
				try {
					LevelUp level = LevelJsonManager.fromJson(selectedCharacter, jsonText);
					selectedCharacter.importLevel(level);
					MessageManager.infoMessage(Controller.class.getName(), "Nivel importado correctamente.", "RLMLVL");
				} catch (InvalidCharacterException ice) {
					MessageManager.basicErrorMessage(Controller.class.getName(),
							"Nivel invalido para importar. El nivel está definido para un personaje distinto al actualmente seleccionado.'", "RLMLVL");
				} catch (InvalidLevelException ile) {
					MessageManager.basicErrorMessage(Controller.class.getName(),
							"Nivel inválido para importar. Probablemente sea un fichero que contenga un nivel antiguo o erróneo.", "RLMLVL");
				}
			} catch (Exception exc) {
				EsherLog.errorMessage(Controller.class.getName(), exc);
				MessageManager.basicErrorMessage(Controller.class.getName(), "Error al importar el nivel!", "RLMLVL");
			} finally {
				try {
					if (bufferReader != null)
						bufferReader.close();
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
			String path = selectJson.exploreWindows("Exportar Personaje", JFileChooser.FILES_ONLY, getCharacterNameFormatted() + ".rlm");
			File file = new File(path);
			boolean create = true;
			if (file.exists() && !file.isDirectory()) {
				if (!MessageManager.questionMessage("Ya existe un fichero con ese nombre. ¿Desea sobreescribirlo?", "Save")) {
					create = false;
				}
			}
			if (create && path != null && path.length() > 0) {
				String jsonText = CharacterJsonManager.toJson(selectedCharacter);
				// store in a file.
				File fileDir = new File(path);

				try {
					Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));

					out.append(jsonText);
					out.flush();
					out.close();

					MessageManager.infoMessage(Controller.class.getName(), "Personaje exportado correctamente.", "RLM");
				} catch (IOException e1) {
					MessageManager.basicErrorMessage(Controller.class.getName(), "Error al exportar el personaje!", "RLM");
				}

			}
		}
	}

	class ImportCharacter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForRlm selectTxt = new ExploreWindowForRlm("Personaje.rlm");
			String path = selectTxt.exploreWindows("Importar Personaje", JFileChooser.FILES_ONLY, "Personaje.rlm");

			String jsonText = "";
			BufferedReader bufferReader = null;
			try {
				bufferReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
				String str;

				while ((str = bufferReader.readLine()) != null) {
					jsonText += str;
				}
				bufferReader.close();
				CharacterPlayer characterPlayer = CharacterJsonManager.fromJson(jsonText);
				MessageManager.infoMessage(Controller.class.getName(), "Personaje importado correctamente.", "RLM");
				addCharacterPlayer(characterPlayer);
			} catch (Exception exc) {
				EsherLog.errorMessage(Controller.class.getName(), exc);
				MessageManager.basicErrorMessage(Controller.class.getName(), "Error al importar el personaje!", "RLM");
			} finally {
				try {
					if (bufferReader != null)
						bufferReader.close();
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
			String path = selectTxt.exploreWindows("Hoja en Txt", JFileChooser.FILES_ONLY, getCharacterNameFormatted() + ".txt");
			File file = new File(path);
			boolean create = true;
			if (file.exists() && !file.isDirectory()) {
				if (!MessageManager.questionMessage("Ya existe un fichero con ese nombre. ¿Desea sobreescribirlo?", "Save")) {
					create = false;
				}
			}
			if (create && path != null && path.length() > 0) {
				new TxtSheet(selectedCharacter).exportSheet(path);
				MessageManager.infoMessage(Controller.class.getName(), "Ficha creada correctamente.", "TXT");
			}
		}
	}

	class ExportAbbreviatedToTxt implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForTxt selectTxt = new ExploreWindowForTxt("Ficha.pdf");
			String path = selectTxt.exploreWindows("Hoja en Txt", JFileChooser.FILES_ONLY, getCharacterNameFormatted() + ".txt");
			File file = new File(path);
			boolean create = true;
			if (file.exists() && !file.isDirectory()) {
				if (!MessageManager.questionMessage("Ya existe un fichero con ese nombre. ¿Desea sobreescribirlo?", "Save")) {
					create = false;
				}
			}
			if (create && path != null && path.length() > 0) {
				TxtSheet.exportCharacterAbbreviature(selectedCharacter, path);
				MessageManager.infoMessage(Controller.class.getName(), "Ficha creada correctamente.", "TXT");
			}
		}
	}

	class ExportToStandardPdf implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			final ExploreWindowForPdf selectPdf = new ExploreWindowForPdf("Hoja en PDF");
			String path = selectPdf.exploreWindows("Hoja en PDF", JFileChooser.FILES_ONLY, getCharacterNameFormatted() + ".pdf");
			File file = new File(path);
			boolean create = true;
			if (file.exists() && !file.isDirectory()) {
				if (!MessageManager.questionMessage("Ya existe un fichero con ese nombre. ¿Desea sobreescribirlo?", "Save")) {
					create = false;
				}
			}
			try {
				if (create && path != null && path.length() > 0) {
					new PdfStandardSheet(selectedCharacter, path, selectedCharacter.isSortPdfSkills());
					MessageManager.infoMessage(Controller.class.getName(), "Ficha creada correctamente.", "PDF");
				}
			} catch (DocumentException | IOException ex) {
				MessageManager.basicErrorMessage(Controller.class.getName(), "Error al crear el PDF.", "PDF");
				EsherLog.errorMessage(Controller.class.getName(), ex);
			}
		}
	}

	class ExportToCombinedPdf1Column implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForPdf selectPdf = new ExploreWindowForPdf("RMFComb.pdf");
			String path = selectPdf.exploreWindows("Hoja en PDF", JFileChooser.FILES_ONLY, getCharacterNameFormatted() + ".pdf");
			try {
				File file = new File(path);
				boolean create = true;
				if (file.exists() && !file.isDirectory()) {
					if (!MessageManager.questionMessage("Ya existe un fichero con ese nombre. ¿Desea sobreescribirlo?", "Save")) {
						create = false;
					}
				}
				if (create && path != null && path.length() > 0) {
					new PdfCombinedSheet1Column(selectedCharacter, path);
					MessageManager.infoMessage(Controller.class.getName(), "Ficha creada correctamente.", "PDF");
				}
			} catch (DocumentException | IOException ex) {
				MessageManager.basicErrorMessage(Controller.class.getName(), "Error al crear el PDF.", "PDF");
				EsherLog.errorMessage(Controller.class.getName(), ex);
			}
		}
	}

	class ExportToCombinedPdf2Columns implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ExploreWindowForPdf selectPdf = new ExploreWindowForPdf("RMFComb.pdf");
			String path = selectPdf.exploreWindows("Hoja en PDF", JFileChooser.FILES_ONLY, getCharacterNameFormatted() + ".pdf");
			try {
				File file = new File(path);
				boolean create = true;
				if (file.exists() && !file.isDirectory()) {
					if (!MessageManager.questionMessage("Ya existe un fichero con ese nombre. ¿Desea sobreescribirlo?", "Save")) {
						create = false;
					}
				}
				if (create && path != null && path.length() > 0) {
					new PdfCombinedSheet2Columns(selectedCharacter, path);
					MessageManager.infoMessage(Controller.class.getName(), "Ficha creada correctamente.", "PDF");
				}
			} catch (DocumentException | IOException ex) {
				MessageManager.basicErrorMessage(Controller.class.getName(), "Error al crear el PDF.", "PDF");
				EsherLog.errorMessage(Controller.class.getName(), ex);
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
			if (ShowMessage.showQuestionMessage(null, "Cualquier cambio no guardado se perderá. ¿Quieres continuar con la acción?", "Cerrar")) {
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
	}

	class RandomNameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			selectedCharacter.setName(selectedCharacter.getRace().getRandonName(selectedCharacter.getSex()));
			mainGui.updateCharacterInfo();
			updateCharacterListToMenu();
		}
	}

	private boolean isEverythingSelectedCorrectly() {
		boolean value = true;
		value &= selectedCharacter.getRace() != null;
		value &= selectedCharacter.getProfession() != null;
		value &= selectedCharacter.getCulture() != null;
		return value;
	}

	class RandomCharacterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				randomWindow.dispose();
			} catch (NullPointerException npe) {
			}
			if (isEverythingSelectedCorrectly()) {
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
								// Check if everything is correct.
								// Create random character.
								characters.remove(selectedCharacter);
								try {
									RandomCharacterPlayer randomCharacter = new RandomCharacterPlayer(character, randomWindow.getFinalLevel());
									randomCharacter.setSuggestedTrainings(randomWindow.getSuggestedTrainingList());
									for (String categoryName : randomWindow.getSuggestedCategoriesRanks().keySet()) {
										randomCharacter.setSuggestedCategoryRanks(categoryName, randomWindow.getSuggestedCategoriesRanks().get(categoryName));
									}
									for (String skillName : randomWindow.getSuggestedSkillsRanks().keySet()) {
										randomCharacter.setSuggestedCategoryRanks(skillName, randomWindow.getSuggestedSkillsRanks().get(skillName));
									}
									randomCharacter.setSelectPerks(randomWindow.isPerksEnabled());
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
			skillWindow = new SkillRanksWindow(selectedCharacter);
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
			historyWindow = new WriteHistoryWindow(selectedCharacter);
			historyWindow.setVisible(true);
		}
	}

	class BackgroundWindowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				backgroundWindow.dispose();
			} catch (NullPointerException npe) {
			}
			backgroundWindow = new BackgroundWindow(selectedCharacter);
			backgroundWindow.setVisible(true);
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
			MessageManager.infoMessage(this.getClass().getName(), "Ahora el personaje está en nivel " + selectedCharacter.getCurrentLevelNumber(),
					"Subir de nivel");
		}
	}

	private String getCharacterNameFormatted() {
		return selectedCharacter.getName().trim().replace(" ", "_") + "_N" + selectedCharacter.getCurrentLevelNumber()
				+ (selectedCharacter.getRace() != null ? "_" + selectedCharacter.getRace().getName() : "")
				+ (selectedCharacter.getProfession() != null ? "_" + selectedCharacter.getProfession().getName() : "");
	}
}
