package com.github.hteph.UI;

import com.github.hteph.components.Creature;
import com.github.hteph.components.Item;

public class DropScreen extends InventoryBasedScreen {

	// Constructor ----------------------------------------------
    public DropScreen(Creature player) {
        super(player);
    }
	//Methods --------------------------------------------------

	//Internal Methods ----------------------------------------
    protected String getVerb() {
        return "drop";
    }
    
    protected boolean isAcceptable(Item item) {
        return true;
    }
    
    protected Screen use(Item item) {
        player.drop(item);
        return null;
    }
	// Getters and Setters -------------------------------------
}
