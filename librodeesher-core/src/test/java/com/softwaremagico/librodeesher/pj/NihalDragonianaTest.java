package com.softwaremagico.librodeesher.pj;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.export.json.CharacterJsonManager;
import com.softwaremagico.librodeesher.pj.export.txt.TxtSheet;
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerDao;

@Test(groups = "NihalDragoniana")
public class NihalDragonianaTest {
	private final static String CHARACTER_FILE = "Nihal_Dragoniana_N7.rlm";
	private final static String CHARACTER_NAME = "Nihal_Dragoniana";
	private CharacterPlayer characterPlayer;
	private CharacterPlayer characterPlayer2;
	private CharacterPlayerDao characterPlayerDao = CharacterPlayerDao.getInstance();
	
	private final static String CODIFICATION = "UTF-8";

	public void importFromJson() throws IOException {
		StringBuilder text = readLargerTextFile(CHARACTER_FILE);
		String character = text.toString();
		characterPlayer = CharacterJsonManager.fromJson(character);
		Assert.assertNotNull(characterPlayer);
		Assert.assertEquals(CHARACTER_NAME, characterPlayer.getName());
	}

	@Test(dependsOnMethods = "importFromJson")
	public void saveinDatabase() throws Exception {
		characterPlayerDao.makePersistent(characterPlayer);
		Assert.assertNotNull(characterPlayer.getId());
	}

	@Test(dependsOnMethods = "importFromJson", enabled = false)
	public void copyCharacter() throws Exception {
		CharacterPlayer characterPlayer2 = (CharacterPlayer) characterPlayer.copyData();

		// Compared generated sheet to be sure that has the same information.
		String orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		String copiedSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer2);

		Assert.assertEquals(copiedSheet, orginalSheet);
	}

	@Test(dependsOnMethods = "saveinDatabase")
	public void loadFromDatabase() {
		characterPlayer2 = characterPlayerDao.read(characterPlayer.getId());
		Assert.assertNotNull(characterPlayer2);
	}

	@Test(dependsOnMethods = "loadFromDatabase")
	public void saveAgainAsNew() throws Exception {
		int prevCharacter = characterPlayerDao.getRowCount();
		characterPlayerDao.makePersistent(characterPlayer2);
		Assert.assertEquals(prevCharacter, characterPlayerDao.getRowCount());
	}

	@Test(dependsOnMethods = {"saveAgainAsNew"})
	@After
	public void removeAll() throws Exception {
		List<CharacterPlayer> characterPlayers = characterPlayerDao.getAll();
		for (CharacterPlayer character : characterPlayers) {
			characterPlayerDao.makeTransient(character);
		}
		Assert.assertEquals(0, characterPlayerDao.getRowCount());
	}

	private StringBuilder readLargerTextFile(String resourceName) throws IOException {
		File file = new File(getClass().getClassLoader().getResource(resourceName).getFile());
		StringBuilder text = new StringBuilder();
		try (Scanner scanner = new Scanner(file, CODIFICATION)) {
			while (scanner.hasNextLine()) {
				text.append(scanner.nextLine());
			}
		}
		return text;
	}
}
