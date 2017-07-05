package com.github.hteph.components;

import com.github.hteph.utilities.LevelUpController;
import com.github.hteph.utilities.Line;
import com.github.hteph.utilities.Point;

public class CreatureAi {
    protected Creature creature;

//Constructor -----------------------------------------
    
    public CreatureAi(Creature creature){
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

 //Methods --------------------------------------------------------   
    
    public void onEnter(int x, int y, int z, Tile tile){
        if (tile.isGround()){
             creature.x = x;
             creature.y = y;
             creature.z = z;
        } else {
             creature.doAction("bump into a wall");
        }
    }

	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

	public void onNotify(String format) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean canSee(int wx, int wy, int wz) {
        if (creature.z != wz)
            return false;
    
        if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.visionRadius()*creature.visionRadius())
            return false;
    
        for (Point p : new Line(creature.x, creature.y, wx, wy)){
            if (creature.tile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy)
                continue;
        
            return false;
        }
    
        return true;
    }
	
	public void wander(){
	    int mx = (int)(Math.random() * 3) - 1;
	    int my = (int)(Math.random() * 3) - 1;
	    
	    Creature other = creature.creature(creature.x + mx, creature.y + my, creature.z);
	    
	    if (other != null && other.glyph() == creature.glyph())
	        return;
	    else
	        creature.moveBy(mx, my, 0);
	}
	
	public void onGainLevel() {
	    new LevelUpController().autoLevelUp(creature);
	  }
}
