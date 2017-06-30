package com.github.hteph.UI;

import com.github.hteph.components.Creature;
import com.github.hteph.components.Item;

public class EatScreen extends InventoryBasedScreen {

	// Constructor ----------------------------------------------
    public EatScreen(Creature player) {
        super(player);
    }
	//Methods --------------------------------------------------

	//Internal Methods ----------------------------------------
    protected String getVerb() {
        return "eat";
    }

    protected boolean isAcceptable(Item item) {
        return item.foodValue() != 0;
    }

    protected Screen use(Item item) {
        player.eat(item);
        return null;
    }
	// Getters and Setters -------------------------------------
}
