package com.github.hteph.UI;

import com.github.hteph.components.Creature;
import com.github.hteph.components.Item;

public class EquipScreen extends InventoryBasedScreen {

	// Constructor ----------------------------------------------
	  public EquipScreen(Creature player) {
		    super(player);
		  }
	//Methods --------------------------------------------------

	//Internal Methods ----------------------------------------
	  
	  protected String getVerb() {
		    return "wear or wield";
		  }

		  protected boolean isAcceptable(Item item) {
		    return item.attackValue() > 0 || item.defenseValue() > 0;
		  }

		  protected Screen use(Item item) {
		    player.equip(item);
		    return null;
		  }

	// Getters and Setters -------------------------------------
}
