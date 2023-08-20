package com.softwaremagico.librodeesher.pj.race;

public enum RaceSize {

	XXS("muy pequeño", "MP"),

	XS("pequeño", "P"),
	
	S("bajo", "M"),

	M("mediano", "M"),
	
	L("alto", "M"),

	XL("grande", "G"),

	XXL("super grande", "EN");

	private String code;

	private String abbreviature;

	RaceSize(String code, String abbreviature) {
		this.code = code;
		this.abbreviature = abbreviature;
	}

	public static RaceSize getRaceSize(String code) {
		for (RaceSize raceSize : RaceSize.values()) {
			if (raceSize.code.equals(code.toLowerCase())) {
				return raceSize;
			}
		}

		if (code.toLowerCase().equals("normal")
				|| code.toLowerCase().equals("medio")) {
			return RaceSize.M;
		}

		if (code.toLowerCase().equals("enorme")
				|| code.toLowerCase().equals("muy grande")) {
			return RaceSize.XL;
		}

		return RaceSize.M;
	}
	
	public String getAbbreviature(){
		return abbreviature;
	}
}
