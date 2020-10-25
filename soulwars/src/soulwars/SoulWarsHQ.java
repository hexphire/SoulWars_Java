package soulwars;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class SoulWarsHQ extends Entity {
	private int health;
	private int team;
	private int soulCount;
	private int type;
	
	
	public SoulWarsHQ(float x, float y, int team, int type) {
		super(x, y);
		this.type = type;
		this.team = team;
		
		if(type == 0) {
			this.addImageWithBoundingBox(ResourceManager.getImage(SoulWarsGame.HQ_PLAYER_RSC).getScaledCopy(.5f));
		}
		
		if(type == 1) {
			this.addImageWithBoundingBox(ResourceManager.getImage(SoulWarsGame.HQ_BOTTOM_RSC));
			this.addImage(ResourceManager.getImage(SoulWarsGame.HQ_TOP_RSC), new Vector(0, -64));
		}
		
		if(type == 2) {
			this.addImageWithBoundingBox(ResourceManager.getImage(SoulWarsGame.HQ_TOWER_RSC));
		}
		
		
	}
	
	public void addSoul(int soul) {
		this.soulCount += soul;
	}
	
	public int getSoulCount() {
		return soulCount;
	}
	
	public int getTeam() {
		return team;
	}
	
	public int getHealth() {
		return health;
	}

}
