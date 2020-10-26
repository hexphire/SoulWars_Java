package soulwars;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class SoulWarsHQ extends Entity {
	private int health;
	private int maxHealth;
	private int energy;
	private int maxEnergy;
	private int armor;	
	private int maxArmor;
	private int team;
	private int soulCount;
	private int type;
	private int healCooldown;
	
	
	public SoulWarsHQ(float x, float y, int team, int type) {
		super(x, y);
		this.type = type;
		this.team = team;
		this.healCooldown = 1500;
		
		if(type == 0) {
			this.addImageWithBoundingBox(ResourceManager.getImage(SoulWarsGame.HQ_PLAYER_RSC).getScaledCopy(.5f));
			this.maxHealth = 100;
			this.health = 100;
			this.maxArmor = 100;
			this.armor = 100;
		}
		
		if(type == 1) {
			this.addImageWithBoundingBox(ResourceManager.getImage(SoulWarsGame.HQ_BOTTOM_RSC));
			this.addImage(ResourceManager.getImage(SoulWarsGame.HQ_TOP_RSC), new Vector(0, -64));
			this.maxHealth = 100;
			this.health = 100;
			this.maxArmor = 100;
			this.armor = 100;
		}
		
		if(type == 2) {
			this.addImageWithBoundingBox(ResourceManager.getImage(SoulWarsGame.HQ_TOWER_RSC));
			this.maxHealth = 75;
			this.health = 75;
			this.maxArmor = 50;
			this.armor = 50;
		}
		
		
	}
	
	public int getMapPosX() {
		int mapPos = (int)(this.getX()/64);
		return mapPos;
	}
	
	public int getMapPosY() {
		int mapPos = (int)(this.getY()/64);
		return mapPos;
	}
	
	public void addSoul(int soul) {
		this.soulCount += soul;
	}
	
	public boolean healCooldownCheck() {
		if(healCooldown <= 0) {
			return true;
		}
		return false;
	}
	
	public void resetHealCooldown() {
		healCooldown = 1500;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxArmor() {
		return maxArmor;
	}
	public int getArmor() {
		return armor;
	}
	
	public void damageArmor() {
		if(armor > 0)
			armor -= 10;		
	}
	
	public void takeDamage(int damage) {
		if(armor <= 0) {
			if(health > 0) {
				health -= damage;
			}
		}
			
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public int getType() {
		return type;
	}
	
	public int getSoulCount() {
		return soulCount;
	}
	
	public int getTeam() {
		return team;
	}
	
	public void update(int delta) {
		if(healCooldown > 0) {
			healCooldown -= delta;
		}else if (healCooldown < 0) {
			resetHealCooldown();
		}
	}

	

	
	
	

}
