package com.softwaremagico.librodeesher.basics;

public class Experience {

	public static int getMinExperienceForLevel(int level) {
		if (level < 5) {
			return level * 10000;
		}
		if (level < 10) {
			return (level - 5) * 20000 + 50000;
		}
		if (level < 15) {
			return (level - 10) * 30000 + 150000;
		}
		if (level < 20) {
			return (level - 15) * 40000 + 300000;
		}
		return (level - 20) * 50000 + 500000;
	}
}
