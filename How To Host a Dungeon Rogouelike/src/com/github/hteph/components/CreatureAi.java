package com.github.hteph.components;

import java.util.List;

import com.github.hteph.utilities.LevelUpController;
import com.github.hteph.utilities.Line;
import com.github.hteph.utilities.Path;
import com.github.hteph.utilities.Point;

public class CreatureAi {
	protected Creature creature;

// Constructor -----------------------------------------

	public CreatureAi(Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}

	// Methods --------------------------------------------------------

	public void onEnter(int x, int y, int z, Tile tile) {
		if (tile.isGround()) {
			creature.x = x;
			creature.y = y;
			creature.z = z;
		} else {
			creature.doAction("bump into a wall");
		}
	}

	public void onUpdate() {
		// TODO Auto-generated method stub

	}

	public void onNotify(String format) {
		// TODO Auto-generated method stub

	}

	public boolean canSee(int wx, int wy, int wz) {
		if (creature.z != wz)
			return false;

		if ((creature.x - wx) * (creature.x - wx) + (creature.y - wy) * (creature.y - wy) > creature.visionRadius()
				* creature.visionRadius())
			return false;

		for (Point p : new Line(creature.x, creature.y, wx, wy)) {
			
			if (creature.realTile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy) continue;
				
			return false;
		}

		return true;
	}

	public void wander() {
		int mx = (int) (Math.random() * 3) - 1;
		int my = (int) (Math.random() * 3) - 1;

		Creature other = creature.creature(creature.x + mx, creature.y + my, creature.z);

		if (other != null && other.glyph() == creature.glyph()) return;			
		else creature.moveBy(mx, my, 0);
			
	}

	public void onGainLevel() {
		new LevelUpController().autoLevelUp(creature);
	}

	public Tile rememberedTile(int wx, int wy, int wz) {
		return Tile.UNKNOWN;
	}

	protected boolean canRangedWeaponAttack(Creature other) {
		return creature.weapon() != null && creature.weapon().rangedAttackValue() > 0
				&& creature.canSee(other.x, other.y, other.z);
	}

	protected boolean canThrowAt(Creature other) {
		return creature.canSee(other.x, other.y, other.z) && getWeaponToThrow() != null;
	}

	protected Item getWeaponToThrow() {
		Item toThrow = null;

		for (Item item : creature.inventory().getItems()) {
			if (item == null || creature.weapon() == item || creature.armor() == item)
				continue;

			if (toThrow == null || item.thrownAttackValue() > toThrow.attackValue())
				toThrow = item;
		}

		return toThrow;
	}

	protected boolean canPickup() {
		return creature.item(creature.x, creature.y, creature.z) != null && !creature.inventory().isFull();
	}

	public void hunt(Creature target) {
		List<Point> points = new Path(creature, target.x, target.y).points();

		int mx = points.get(0).x - creature.x;
		int my = points.get(0).y - creature.y;

		creature.moveBy(mx, my, 0);
	}

	protected boolean canUseBetterEquipment() {
		int currentWeaponRating = creature.weapon() == null ? 0
				: creature.weapon().attackValue() + creature.weapon().rangedAttackValue();
		int currentArmorRating = creature.armor() == null ? 0 : creature.armor().defenseValue();

		for (Item item : creature.inventory().getItems()) {
			if (item == null)
				continue;

			boolean isArmor = item.attackValue() + item.rangedAttackValue() < item.defenseValue();

			if (item.attackValue() + item.rangedAttackValue() > currentWeaponRating
					|| isArmor && item.defenseValue() > currentArmorRating)
				return true;
		}

		return false;
	}

	protected void useBetterEquipment() {
		int currentWeaponRating = creature.weapon() == null ? 0 : creature.weapon().attackValue() + creature.weapon().rangedAttackValue();
				
		int currentArmorRating = creature.armor() == null ? 0 : creature.armor().defenseValue();

		for (Item item : creature.inventory().getItems()) {
			if (item == null) continue;
				
			boolean isArmor = item.attackValue() + item.rangedAttackValue() < item.defenseValue();

			if (item.attackValue() + item.rangedAttackValue() > currentWeaponRating
					|| isArmor && item.defenseValue() > currentArmorRating) {
				creature.equip(item);
			}
		}
	}
}
