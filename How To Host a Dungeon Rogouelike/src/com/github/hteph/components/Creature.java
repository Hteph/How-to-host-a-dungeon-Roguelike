package com.github.hteph.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.github.hteph.UI.LoseScreen;
import com.github.hteph.UI.WinScreen;
import com.github.hteph.utilities.Line;
import com.github.hteph.utilities.Point;

public class Creature {
	private World world;

	public int x;
	public int y;
	public int z;

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
	public void modifyAttackValue(int value) { attackValue += value; }
	public int attackValue() { 
		return attackValue
				+ (weapon == null ? 0 : weapon.attackValue())
				+ (armor == null ? 0 : armor.attackValue());
	}

	private int defenseValue;
	public void modifyDefenseValue(int value) { defenseValue += value; }
	public int defenseValue() { 
		return defenseValue
				+ (weapon == null ? 0 : weapon.defenseValue())
				+ (armor == null ? 0 : armor.defenseValue());
	}


	private int visionRadius;
	public void modifyVisionRadius(int value) { visionRadius += value; }
	public int visionRadius() { return visionRadius; }//TODO turn into proper getter

	private String name;
	public String name() { return name; }

	private Inventory inventory;
	public Inventory inventory() { return inventory; }

	private int maxFood;
	public int maxFood() { return maxFood; }

	private int food;
	public int food() { return food; }

	private Item weapon;
	public Item weapon() { return weapon; }

	private Item armor;
	public Item armor() { return armor; }

	private int xp;
	public int xp() { return xp; }

	private int level;
	public int level() { return level; }
	
	private int regenHpCooldown;
    private int regenHpPer1000;
    public void modifyRegenHpPer1000(int amount) { regenHpPer1000 += amount; }
    
    private List<Effect> effects;
    public List<Effect> effects(){ return effects; }
    private int maxMana;
    public int maxMana() { return maxMana; }
    
    private int mana;
    public int mana() { return mana; }
    public void modifyMana(int amount) { mana = Math.max(0, Math.min(mana+amount, maxMana));} 
    
    private int regenManaCooldown;
    private int regenManaPer1000;
    public void modifyRegenManaPer1000(int amount) { regenManaPer1000 += amount; }
    
    private int detectCreatures;
    public void modifyDetectCreatures(int amount) { detectCreatures += amount; }
    
    private String causeOfDeath;
    public String causeOfDeath() { return causeOfDeath; }

//Constructor --------------------------------------------------------------------

	public Creature(World world, char glyph, Color color,String name, int maxHp, int attack, int defense ){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.visionRadius = 9;
		this.name = name;
		this.inventory = new Inventory(20);
		this.maxFood = 1000;
		this.food = maxFood / 3 * 2;
		this.level = 1;
		this.regenHpCooldown = 10;
		this.effects = new ArrayList<Effect>();
		this.maxMana = 20;
		this.mana = maxMana;
		this.regenManaPer1000 = 20;

	}

//Methods --------------------------------------------------------------------------
	
	
	public void update(){
		modifyFood(-1);
		regenerateHealth();
		regenerateMana();
		updateEffects();
		ai.onUpdate();  
	}

	public void rangedWeaponAttack(Creature other){
		commonAttack(other, attackValue / 2 + weapon.rangedAttackValue(), "fire a %s at the %s for %d damage", nameOf(weapon), other.name);
	}

	public void meleeAttack(Creature other){
		commonAttack(other, attackValue(), "attack the %s for %d damage", other.name);
	}


	public void throwItem(Item item, int wx, int wy, int wz) {
		
		Point end = new Point(x, y, 0);

		for (Point p : new Line(x, y, wx, wy)){
			
			if (!realTile(p.x, p.y, z).isGround()) break;
				
			end = p;
		}

		wx = end.x;
		wy = end.y;

		Creature c = creature(wx, wy, wz);

		if (c != null) throwAttack(item, c);
		else doAction("throw a %s", nameOf(item));
		
		if (item.quaffEffect() != null && c != null) getRidOf(item);			
		else putAt(item, wx, wy, wz);
						
	}



	public void unequip(Item item){
		if (item == null)
			return;

		if (item == armor){
			doAction("remove a " + nameOf(item));
			armor = null;
		} else if (item == weapon) {
			doAction("put away a " + nameOf(item));
			weapon = null;
		}
	}

	public void equip(Item item){
		if (!inventory.contains(item)) {
			if (inventory.isFull()) {
				notify("Can't equip %s since you're holding too much stuff.", nameOf(item));
				return;
			} else {
				world.remove(item);
				inventory.add(item);
			}
		}

		if (item.attackValue() == 0 && item.rangedAttackValue() == 0 && item.defenseValue() == 0)
			return;

		if (item.attackValue() + item.rangedAttackValue() >= item.defenseValue()){
			unequip(weapon);
			doAction("wield a " + nameOf(item));
			weapon = item;
		} else {
			unequip(armor);
			doAction("put on a " + nameOf(item));
			armor = item;
		}
	}



	public void modifyFood(int amount) {
		food += amount;
		if (food > maxFood) {
			maxFood = maxFood + food / 2;
			food = maxFood;
			notify("You can't believe your stomach can hold that much!");
			modifyHp(-1, "Killed by overeating");
		} else if (food < 1 && isPlayer()) {
			modifyHp(-1000, "Starved to death");
		}
	}

	public void pickup(){
		Item item = world.item(x, y, z);

		if (inventory.isFull() || item == null){
			doAction("grab at the ground");
		} else {
			doAction("pickup a %s", nameOf(item));
			world.remove(x, y, z);
			inventory.add(item);
		}
	}

	public boolean isPlayer(){
		return glyph == '@';
	}

	public void quaff(Item item){
        doAction("quaff a " + nameOf(item));
        consume(item);
    }

    public void eat(Item item){
        doAction("eat a " + nameOf(item));
        consume(item);
    }
    
    private void consume(Item item){
        if (item.foodValue() < 0)
            notify("Gross!");
            
        addEffect(item.quaffEffect());
            
        modifyFood(item.foodValue());
        getRidOf(item);
    }
    
    private void addEffect(Effect effect){
        if (effect == null)
            return;
            
        effect.start(this);
        effects.add(effect);
    }

	public void dig(int wx, int wy, int wz) {
		modifyFood(-10);
		world.dig(wx, wy, wz);
		doAction("dig");
	}

	public void moveBy(int mx, int my, int mz){

		Tile tile = world.tile(x+mx, y+my, z+mz);

		if (mx==0 && my==0 && mz==0) return; // If standing still bail out from this


		if (mz == -1){

			if (tile == Tile.STAIRS_DOWN) {
				doAction("walk up the stairs to level %d", z+mz+1);
			} else {
				doAction("try to go up but are stopped by the cave ceiling");
				return;
			}
		} else if (mz == 1){
			if (tile == Tile.STAIRS_UP) {
				doAction("walk down the stairs to level %d", z+mz+1);
			} else {
				doAction("try to go down but are stopped by the cave floor");
				return;
			}
		}

		Creature other = world.creature(x+mx, y+my, z+mz);

		if (other == null) ai.onEnter(x+mx, y+my, z+mz, tile);
		else attack(other);
	}



	public void attack(Creature other){
		int amount = Math.max(0, attackValue() - other.defenseValue());

		amount = (int)(Math.random() * amount) + 1;

		other.modifyHp(-amount, "killed by"+other.name());
		notify("You attack the '%s' for %d damage.", other.name, amount);
		other.notify("The '%s' attacks you for %d damage.", name, amount);

		if (other.hp < 1) gainXp(other);
	}

	public void modifyHp(int amount, String causeOfDeath) {
		hp += amount;

		if (hp < 1) {
			doAction("die");
			leaveCorpse();
			world.remove(this);
			this.causeOfDeath = causeOfDeath;
		}
	} 



	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
	}

	public void notify(String message, Object ... params){
		ai.onNotify(String.format(message, params));
	}
	
	

	public void doAction(String message, Object ... params){
	    for (Creature other : getCreaturesWhoSeeMe()){
	        if (other == this){
	            other.notify("You " + message + ".", params);
	        } else {
	            other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
	        }
	    }
	}
	 
	public void doAction(Item item, String message, Object ... params){
	    if (hp < 1)
	        return;
	  
	    for (Creature other : getCreaturesWhoSeeMe()){
	        if (other == this){
	            other.notify("You " + message + ".", params);
	        } else {
	            other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
	        }
	        other.learnName(item);
	    }
	}
	 



//	public void doAction(String message, Object ... params){ //ToDo Change name to a better name 
//		int r = 9;
//		for (int ox = -r; ox < r+1; ox++){
//			for (int oy = -r; oy < r+1; oy++){
//				if (ox*ox + oy*oy > r*r)
//					continue;
//
//				Creature other = world.creature(x+ox, y+oy, z);
//
//				if (other == null)
//					continue;
//
//				if (other == this)
//					other.notify("You " + message + ".", params);
//				else
//					other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);
//			}
//		}
//	}

	private String makeSecondPerson(String text){ // TODO change name to emphasis text operations
		String[] words = text.split(" ");
		words[0] = words[0] + "s";

		StringBuilder builder = new StringBuilder();
		for (String word : words){
			builder.append(" ");
			builder.append(word);
		}

		return builder.toString().trim();
	}

	public boolean canSee(int wx, int wy, int wz){
		return (detectCreatures > 0 && world.creature(wx, wy, wz) != null || ai.canSee(wx, wy, wz));
	}

	public void drop(Item item){
		if (world.addAtEmptySpace(item, x, y, z)){
			doAction("drop a " + nameOf(item));
			inventory.remove(item);
			unequip(item);
		} else {
			notify("There's nowhere to drop the %s.", nameOf(item));
		}
	}


	public void modifyXp(int amount) {
		xp += amount;

		notify("You %s %d xp.", amount < 0 ? "lose" : "gain", amount);

		while (xp > (int)(Math.pow(level, 1.5) * 20)) {
			level++;
			doAction("advance to level %d", level);
			ai.onGainLevel();
			modifyHp(level * 2, "Levelling");
		}
	}

	public void gainXp(Creature other){
		int amount = 
				other.maxHp
				+ other.attackValue()
				+ other.defenseValue()
				- level * 2;

		if (amount > 0)
			modifyXp(amount);
	}

	public void gainMaxHp() {
		maxHp += 10;
		hp += 10;
		doAction("look healthier");
	}

	public void gainAttackValue() {
		attackValue += 2;
		doAction("look stronger");
	}

	public void gainDefenseValue() {
		defenseValue += 2;
		doAction("look tougher");
	}

	public void gainVision() {
		visionRadius += 1;
		doAction("look more aware");
	}
	
	public void gainMaxMana() {
        maxMana += 5;
        mana += 5;
        doAction("look more magical");
    }

    public void gainRegenMana(){
        regenManaPer1000 += 5;
        doAction("look a little less tired");
    }

	public String details() {
		return String.format("     level:%d     attack:%d     defense:%d     hp:%d", level, attackValue(), defenseValue(), hp);
	}

	public Tile realTile(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz);
	}

	public Tile tile(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz)) return world.tile(wx, wy, wz);
		else  return ai.rememberedTile(wx, wy, wz);
	}

	public Creature creature(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz)) return world.creature(wx, wy, wz);
		else return null;
	}

	public Item item(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz)) return world.item(wx, wy, wz);
		else  return null;
	}

	public String nameOf(Item item){
	    return ai.getName(item);
	}
	 
	public void learnName(Item item){
	    notify("The " + item.appearance() + " is a " + nameOf(item) + "!");
	    ai.setName(item, nameOf(item));
	}

	//Internal Methods ---------------------------------------------------------------

	private void leaveCorpse(){
		Item corpse = new Item('%', color, name + " corpse", null);
		corpse.modifyFoodValue(maxHp * 3);
		world.addAtEmptySpace(corpse, x, y, z);
		for (Item item : inventory.getItems()){
			if (item != null)
				drop(item);
		}
	}
	
	private void updateEffects(){
        List<Effect> done = new ArrayList<Effect>();
            
        for (Effect effect : effects){
            effect.update(this);
            if (effect.isDone()) {
                effect.end(this);
                done.add(effect);
            }
        }
            
        effects.removeAll(done);
    }

	private void getRidOf(Item item){
		inventory.remove(item);
		unequip(item);
	}

	private void putAt(Item item, int wx, int wy, int wz){
		inventory.remove(item);
		unequip(item);
		world.addAtEmptySpace(item, wx, wy, wz);
	}

	private void throwAttack(Item item, Creature other) {
		commonAttack(other, attackValue / 2 + item.thrownAttackValue(), "throw a %s at the %s for %d damage", nameOf(item), other.name);
		other.addEffect(item.quaffEffect());
	}


	private void commonAttack(Creature other, int attack, String action, Object ... params) { //TODO Isnt't that Object ... params a bit ugly?
		modifyFood(-2);

		int amount = Math.max(0, attack - other.defenseValue());

		amount = (int)(Math.random() * amount) + 1;

		Object[] params2 = new Object[params.length+1];
		for (int i = 0; i < params.length; i++){
			params2[i] = params[i];
		}
		params2[params2.length - 1] = amount;

		doAction(action, params2);

		other.modifyHp(-amount, "Killed by"+other.name());

		if (other.hp < 1)
			gainXp(other);
	}
	
	private void regenerateHealth(){
        regenHpCooldown -= regenHpPer1000;
        if (regenHpCooldown < 0){
            modifyHp(1, "Regeneration");
            modifyFood(-1);
            regenHpCooldown += 1000;
        }
    }
	
    private void regenerateMana(){
        regenManaCooldown -= regenManaPer1000;
        if (regenManaCooldown < 0){
            if (mana < maxMana) {
                modifyMana(1);
                modifyFood(-1);
            }
            regenManaCooldown += 1000;
        }
    }
    
    public void summon(Creature other) {
        world.add(other);
    }
    
    public void castSpell(Spell spell, int x2, int y2) {
        Creature other = creature(x2, y2, z);
        
        if (spell.manaCost() > mana){
            doAction("point and mumble but nothing happens");
            return;
        } else if (other == null) {
            doAction("point and mumble at nothing");
            return;
        }
        
        other.addEffect(spell.effect());
        modifyMana(-spell.manaCost());
    }
    
    
	private List<Creature> getCreaturesWhoSeeMe(){
	    List<Creature> others = new ArrayList<Creature>();
	    int r = 9;
	    for (int ox = -r; ox < r+1; ox++){
	        for (int oy = -r; oy < r+1; oy++){
	            if (ox*ox + oy*oy > r*r)
	                continue;
	    
	            Creature other = world.creature(x+ox, y+oy, z);
	    
	            if (other == null)
	                continue;
	    
	            others.add(other);
	        }
	    }
	    return others;
	}
}
