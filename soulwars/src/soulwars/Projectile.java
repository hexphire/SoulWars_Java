package soulwars;

import org.newdawn.slick.SpriteSheet;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Projectile extends Entity {
	
	int timer;
	int type;
	int damage;
	Vector velocity;
	SpriteSheet sheet;
	
	public Projectile(float PosX, float PosY, Vector shot, int type) {
		super(PosX,PosY);
		velocity = shot;
		this.type = type;
		setupProjectile();
	}
	
	private void setupProjectile() {
		if (this.type == 0) {
			
		}else if(this.type == 1) {
			sheet = new SpriteSheet(ResourceManager.getImage(SoulWarsGame.SPELL_RSC_FRBL), 60, 60, 4, 0);
			this.addImageWithBoundingBox(sheet.getSprite(3, 0).getScaledCopy(.4f));
		}
	}
	
	public void update(final int delta) {
		this.translate(velocity);
	}

	public int getType() {
		return this.type;
	}
}
