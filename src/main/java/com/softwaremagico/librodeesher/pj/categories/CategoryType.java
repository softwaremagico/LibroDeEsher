package com.softwaremagico.librodeesher.pj.categories;

public enum CategoryType {
    STANDARD("Estándar"),
    COMBINED("Combinada"),
    LIMITED("Limitada"),
    SPECIAL("Especial"),
    PPD("DPP"),
    FD("DF");
    
    private String tag;
    
    CategoryType(String tag){
        this.tag = tag;
    }
    
    public static CategoryType getCategoryType(String tag){
        for(CategoryType type : CategoryType.values()){
            if(type.tag.equals(tag)){
                return type;
            }
        }
        return null;
    }
}
