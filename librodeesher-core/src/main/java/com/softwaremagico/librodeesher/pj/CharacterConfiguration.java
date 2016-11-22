package com.softwaremagico.librodeesher.pj;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_CHARACTER_CONFIGURATION")
public class CharacterConfiguration extends StorableObject {
	private static final long serialVersionUID = -4112177386907341153L;

	@Expose
	private boolean fireArmsActivated;
	@Expose
	private boolean darkSpellsAsBasic;
	@Expose
	private boolean chiPowersAllowed;
	@Expose
	private boolean otherRealmsTrainingSpells;
	@Expose
	private boolean perksCostHistoryPoints;
	@Expose
	private boolean handWrittingFont;
	@Expose
	private boolean sortPdfSkills;
	@Expose
	private boolean magicAllowed;

	@Override
	public void resetIds() {
		resetIds(this);
	}

	@Override
	public void resetComparationIds() {
		resetComparationIds(this);
	}

	public boolean isFireArmsActivated() {
		return fireArmsActivated;
	}

	public void setFireArmsActivated(boolean fireArmsActivated) {
		this.fireArmsActivated = fireArmsActivated;
	}

	public boolean isDarkSpellsAsBasic() {
		return darkSpellsAsBasic;
	}

	public void setDarkSpellsAsBasic(boolean darkSpellsAsBasic) {
		this.darkSpellsAsBasic = darkSpellsAsBasic;
	}

	public boolean isChiPowersAllowed() {
		return chiPowersAllowed;
	}

	public void setChiPowersAllowed(boolean chiPowersAllowed) {
		this.chiPowersAllowed = chiPowersAllowed;
	}

	public boolean isOtherRealmsTrainingSpells() {
		return otherRealmsTrainingSpells;
	}

	public void setOtherRealmsTrainingSpells(boolean otherRealmsTrainingSpells) {
		this.otherRealmsTrainingSpells = otherRealmsTrainingSpells;
	}

	public boolean isPerksCostHistoryPoints() {
		return perksCostHistoryPoints;
	}

	public void setPerksCostHistoryPoints(boolean perksCostHistoryPoints) {
		this.perksCostHistoryPoints = perksCostHistoryPoints;
	}

	public boolean isHandWrittingFont() {
		return handWrittingFont;
	}

	public void setHandWrittingFont(boolean handWrittingFont) {
		this.handWrittingFont = handWrittingFont;
	}

	public boolean isSortPdfSkills() {
		return sortPdfSkills;
	}

	public void setSortPdfSkills(boolean sortPdfSkills) {
		this.sortPdfSkills = sortPdfSkills;
	}

	public boolean isMagicAllowed() {
		return magicAllowed;
	}

	public void setMagicAllowed(boolean magicAllowed) {
		this.magicAllowed = magicAllowed;
	}

}
