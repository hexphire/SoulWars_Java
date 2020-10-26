package soulwars;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import jig.Entity;
import jig.ResourceManager;

public class SoulWarsSoul extends Entity {
	
	private int soulCount;
	private int lifeSpan;
	private SpriteSheet soulSheet;
	private Animation soulAnim;
	private Image baseImage;
	
	public SoulWarsSoul(float x, float y, int count, boolean limited) {
		super(x,y);
		
		soulSheet = new SpriteSheet(ResourceManager.getImage(SoulWarsGame.SOUL_RSC_MAIN).getScaledCopy(1.5f), 24, 24, 0 , 0);
		this.baseImage = soulSheet.getSprite(0, 0);
		this.soulAnim = new Animation(soulSheet, 0, 0, 3, 0,true, 15 ,true);
		soulAnim.setSpeed(soulAnim.getSpeed()/4);
		this.addImageWithBoundingBox(baseImage);
		this.removeImage(baseImage);
		this.addAnimation(soulAnim);
		this.soulCount = count;
	}
	
	public int getCount() {
		return soulCount;
	}

}
