package com.github.hteph.UI;

import com.github.hteph.components.Creature;
import com.github.hteph.components.Spell;

public class CastSpellScreen extends TargetBasedScreen {
    private Spell spell;

	// Constructor ----------------------------------------------
    public CastSpellScreen(Creature player, String caption, int sx, int sy, Spell spell) {
        super(player, caption, sx, sy);
        this.spell = spell;
    }
	//Methods --------------------------------------------------
    public void selectWorldCoordinate(int x, int y, int screenX, int screenY){
        player.castSpell(spell, x, y);
    }
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
