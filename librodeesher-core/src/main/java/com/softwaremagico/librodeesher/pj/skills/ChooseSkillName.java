package com.softwaremagico.librodeesher.pj.skills;

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.basics.ChooseGroup;
import com.softwaremagico.librodeesher.basics.ChooseType;

public class ChooseSkillName extends ChooseGroup<String> {

	public ChooseSkillName(int chooseNumber, List<String> skillList, ChooseType chooseType) {
		super(chooseNumber, skillList, chooseType);
	}

	@Override
	public List<String> getOptionsAsString() {
		List<String> nameList = new ArrayList<>();
		for (String skill : getOptionsGroup()) {
			nameList.add(skill);
		}
		return nameList;
	}
}
