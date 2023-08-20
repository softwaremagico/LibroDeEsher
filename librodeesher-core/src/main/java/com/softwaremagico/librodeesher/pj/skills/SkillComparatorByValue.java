package com.softwaremagico.librodeesher.pj.skills;

import java.util.Comparator;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

/**
 * Compare two skills by total value
 */
public class SkillComparatorByValue implements Comparator<Skill> {
	private CharacterPlayer characterPlayer;

	public SkillComparatorByValue(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}

	@Override
	public int compare(Skill skill1, Skill skill2) {
		int skillValue1 = characterPlayer.getTotalValue(skill1) - characterPlayer.getItemBonus(skill1);
		int skillValue2 = characterPlayer.getTotalValue(skill2) - characterPlayer.getItemBonus(skill2);
		if (skillValue1 == skillValue2) {
			// Common skills first.
			return (skill1.isRare() == skill2.isRare() ? 0 : (skill1.isRare() ? 1 : -1));
		}
		return skillValue1 - skillValue2;
	}
}
