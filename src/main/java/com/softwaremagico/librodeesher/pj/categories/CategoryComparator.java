package com.softwaremagico.librodeesher.pj.categories;

import java.util.Comparator;

public class CategoryComparator implements Comparator<Category> {
    @Override
    public int compare(Category o1, Category o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
