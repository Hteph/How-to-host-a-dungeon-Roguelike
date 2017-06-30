package com.github.hteph.components;

import java.util.List;

public class PlayerAi extends CreatureAi {

    private List<String> messages;
    
//Constructor ------------------------------------------------------------
	
    public PlayerAi(Creature creature, List<String> messages) {
        super(creature);
        this.messages = messages;
    }
  
// Methods -------------------------------------------------------------------
    
    public void onEnter(int x, int y, int z,Tile tile){
        if (tile.isGround()){
            creature.x = x;
            creature.y = y;
            creature.z = z;
        } else if (tile.isDiggable()) {
            creature.dig(x, y,z);
        }
    }
    
    public void onNotify(String message){
        messages.add(message);
    }
}
