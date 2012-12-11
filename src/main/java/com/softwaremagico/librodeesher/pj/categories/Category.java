package com.softwaremagico.librodeesher.pj.categories;

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.pj.characteristics.Characteristic;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public abstract class Category extends SimpleCategory{
	protected String abbreviature;
	protected CategoryType type;
	public List<Characteristic> characteristics;

	public Category(String name, String abbreviature, CategoryType type) {
		super(name);
		this.abbreviature = abbreviature;
		this.type = type;
		skills = new ArrayList<>();
		characteristics = new ArrayList<>();
	}

	public abstract void addSkills(String skills);

	public List<Skill> getSkills() {
		return skills;
	}

	public String getAbbreviature() {
		return abbreviature;
	}
}
