package com.github.hteph.components;

public class CreatureAi {
    protected Creature creature;

    public CreatureAi(Creature creature){
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    public void onEnter(int x, int y, Tile tile) { }

	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

	public void onNotify(String format) {
		// TODO Auto-generated method stub
		
	}
}
