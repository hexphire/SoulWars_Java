package soulwars;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
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
	Animation projectileAnim;
	Image baseImage;
	
	public Projectile(float PosX, float PosY, Vector shot, int type) {
		super(PosX,PosY);
		
		this.type = type;
		setupProjectile();
		if(this.type == 1) {
			velocity = shot.scale(2);
		}
		if(this.type == 0) {
			velocity = shot;
		}
	}
	
	private void setupProjectile() {
		if (this.type == 0) {
			this.addImageWithBoundingBox(ResourceManager.getImage(SoulWarsGame.TWR_ARW_RSC));
		}else if(this.type == 1) {
			sheet = new SpriteSheet(ResourceManager.getImage(SoulWarsGame.SPELL_RSC_FRBL).getScaledCopy(.5f), 32, 32, 0, 0);
			baseImage = sheet.getSprite(3, 0);
			this.projectileAnim = new Animation(sheet, 3, 0, 6, 0, true, 30, true);
			this.projectileAnim.setSpeed(projectileAnim.getSpeed()/4);
			this.addImageWithBoundingBox(baseImage);
			this.removeImage(baseImage);
			this.addAnimation(projectileAnim);
		}
	}
	
	public void update(final int delta) {
		this.translate(velocity);
	}

	public int getType() {
		return this.type;
	}
}
