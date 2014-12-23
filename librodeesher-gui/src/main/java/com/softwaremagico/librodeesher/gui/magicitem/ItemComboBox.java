package com.softwaremagico.librodeesher.gui.magicitem;

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.elements.BaseComboBox;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;

public class ItemComboBox extends BaseComboBox<MagicObject> {
	private static final long serialVersionUID = 4290328543865348273L;
	private List<ItemChangedListener> itemListeners;

	public ItemComboBox() {
		itemListeners = new ArrayList<>();
	}

	@Override
	public void doAction() {
		for (ItemChangedListener listener : itemListeners) {
			listener.ItemChanged((MagicObject) this.getSelectedItem());
		}
	}

	public void addItemChangedListener(ItemChangedListener listener) {
		itemListeners.add(listener);
	}

	public void removeItemChangedListener(ItemChangedListener listener) {
		itemListeners.remove(listener);
	}

}
