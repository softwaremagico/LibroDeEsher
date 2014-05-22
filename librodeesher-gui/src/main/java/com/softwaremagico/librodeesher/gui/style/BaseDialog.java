package com.softwaremagico.librodeesher.gui.style;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class BaseDialog extends JDialog {
	private static final String DIALOG_TITLE = "El Libro de Esher";
	private static final long serialVersionUID = -1726966977605568691L;

	public BaseDialog(BaseFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		setTitle(DIALOG_TITLE);
		setIconImage(new ImageIcon(this.getClass().getResource("/librodeesher.png")).getImage());
	}

	public void center() {
		setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2
				- (int) (this.getWidth() / 2), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
				/ 2 - (int) (this.getHeight() / 2));
	}

}
