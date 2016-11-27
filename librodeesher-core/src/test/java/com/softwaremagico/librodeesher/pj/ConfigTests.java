package com.softwaremagico.librodeesher.pj;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.config.Config;

@Test(groups = "config")
public class ConfigTests extends BasicTest {

	@Test
	public void checkCharacterConfig() {
		CharacterPlayer characterPlayer = new CharacterPlayer();
		characterPlayer.setFirearmsAllowed(true);
		Assert.assertTrue(characterPlayer.isFirearmsAllowed());
	}

	@Test
	public void checkMultipleCharacterConfig() {
		CharacterPlayer characterPlayer1 = new CharacterPlayer();
		characterPlayer1.setFirearmsAllowed(true);
		Assert.assertTrue(characterPlayer1.isFirearmsAllowed());

		CharacterPlayer characterPlayer2 = new CharacterPlayer();
		characterPlayer2.setFirearmsAllowed(false);
		Assert.assertTrue(characterPlayer1.isFirearmsAllowed());
		Assert.assertFalse(characterPlayer2.isFirearmsAllowed());
	}

	@Test
	public void checkDefaultConfigChanges() {
		Config.setFireArmsActivated(true);
		Config.setOtherRealmTrainingSpells(true);
		Config.setChiPowersAllowed(true);

		// Character has default config set.
		CharacterPlayer characterPlayer1 = new CharacterPlayer();
		Assert.assertTrue(characterPlayer1.isFirearmsAllowed());
		Assert.assertTrue(characterPlayer1.isOtherRealmTrainingSpellsAllowed());
		Assert.assertTrue(characterPlayer1.isChiPowersAllowed());

		Config.setFireArmsActivated(false);
		Config.setOtherRealmTrainingSpells(false);
		Config.setChiPowersAllowed(false);

		// Character has not default config set.
		CharacterPlayer characterPlayer2 = new CharacterPlayer();
		Assert.assertFalse(characterPlayer2.isFirearmsAllowed());
		Assert.assertFalse(characterPlayer2.isOtherRealmTrainingSpellsAllowed());
		Assert.assertFalse(characterPlayer2.isChiPowersAllowed());

		// Previous configuration not changed.
		Assert.assertTrue(characterPlayer1.isFirearmsAllowed());
		Assert.assertTrue(characterPlayer1.isOtherRealmTrainingSpellsAllowed());
		Assert.assertTrue(characterPlayer1.isChiPowersAllowed());

	}

}
