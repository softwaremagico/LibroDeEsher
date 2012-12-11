package com.softwaremagico.librodeesher.pj.skills;

public abstract class Skill {
    private String name;
    
    
    public Skill(String name){
        this.name = name;
    }
    
    public String getName(){
    	return name;
    }
    
}
