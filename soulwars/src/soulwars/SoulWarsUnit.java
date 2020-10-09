package soulwars;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class SoulWarsUnit extends Entity {
	
	private Vector velocity;
	private int health;
	private int soulCount;
	private int attack;
	private boolean isRanged;
	Image unitSprite;
	
	
	
	
	public SoulWarsUnit(final float x, final float y, int type) {
		super(x, y);
		if(type == 1) {
			isRanged = false;
			health = 5;
			soulCount = 1;
			unitSprite = ResourceManager.getImage(SoulWarsGame.UNIT_RSC_REDW);
			unitSprite.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(unitSprite);
			
		}
	}
	
	public CoordinatePair<Float,Float> getPos() {
		CoordinatePair<Float,Float> unitPos = new CoordinatePair<Float,Float>(this.getX(),this.getY());
		return unitPos;
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
