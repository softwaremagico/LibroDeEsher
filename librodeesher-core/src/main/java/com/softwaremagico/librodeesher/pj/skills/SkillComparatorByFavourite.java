package com.softwaremagico.librodeesher.pj.skills;

import java.util.Comparator;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

/**
 * Compare two skills by total value
 */
public class SkillComparatorByFavourite implements Comparator<Skill> {
	private CharacterPlayer characterPlayer;

	public SkillComparatorByFavourite(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}

	@Override
	public int compare(Skill skill1, Skill skill2) {
		// First user selected skills agains recommended skills.
		if (characterPlayer.getCurrentLevel().getFavouriteSkills().contains(skill1) && !characterPlayer.getCurrentLevel().getFavouriteSkills().contains(skill1)) {
			return -1;
		}
		if (!characterPlayer.getCurrentLevel().getFavouriteSkills().contains(skill1) && characterPlayer.getCurrentLevel().getFavouriteSkills().contains(skill1)) {
			return 1;
		}
		int skillValue1 = characterPlayer.getTotalValue(skill1) - characterPlayer.getItemBonus(skill1);
		int skillValue2 = characterPlayer.getTotalValue(skill2) - characterPlayer.getItemBonus(skill2);
		if (skillValue1 == skillValue2) {
			return 0;
		}
		return skillValue1 - skillValue2;
	}
}
