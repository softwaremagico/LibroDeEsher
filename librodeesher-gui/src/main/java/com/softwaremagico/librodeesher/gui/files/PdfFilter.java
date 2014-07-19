package com.softwaremagico.librodeesher.gui.files;

import java.io.File;

public class PdfFilter extends javax.swing.filechooser.FileFilter {

	@Override
	public boolean accept(File file) {
		String filename = file.getName();
		return file.isDirectory() || filename.endsWith(".pdf");
	}

	@Override
	public String getDescription() {
		return "Portable Document Format";
	}
}