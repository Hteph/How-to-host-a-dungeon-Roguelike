package com.github.hteph.UI;

import com.github.hteph.components.Creature;
import com.github.hteph.components.Item;

public class ExamineScreen extends InventoryBasedScreen {

	// Constructor ----------------------------------------------
    public ExamineScreen(Creature player) {
        super(player);
    }
	//Methods --------------------------------------------------
    protected String getVerb() {
        return "examine";
    }

    protected boolean isAcceptable(Item item) {
        return true;
    }

    protected Screen use(Item item) {
        String article = "aeiou".contains(player.nameOf(item).subSequence(0, 1)) ? "an " : "a ";
        player.notify("It's " + article + player.nameOf(item) + "." + item.details());
        return null;
    }
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
