package com.softwaremagico.librodeesher.pj;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.export.json.CharacterJsonManager;
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerDao;

@Test(groups = "NihalDragoniana")
public class NihalDragonianaTest {
	private final static String CHARACTER_FILE = "Nihal_Dragoniana_N7.rlm";
	private final static String CHARACTER_NAME = "Nihal_Dragoniana";
	private CharacterPlayer characterPlayer;
	private CharacterPlayerDao characterPlayerDao = CharacterPlayerDao.getInstance();

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

	private StringBuilder readLargerTextFile(String resourceName) throws IOException {
		File file = new File(getClass().getClassLoader().getResource(resourceName).getFile());
		StringBuilder text = new StringBuilder();
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				text.append(scanner.nextLine());
			}
		}
		return text;
	}
}
