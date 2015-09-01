package com.softwaremagico.librodeesher.pj;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
	
	private final static String CODIFICATION = "UTF-8";

	@Test
	public void importFromJson() throws IOException {
		StringBuilder text = readLargerTextFile(CHARACTER_FILE);
		String character = text.toString();
		CharacterPlayer characterPlayer = CharacterJsonManager.fromJson(character);
		Assert.assertNotNull(characterPlayer);
		Assert.assertEquals(CHARACTER_NAME, characterPlayer.getName());


		String originalTxtSheet = readLargerTextFile(TXT_FILE).toString();		
		PrintWriter out1 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator
				+ "originalMorticia.txt", CODIFICATION);
		out1.println(originalTxtSheet);
		out1.close();
		
		String createdCharacterSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		PrintWriter out2 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator
				+ "importedMorticia.txt", CODIFICATION);
		out2.println(createdCharacterSheet);
		out2.close();

		Assert.assertEquals(originalTxtSheet, createdCharacterSheet);
	}

	private StringBuilder readLargerTextFile(String resourceName) throws IOException {
		File file = new File(getClass().getClassLoader().getResource(resourceName).getFile());
		StringBuilder text = new StringBuilder();
		try (Scanner scanner = new Scanner(file, CODIFICATION)) {
			while (scanner.hasNextLine()) {
				text.append(scanner.nextLine()).append("\n");
			}
		}
		return text;
	}
}
