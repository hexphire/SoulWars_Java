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
	private int maxHealth;
	private int maxMana;
	private int health;
	private int mana;
	private int soulCount;
	private int attack;
	private int fireballCooldown;
	private int healCooldown;
	private int hasteCooldown;
	private int summonCooldown;
	private int manaRegenTick;
	private float manaRegenMult;
	
	private SpriteSheet moveSheet;
	private SpriteSheet attackSheet;
	
	
	
	
	
	public WizardCharacter(final float x, final float y) throws SlickException {
		super(x, y);
		moveSheet = new SpriteSheet(ResourceManager.getImage(SoulWarsGame.CHAR_RSC_MAIN), 32, 32, 4, 0);
		attackSheet = new SpriteSheet(ResourceManager.getImage(SoulWarsGame.CHAR_RSC_ATK), 32, 32, 4, 0);
		this.addImageWithBoundingBox(moveSheet.getSprite(0, 0));
		this.maxHealth = 100;
		this.maxMana = 100;
		this.health = 100;
		this.mana = 100;
		this.manaRegenMult = .01f;
		this.fireballCooldown = 0;
		this.healCooldown = 0;
		this.hasteCooldown = 0;
		this.summonCooldown = 0;
		this.manaRegenTick = 0;
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public int getSoulCount() {
		return soulCount;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getMaxMana() {
		return maxMana;
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
	
	public void heal(int amount) {
		health += amount;
				
	}
	
	public boolean castFireball() {
		if(mana >= 10) {
			mana -= 10;
			fireballCooldown = 250;
			return true;
		}
		return false;
	}
	
	public boolean fireballCooldownCheck() {
		if(fireballCooldown <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean castHaste() {
		if(mana >= 15) {
			mana -= 15;
			hasteCooldown = 60000;
			return true;
		}
		return false;		
	}
	
	public boolean hasteCooldownCheck() {
		if(hasteCooldown <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean castHeal() {
		if(mana >= 25 && soulCount > 5) {
			mana -= 25;
			healCooldown = 50000;
			return true;
		}
		return false;
	}
	
	public boolean healCooldownCheck() {
		if(healCooldown <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean summonUnit() {
		if(mana >= 50 && soulCount >= 1) {
			soulCount -= 1;
			mana -= 50;
			summonCooldown = 30000;
			return true;
		}
		return false;
	}
	
	public boolean summonCooldownCheck() {
		if(summonCooldown <= 0) {
			return true;
		}
		return false;
	}
	
	public void collectSoul(int soulCount) {
		this.soulCount += soulCount;		
	}
	
	public void update(final int delta) {
		fireballCooldown -= delta;
		healCooldown -= delta;
		hasteCooldown -= delta;
		summonCooldown -= delta;
		manaRegenTick -= delta;
		if(manaRegenTick <= 0) {
			manaRegenTick = 250;
			if(mana < maxMana) {
				mana += maxMana * manaRegenMult;
				if(mana > maxMana) {
					mana = maxMana;
				}
			}
		}
		
		
	
	}
	
	public int getHash() {
		int hash = 17 * (int)(this.getX()) ^ 19 * (int)(this.getY());
		return hash;
	}
	
	
	
}


