package com.softwaremagico.utils;

import java.util.ArrayList;

public class ListWithoutCase extends ArrayList<String> {
	private static final long serialVersionUID = -5031528424888944839L;

	@Override
	public boolean contains(Object o) {
		String paramStr = (String) o;
		for (String s : this) {
			if (paramStr.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
}
