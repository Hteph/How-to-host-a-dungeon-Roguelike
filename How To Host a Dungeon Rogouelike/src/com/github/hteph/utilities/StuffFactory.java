package com.github.hteph.utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.hteph.components.BatAi;
import com.github.hteph.components.Creature;
import com.github.hteph.components.Effect;
import com.github.hteph.components.FungusAi;
import com.github.hteph.components.GoblinAi;
import com.github.hteph.components.Item;
import com.github.hteph.components.PlayerAi;
import com.github.hteph.components.World;
import com.github.hteph.components.ZombieAi;

import asciiPanel.AsciiPanel;

public class StuffFactory {
	
	private Map<String, Color> potionColors;
    private List<String> potionAppearances;
    private World world;

    public StuffFactory(World world){
        this.world = world;
        
        setUpPotionAppearances();
    }
    
    public Creature newPlayer(List<String> messages, FieldOfView fov){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite,"player", 100, 20, 5);
        world.addAtEmptyLocation(player,0);
        new PlayerAi(player, messages, fov);
        return player;
    }
    
    public Creature newZombie(int depth, Creature player){
        Creature zombie = new Creature(world, 'z', AsciiPanel.white, "zombie", 50, 10, 10);
        world.addAtEmptyLocation(zombie, depth);
        new ZombieAi(zombie, player);
        return zombie;
    }

    public Creature newFungus(int depth){
        Creature fungus = new Creature(world, 'f', AsciiPanel.green,"fungus", 10, 0, 0);
        world.addAtEmptyLocation(fungus, depth);
        new FungusAi(fungus, this);
        return fungus;
    }
    
    public Creature newBat(int depth){
        Creature bat = new Creature(world, 'b', AsciiPanel.yellow,"bat", 15, 5, 0);
        world.addAtEmptyLocation(bat, depth);
        new BatAi(bat);
        return bat;
    }
    
    public Item newRock(int depth){
        Item rock = new Item(',', AsciiPanel.yellow, "rock", null);
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }
    
    public Item newVictoryItem(int depth){
        Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear", null);
        world.addAtEmptyLocation(item, depth);
        return item;
    }
    
    public Item newDagger(int depth){
        Item item = new Item(')', AsciiPanel.white, "dagger", null);
        item.modifyAttackValue(5);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item newSword(int depth){
        Item item = new Item(')', AsciiPanel.brightWhite, "sword", null);
        item.modifyAttackValue(10);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item newStaff(int depth){
        Item item = new Item(')', AsciiPanel.yellow, "staff", null);
        item.modifyAttackValue(5);
        item.modifyDefenseValue(3);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item newLightArmor(int depth){
        Item item = new Item('[', AsciiPanel.green, "tunic", null);
        item.modifyDefenseValue(2);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item newMediumArmor(int depth){
        Item item = new Item('[', AsciiPanel.white, "chainmail", null);
        item.modifyDefenseValue(4);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item newHeavyArmor(int depth){
        Item item = new Item('[', AsciiPanel.brightWhite, "platemail", null);
        item.modifyDefenseValue(6);
        world.addAtEmptyLocation(item, depth);
        return item;
      }
      
      public Item newBow(int depth){
          Item item = new Item(')', AsciiPanel.yellow, "bow", null);
          item.modifyAttackValue(1);
          item.modifyRangedAttackValue(5);
          world.addAtEmptyLocation(item, depth);
          return item;
      }

      public Item randomWeapon(int depth){
        switch ((int)(Math.random() * 3)){
        case 0: return newDagger(depth);
        case 1: return newSword(depth);
        case 2: return newBow(depth);
        default: return newStaff(depth);
        }
      }

      public Item randomArmor(int depth){
        switch ((int)(Math.random() * 3)){
        case 0: return newLightArmor(depth);
        case 1: return newMediumArmor(depth);
        default: return newHeavyArmor(depth);
        }
      }
      
      public Item newEdibleWeapon(int depth){
    	    Item item = new Item(')', AsciiPanel.yellow, "baguette", null);
    	    item.modifyAttackValue(3);
    	    item.modifyFoodValue(50);
    	    world.addAtEmptyLocation(item, depth);
    	    return item;
    	  }
      
      public Creature newGoblin(int depth, Creature player){
          Creature goblin = new Creature(world, 'g', AsciiPanel.brightGreen, "goblin", 66, 15, 5);
          goblin.equip(randomWeapon(depth));
          goblin.equip(randomArmor(depth));
          world.addAtEmptyLocation(goblin, depth);
          new GoblinAi(goblin, player);
          return goblin;
      } 
      
      public Item newPotionOfHealth(int depth){
    	  String appearance = potionAppearances.get(0);
    	    Item item = new Item('!', potionColors.get(appearance), "health potion",appearance);
    	    item.setQuaffEffect(new Effect(1){
    	        public void start(Creature creature){
    	        	
    	            if (creature.hp() == creature.maxHp()) return;
    	            
    	            creature.learnName(item);     	                                
    	            creature.modifyHp(15, "a Health Potion");
    	            creature.doAction("look healthier");
    	        }
    	    });
    	                
    	    world.addAtEmptyLocation(item, depth);
    	    return item;
    	}
      
      public Item newPotionOfPoison(int depth){
    	  String appearance = potionAppearances.get(1);
    	    Item item = new Item('!', potionColors.get(appearance), "poison potion", appearance);
    	    item.setQuaffEffect(new Effect(20){
    	        public void start(Creature creature){
    	            creature.doAction("look sick");
    	            creature.learnName(item);
    	        }
    	                       
    	        public void update(Creature creature){
    	            super.update(creature);
    	            creature.modifyHp(-1, "a Poison");
    	        }
    	        
    	    });
    	                
    	    world.addAtEmptyLocation(item, depth);
    	    return item;
    	} 
      
      public Item newPotionOfWarrior(int depth){
    	  String appearance = potionAppearances.get(2);
    	    Item item = new Item('!', potionColors.get(appearance), "warrior's potion", appearance);
    	    item.setQuaffEffect(new Effect(20){
    	        public void start(Creature creature){
    	        	creature.learnName(item);
    	            creature.modifyAttackValue(5);
    	            creature.modifyDefenseValue(5);
    	            creature.doAction("look stronger");
    	        }
    	        public void end(Creature creature){
    	            creature.modifyAttackValue(-5);
    	            creature.modifyDefenseValue(-5);
    	            creature.doAction("look less strong");
    	        }
    	    });
    	                
    	    world.addAtEmptyLocation(item, depth);
    	    return item;
    	}
      
      public Item randomPotion(int depth){
          switch ((int)(Math.random() * 3)){
          case 0: return newPotionOfHealth(depth);
          case 1: return newPotionOfPoison(depth);
          default: return newPotionOfWarrior(depth);
          }
  }
      
 
  	
    private void setUpPotionAppearances(){
        potionColors = new HashMap<String, Color>();
        potionColors.put("red potion", AsciiPanel.brightRed);
        potionColors.put("yellow potion", AsciiPanel.brightYellow);
        potionColors.put("green potion", AsciiPanel.brightGreen);
        potionColors.put("cyan potion", AsciiPanel.brightCyan);
        potionColors.put("blue potion", AsciiPanel.brightBlue);
        potionColors.put("magenta potion", AsciiPanel.brightMagenta);
        potionColors.put("dark potion", AsciiPanel.brightBlack);
        potionColors.put("grey potion", AsciiPanel.white);
        potionColors.put("light potion", AsciiPanel.brightWhite);

        potionAppearances = new ArrayList<String>(potionColors.keySet());
        Collections.shuffle(potionAppearances);
    }
  	
  	
}
