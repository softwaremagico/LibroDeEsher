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
			return 0;
		}
		return skillRanks1 - skillRanks2;
    }
}
