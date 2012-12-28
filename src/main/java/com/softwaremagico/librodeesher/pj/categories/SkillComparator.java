package com.softwaremagico.librodeesher.pj.categories;

import java.util.Comparator;

import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SkillComparator implements Comparator<Skill> {
    @Override
    public int compare(Skill o1, Skill o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
