package com.github.hteph.UI;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.github.hteph.components.Creature;
import com.github.hteph.components.World;
import com.github.hteph.utilities.CreatureFactory;
import com.github.hteph.utilities.WorldBuilder;

import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {
	
	private World world;
	private Creature player;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;
    
// Constructor ---------------------------------------------------
    public PlayScreen(){
        screenWidth = 80;
        screenHeight = 21;
        messages = new ArrayList<String>();
        createWorld();
        CreatureFactory creatureFactory = new CreatureFactory(world);
        createCreatures(creatureFactory);

        
    }
    
// Methods -----------------------------------------------------------
    
    public void displayOutput(AsciiPanel terminal) {
    	int left = getScrollX();
        int top = getScrollY();
   
        displayTiles(terminal, left, top);
        displayMessages(terminal, messages);
        
		terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 22);
	    String stats = String.format(" %3d/%3d hp", player.hp(), player.maxHp());
	    terminal.write(stats, 1, 23);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
		case KeyEvent.VK_ESCAPE: return new LoseScreen();
		case KeyEvent.VK_ENTER: return new WinScreen();
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_H: player.moveBy(-1, 0,0); break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_L: player.moveBy( 1, 0,0); break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_K: player.moveBy( 0,-1,0); break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_J: player.moveBy( 0, 1,0); break;
		case KeyEvent.VK_Y: player.moveBy(-1,-1,0); break;
		case KeyEvent.VK_U: player.moveBy( 1,-1,0); break;
		case KeyEvent.VK_B: player.moveBy(-1, 1,0); break;
		case KeyEvent.VK_N: player.moveBy( 1, 1,0); break;

        }
        world.update();
        return this;
    }
    
    //find the scroll limits
    public int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
        
        //public int getScrollX() { return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth)); }

    }
    
    public int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
    }
    
  // Internal methods ------------------------------------------------  
    
    private void createCreatures(CreatureFactory creatureFactory){
        player = creatureFactory.newPlayer(messages);
        for (int z = 0; z < world.depth(); z++){
        for (int i = 0; i < 8; i++){
            creatureFactory.newFungus(z);
        }
        }
    }
    
    private void displayTiles(AsciiPanel terminal, int left, int top) { // Display the right part of the world
    	
        for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;

                Creature creature = world.creature(wx, wy, player.z);
                if (creature != null)
                    terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color());
                else
                    terminal.write(world.glyph(wx, wy,player.z), x, y, world.color(wx, wy,player.z));
            }
        }
    }
    
//    private void scrollBy(int mx, int my){ // no scrolling outside the world!
//    	player.x = Math.max(0, Math.min(player.x + mx, world.width() - 1));
//        player.y = Math.max(0, Math.min(player.y + my, world.height() - 1));
//    }
    
    private void createWorld(){
        world = new WorldBuilder(90, 32,5).makeCaves().build();
    }
    
    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++){
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }
}
