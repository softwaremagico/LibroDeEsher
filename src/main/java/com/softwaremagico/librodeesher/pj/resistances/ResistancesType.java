package com.softwaremagico.librodeesher.pj.resistances;

public enum ResistancesType {
	CANALIZATION("Canalización"), ESSENCE("Esencia"), MENTALISM("Mentalismo"), PSIONIC("Psiónico"), POISON("Veneno"), DISEASE("Enfermedad"), COLD("Frío"), HOT("Calor"), FEAR("Miedo");
	
	private String tag;
	
	ResistancesType(String tag){
		this.tag = tag;
	}
	
    public static ResistancesType getResistancesType(String tag){
        for(ResistancesType type : ResistancesType.values()){
            if(type.tag.equals(tag)){
                return type;
            }
        }
        return null;
    }
}
