package com.softwaremagico.librodeesher.pj;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.characteristics.Characteristics;
import com.softwaremagico.librodeesher.pj.culture.Culture;
import com.softwaremagico.librodeesher.pj.level.LevelUp;
import com.softwaremagico.librodeesher.pj.profession.Profession;
import com.softwaremagico.librodeesher.pj.resistances.Resistances;
import com.softwaremagico.librodeesher.pj.training.Training;

import java.util.List;

public class Character {
    private String name;
    private String surname;
    private Sex sex;
    private String history;
    
    private Characteristics characteristics;
    private List<Category> categories;
    private Culture culture;
    private List<Training> trainings;
    private Profession profession;
    private Resistances resistances;
    
    private List<LevelUp> levelUps;
    
    
    public Character(){
        characteristics = new Characteristics();
    }
    

    
}
