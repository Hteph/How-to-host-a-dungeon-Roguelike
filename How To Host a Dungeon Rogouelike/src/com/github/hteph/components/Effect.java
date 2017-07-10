package com.github.hteph.components;

public class Effect {
	
	protected int duration;

	// Constructor ----------------------------------------------

	public Effect(Effect other){
		this.duration = other.duration; 
	}

	public Effect(int duration){
		this.duration = duration;
	}
	//Methods --------------------------------------------------
	public boolean isDone() { 
		
		return duration < 1; 
		
	}


	public void update(Creature creature){
		
		duration--;
	}

	public void start(Creature creature){

	}

	public void end(Creature creature){

	}
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
