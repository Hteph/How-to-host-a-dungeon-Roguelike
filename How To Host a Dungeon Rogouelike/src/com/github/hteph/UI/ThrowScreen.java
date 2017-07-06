package com.github.hteph.UI;

import com.github.hteph.components.Creature;
import com.github.hteph.components.Item;

public class ThrowScreen extends InventoryBasedScreen {
    private int sx;
    private int sy;

	// Constructor ----------------------------------------------
    public ThrowScreen(Creature player, int sx, int sy) {
        super(player);
        this.sx = sx;
        this.sy = sy;
    }
	//Methods --------------------------------------------------
    protected String getVerb() {
        return "throw";
    }

    protected boolean isAcceptable(Item item) {
        return true;
    }

    protected Screen use(Item item) {
        return new ThrowAtScreen(player, sx, sy, item);
    }
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
