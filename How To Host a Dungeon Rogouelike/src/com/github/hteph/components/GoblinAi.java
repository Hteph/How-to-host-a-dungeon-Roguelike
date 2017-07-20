package com.github.hteph.components;

public class GoblinAi extends CreatureAi {
    private Creature player;

	// Constructor ----------------------------------------------
    public GoblinAi(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }
	//Methods --------------------------------------------------
    
	public void onUpdate(){
		if (canUseBetterEquipment())
			useBetterEquipment();
		else if (canRangedWeaponAttack(player))
			creature.rangedWeaponAttack(player);
		else if (canThrowAt(player))
			creature.throwItem(getWeaponToThrow(), player.x, player.y, player.z);
		else if (creature.canSee(player.x, player.y, player.z))
			hunt(player);
		else if (canPickup())
			creature.pickup();
		else
			wander();
	}
    
	//Internal Methods ----------------------------------------
	
//	public void discussItemName(Item item, Creature other){
//    creature.doAction(item, "say \"%ss are %ss\"", item.appearance(), item.name());
//    other.learnName(item);
//}

	// Getters and Setters -------------------------------------
}
