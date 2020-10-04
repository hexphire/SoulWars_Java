package soulwars;

import jig.Entity;
import jig.Vector;

public class SoulWarsUnit extends Entity {
	
	private Vector velocity;
	private int health;
	private int soulCount;
	private int attack;
	private boolean isRanged;
	
	
	public SoulWarsUnit(final float x, final float y) {
		super(x, y);
	}
	
	public void setVelocity(int dx, int dy) {
		velocity = new Vector(dx,dy);		
	}
	
	public void attack(SoulWarsUnit target) {
		target.takeDamage(attack);
	}
	
	public void takeDamage(int dmg) {
		health = health - dmg;
	}
	
	public void update(final int delta) {
		
	}

}
