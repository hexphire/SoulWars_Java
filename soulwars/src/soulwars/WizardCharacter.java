package soulwars;

import java.util.Stack;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class WizardCharacter extends Entity{
	
	private Vector velocity;
	private int health;
	private int soulCount;
	private int attack;
	private SpriteSheet moveSheet;
	private SpriteSheet attackSheet;
	
	
	
	
	
	public WizardCharacter(final float x, final float y) throws SlickException {
		super(x, y);
		moveSheet = new SpriteSheet(ResourceManager.getImage(SoulWarsGame.CHAR_RSC_MAIN), 32, 32, 4, 0);
		attackSheet = new SpriteSheet(ResourceManager.getImage(SoulWarsGame.CHAR_RSC_ATK), 32, 32, 4, 0);
		this.addImageWithBoundingBox(moveSheet.getSprite(0, 0));
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public int getSoulCount() {
		return soulCount;
	}
		
	public int getMapPosX() {
		int mapPos = (int)(this.getX()/64);
		return mapPos;
	}
	
	public int getMapPosY() {
		int mapPos = (int)(this.getY()/64);
		return mapPos;
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
	
	public void castFireBall() {
		
	}
	
	public void castHaste() {
		
	}
	
	public void castHeal() {
		
	}
	
	public void castMageArmor() {
		
	}
	
	public void collectSoul(int soulCount) {
		this.soulCount += soulCount;		
	}
	
	public void update(final int delta) {
	
	}
	
	public int getHash() {
		int hash = 17 * (int)(this.getX()) ^ 19 * (int)(this.getY());
		return hash;
	}
	
	
	
}


