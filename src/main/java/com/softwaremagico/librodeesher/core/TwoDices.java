package com.softwaremagico.librodeesher.core;

public class TwoDices {
	private Integer firstDiceResult;
	private Integer secondDiceResult;

	public TwoDices() {
		initialize(10);
	}

	public TwoDices(Integer faces) {
		initialize(faces);
	}

	private void initialize(Integer faces) {
		firstDiceResult = Dice.getRoll(faces);
		secondDiceResult = Dice.getRoll(faces);
	}
	
	public Integer getFirstDice(){
		return firstDiceResult;
	}

	public Integer getSecondDice(){
		return secondDiceResult;
	}
	
}
