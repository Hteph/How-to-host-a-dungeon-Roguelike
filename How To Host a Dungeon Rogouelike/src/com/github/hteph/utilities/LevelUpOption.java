package com.github.hteph.utilities;

import com.github.hteph.components.Creature;

public abstract class LevelUpOption {
	  private String name;
	  public String name() { return name; }

	// Constructor ----------------------------------------------
	  public LevelUpOption(String name){
		    this.name = name;
		  }
	  
	//Methods --------------------------------------------------
	  public abstract void invoke(Creature creature);

	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
