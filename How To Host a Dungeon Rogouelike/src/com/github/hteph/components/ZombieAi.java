package com.github.hteph.components;

import java.util.List;

import com.github.hteph.utilities.Path;
import com.github.hteph.utilities.Point;

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
  
  public void hunt(Creature target){
      List<Point> points = new Path(creature, target.x, target.y).points();
  
      int mx = points.get(0).x - creature.x;
      int my = points.get(0).y - creature.y;
  
      creature.moveBy(mx, my, 0);
  }
  
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
