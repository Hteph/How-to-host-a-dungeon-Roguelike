package com.github.hteph.utilities;

import java.util.List;

import com.github.hteph.components.Creature;
import com.github.hteph.components.FungusAi;
import com.github.hteph.components.PlayerAi;
import com.github.hteph.components.World;

import asciiPanel.AsciiPanel;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world){
        this.world = world;
    }
    
    public Creature newPlayer(List<String> messages){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);
        world.addAtEmptyLocation(player,0);
        new PlayerAi(player, messages);
        return player;
    }

    public Creature newFungus(int depth){
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0);
        world.addAtEmptyLocation(fungus, depth);
        new FungusAi(fungus, this);
        return fungus;
    }
}
