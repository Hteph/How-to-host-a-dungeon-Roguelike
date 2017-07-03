package com.github.hteph.components;

import com.github.hteph.UI.InventoryBasedScreen;
import com.github.hteph.UI.Screen;


public class EquipScreen extends InventoryBasedScreen {

	// Constructor ----------------------------------------------
	
	  public EquipScreen(Creature player) {
		    super(player);
		  }

	//Methods --------------------------------------------------
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
		  
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
