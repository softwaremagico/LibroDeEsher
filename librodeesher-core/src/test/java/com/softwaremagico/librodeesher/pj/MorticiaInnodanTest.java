package com.softwaremagico.librodeesher.pj;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.export.json.CharacterJsonManager;
import com.softwaremagico.librodeesher.pj.export.txt.TxtSheet;

@Test(groups = "MorticiaInnodanTest")
public class MorticiaInnodanTest {
	private final static String CHARACTER_FILE = "Morticia_Innodan_N10_Ilourianos_Elementalista.rlm";
	private final static String TXT_FILE = "Morticia_Innodan_N10_Ilourianos_Elementalista.txt";
	private final static String CHARACTER_NAME = "Morticia Innodan";

	@Test
	public void importFromJson() throws IOException {
		StringBuilder text = readLargerTextFile(CHARACTER_FILE);
		String character = text.toString();
		CharacterPlayer characterPlayer = CharacterJsonManager.fromJson(character);
		Assert.assertNotNull(characterPlayer);
		Assert.assertEquals(CHARACTER_NAME, characterPlayer.getName());

		String orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		String txtResult = readLargerTextFile(TXT_FILE).toString();

		Assert.assertEquals(orginalSheet, txtResult);
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
