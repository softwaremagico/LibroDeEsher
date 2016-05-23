package com.softwaremagico.librodeesher.pj;

import org.testng.annotations.Test;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.pj.race.Race;

@Test(groups = "raceFile")
public class RaceFilesTest {
	public static final String RACE_FOLDER = "razas";

	@Test
	private void readAllRaceFiles() throws Exception {
		for (String raceName : RolemasterFolderStructure
				.getFilesAvailable(RACE_FOLDER)) {
			new Race(raceName);
		}
	}
}
