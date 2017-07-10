package com.github.hteph.components;

public class ZombieAi extends CreatureAi {
	
  private Creature player;

	// Constructor ----------------------------------------------
  public ZombieAi(Creature creature, Creature player) {
	    super(creature);
	    this.player = player;
  }
	//Methods --------------------------------------------------
  public void onUpdate(){
      if (Math.random() < 0.2)
          return;
  
      if (creature.canSee(player.x, player.y, player.z))
          hunt(player);
      else
          wander();
  }
  
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
