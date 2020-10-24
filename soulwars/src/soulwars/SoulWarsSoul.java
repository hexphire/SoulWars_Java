package soulwars;

import org.newdawn.slick.SpriteSheet;

import jig.Entity;
import jig.ResourceManager;

public class SoulWarsSoul extends Entity {
	
	private int soulCount;
	private int lifeSpan;
	private SpriteSheet soulSheet;
	
	public SoulWarsSoul(float x, float y, int count, boolean limited) {
		super(x,y);
		soulSheet = new SpriteSheet(ResourceManager.getImage(SoulWarsGame.SOUL_RSC_MAIN), 16, 16, 0 , 0);
		this.addImageWithBoundingBox(soulSheet.getSprite(0, 0).getScaledCopy(1.5f));
	}
	
	public int getCount() {
		return soulCount;
	}

}
