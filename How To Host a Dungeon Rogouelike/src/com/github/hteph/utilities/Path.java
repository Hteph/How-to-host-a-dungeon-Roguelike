package com.github.hteph.utilities;

import java.util.List;

import com.github.hteph.components.Creature;

public class Path {
	

	  private static PathFinder pf = new PathFinder();

	  private List<Point> points; // TODO Better name, for frakks sake, it is a list of points!
	  public List<Point> points() { return points; }

	// Constructor ----------------------------------------------
	  public Path(Creature creature, int x, int y){
	      points = pf.findPath(creature, 
	                           new Point(creature.x, creature.y, creature.z), 
	                           new Point(x, y, creature.z), 
	                           300);
	  }
	//Methods --------------------------------------------------

	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
