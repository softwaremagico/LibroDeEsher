package com.softwaremagico.librodeesher.gui.random;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JSlider;

import com.softwaremagico.librodeesher.gui.elements.BaseComboBox;
import com.softwaremagico.librodeesher.gui.elements.CategoryComboBox;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;

public class RandomWindow extends BaseFrame {
	private static final long serialVersionUID = -2265764014622959400L;
	private JSlider generalizationSpecializationSlider;

	@Override
	public void updateFrame() {
		// setResizable(false);
		setElements();

	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		generalizationSpecializationSlider = createSpecializedSlider();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);

	}

	private JSlider createSpecializedSlider() {
		final int GENERALIZED = -3;
		final int SPECIALIZED = 3;
		final int INIT = 0;

		JSlider generalizationSpecializationSlider = new JSlider(
				JSlider.HORIZONTAL, GENERALIZED, SPECIALIZED, INIT);
		// generalizationSpecializationSlider.addChangeListener(this);

		// Turn on labels at major tick marks.
		generalizationSpecializationSlider.setMajorTickSpacing(1);
		generalizationSpecializationSlider.setMinorTickSpacing(1);
		generalizationSpecializationSlider.setPaintTicks(true);
		generalizationSpecializationSlider.setPaintLabels(false);
		return generalizationSpecializationSlider;
	}

	private CategoryComboBox createCategoryComboBox() {
		CategoryComboBox categoryComboBox = new CategoryComboBox();
		return categoryComboBox;
	}
	
	
}
