package com.github.hteph.components;

public class Spell {

    private String name;
    public String name() { return name; }

    private int manaCost;
    public int manaCost() { return manaCost; }

    private Effect effect;
    public Effect effect() {
    	System.out.println("SPELL");
    	Effect tEff = new Effect(effect); 
    	return tEff;
    	}
    


	// Constructor ----------------------------------------------
    
    public Spell(String name, int manaCost, Effect effect){
    	this.name = name;
    	this.manaCost = manaCost;
    	this.effect = effect;

    	}
    
	//Methods --------------------------------------------------
    

    
	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
