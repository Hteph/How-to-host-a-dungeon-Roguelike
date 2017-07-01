package com.github.hteph.utilities;

import java.util.List;

import com.github.hteph.components.BatAi;
import com.github.hteph.components.Creature;
import com.github.hteph.components.FungusAi;
import com.github.hteph.components.Item;
import com.github.hteph.components.PlayerAi;
import com.github.hteph.components.World;

import asciiPanel.AsciiPanel;

public class StuffFactory {
    private World world;

    public StuffFactory(World world){
        this.world = world;
    }
    
    public Creature newPlayer(List<String> messages, FieldOfView fov){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite,"player", 100, 20, 5);
        world.addAtEmptyLocation(player,0);
        new PlayerAi(player, messages, fov);
        return player;
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
        Item rock = new Item(',', AsciiPanel.yellow, "rock");
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }
    
    public Item newVictoryItem(int depth){
        Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear");
        world.addAtEmptyLocation(item, depth);
        return item;
    }
    
    public Item newDagger(int depth){
        Item item = new Item(')', AsciiPanel.white, "dagger");
        item.modifyAttackValue(5);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item newSword(int depth){
        Item item = new Item(')', AsciiPanel.brightWhite, "sword");
        item.modifyAttackValue(10);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item newStaff(int depth){
        Item item = new Item(')', AsciiPanel.yellow, "staff");
        item.modifyAttackValue(5);
        item.modifyDefenseValue(3);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item newLightArmor(int depth){
        Item item = new Item('[', AsciiPanel.green, "tunic");
        item.modifyDefenseValue(2);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item newMediumArmor(int depth){
        Item item = new Item('[', AsciiPanel.white, "chainmail");
        item.modifyDefenseValue(4);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item newHeavyArmor(int depth){
        Item item = new Item('[', AsciiPanel.brightWhite, "platemail");
        item.modifyDefenseValue(6);
        world.addAtEmptyLocation(item, depth);
        return item;
      }

      public Item randomWeapon(int depth){
        switch ((int)(Math.random() * 3)){
        case 0: return newDagger(depth);
        case 1: return newSword(depth);
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
}