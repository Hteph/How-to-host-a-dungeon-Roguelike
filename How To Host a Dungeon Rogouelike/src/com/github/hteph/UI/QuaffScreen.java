package com.github.hteph.UI;

import com.github.hteph.components.Creature;
import com.github.hteph.components.Item;

public class QuaffScreen extends InventoryBasedScreen {

	// Constructor ----------------------------------------------
	public QuaffScreen(Creature player) {
		super(player);
	}
	//Methods --------------------------------------------------
	protected String getVerb() {
		return "quaff";
	}

	protected boolean isAcceptable(Item item) {
		return item.quaffEffect() != null;
	}

	protected Screen use(Item item) {
		player.quaff(item);
		return null;
	}
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
