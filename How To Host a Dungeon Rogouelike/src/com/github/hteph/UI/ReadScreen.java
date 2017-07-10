package com.github.hteph.UI;

import com.github.hteph.components.Creature;
import com.github.hteph.components.Item;

public class ReadScreen extends InventoryBasedScreen {
    private int sx;
    private int sy;
	// Constructor ----------------------------------------------
    public ReadScreen(Creature player, int sx, int sy) {
        super(player);
        this.sx = sx;
        this.sy = sy;
    }
	//Methods --------------------------------------------------
    protected String getVerb() {
        return "read";
    }

    protected boolean isAcceptable(Item item) {
        return !item.writtenSpells().isEmpty();
    }

    protected Screen use(Item item) {
        return new ReadSpellScreen(player, sx, sy, item);
    }
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
