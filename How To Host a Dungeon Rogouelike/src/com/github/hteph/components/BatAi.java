package com.github.hteph.components;

public class BatAi extends CreatureAi {

	// Constructor ----------------------------------------------
    public BatAi(Creature creature) {
        super(creature);
    }
	//Methods --------------------------------------------------
    public void onUpdate(){
        wander();
        wander();
    }
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
