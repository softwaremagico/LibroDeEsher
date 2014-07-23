package com.softwaremagico.librodeesher.pj.skills;

import java.util.Comparator;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class SkillComparatorByRanks implements Comparator<Skill> {
	private CharacterPlayer characterPlayer;
	
	public SkillComparatorByRanks(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}
	
    @Override
    public int compare(Skill skill1, Skill skill2) {
    	int skillRanks1 = characterPlayer.getTotalRanks(skill1);
		int skillRanks2 = characterPlayer.getTotalRanks(skill2);
		if (skillRanks1 == skillRanks2) {
			// Return -1, 0 or 1 randomly.
			return (int) (Math.random() * 3 + 1) - 2;
		}
		return skillRanks1 - skillRanks2;
    }
}
