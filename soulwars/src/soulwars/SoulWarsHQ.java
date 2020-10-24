package soulwars;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class SoulWarsHQ extends Entity {
	private int health;
	private int team;
	private int soulCount;
	
	
	public SoulWarsHQ(float x, float y, int team) {
		super(x, y);
		this.addImageWithBoundingBox(ResourceManager.getImage(SoulWarsGame.HQ_BOTTOM_RSC));
		this.addImage(ResourceManager.getImage(SoulWarsGame.HQ_TOP_RSC), new Vector(0, -64));
		this.team = team;
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
