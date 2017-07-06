package com.github.hteph.UI;

import com.github.hteph.components.Creature;
import com.github.hteph.components.Item;
import com.github.hteph.utilities.Line;
import com.github.hteph.utilities.Point;

public class ThrowAtScreen extends TargetBasedScreen {
    private Item item;

	// Constructor ----------------------------------------------
    public ThrowAtScreen(Creature player, int sx, int sy, Item item) {
        super(player, "Throw " + item.name() + " at?", sx, sy);
        this.item = item;
    }
	//Methods --------------------------------------------------
    public boolean isAcceptable(int x, int y) {
        if (!player.canSee(x, y, player.z))
            return false;
    
        for (Point p : new Line(player.x, player.y, x, y)){
            if (!player.realTile(p.x, p.y, player.z).isGround())
                return false;
        }
    
        return true;
    }

    public void selectWorldCoordinate(int x, int y, int screenX, int screenY){
        player.throwItem(item, x, y, player.z);
    }
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
