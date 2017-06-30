package com.github.hteph.components;

import com.github.hteph.utilities.CreatureFactory;

public class FungusAi extends CreatureAi {
	 
	private CreatureFactory factory;
    private int spreadcount;
 
    public FungusAi(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
    }

    public void onUpdate(){
        if (spreadcount < 50 && Math.random() < 0.5)
            spread();
    }
 
    private void spread(){
        int x = creature.x + (int)(Math.random() * 11) - 5;
        int y = creature.y + (int)(Math.random() * 11) - 5;
  
        if (!creature.canEnter(x, y))
            return;
  
        Creature child = factory.newFungus(creature.z);
        child.x = x;
        child.y = y;
        child.z = creature.z;

        spreadcount++;
    }
}
