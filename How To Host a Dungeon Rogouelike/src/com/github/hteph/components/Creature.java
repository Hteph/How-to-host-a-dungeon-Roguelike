package com.github.hteph.components;

import java.awt.Color;

public class Creature {
	private World world;

	public int x;
	public int y;

	private char glyph;
	public char glyph() { return glyph; } //TODO turn into proper getter

	private Color color;
	public Color color() { return color; }//TODO turn into proper getter

	private CreatureAi ai;
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }//TODO turn into proper setter

	private int maxHp;
	public int maxHp() { return maxHp; }//TODO turn into proper getter

	private int hp;
	public int hp() { return hp; }//TODO turn into proper getter

	private int attackValue;
	public int attackValue() { return attackValue; }//TODO turn into proper getter

	private int defenseValue;

	public int z;

	private int oz;

	private int wz;
	public int defenseValue() { return defenseValue; }//TODO turn into proper getter

//Constructor --------------------------------------------------------------------

	public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
	}

//Methods --------------------------------------------------------------------------

	public void dig(int wx, int wy, int wz) {
		world.dig(wx, wy, wz);
	}

	public void moveBy(int mx, int my, int mz){
		Creature other = world.creature(x+mx, y+my,z+my);

		if (other == null){

			ai.onEnter(x+mx, y+my, world.tile(x+mx, y+my, z+mz));
		}else{

			attack(other);
		}
	}

	public void attack(Creature other){
		int amount = Math.max(0, attackValue() - other.defenseValue());

		amount = (int)(Math.random() * amount) + 1;

		other.modifyHp(-amount);
		notify("You attack the '%s' for %d damage.", other.glyph, amount);
		other.notify("The '%s' attacks you for %d damage.", glyph, amount);
	}

	public void modifyHp(int amount) {
		hp += amount;

		if (hp < 1)
			world.remove(this);
	} 

	public void update(){   
		ai.onUpdate();  
	}

	public boolean canEnter(int wx, int wy) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
	}

	public void notify(String message, Object ... params){
		ai.onNotify(String.format(message, params));
	}

	public void doAction(String message, Object ... params){
		int r = 9;
		for (int ox = -r; ox < r+1; ox++){
			for (int oy = -r; oy < r+1; oy++){
				if (ox*ox + oy*oy > r*r)
					continue;

				Creature other = world.creature(x+ox, y+oy, z+oz);

				if (other == null)
					continue;

				if (other == this)
					other.notify("You " + message + ".", params);
				else
					other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);
			}
		}
	}
	
	private String makeSecondPerson(String text){
	    String[] words = text.split(" ");
	    words[0] = words[0] + "s";
	    
	    StringBuilder builder = new StringBuilder();
	    for (String word : words){
	        builder.append(" ");
	        builder.append(word);
	    }
	    
	    return builder.toString().trim();
	}
}
