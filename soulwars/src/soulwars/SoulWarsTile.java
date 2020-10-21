package soulwars;






import org.newdawn.slick.Graphics;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


public class SoulWarsTile extends Entity {
	
	
	private boolean blocked;
	private int type;
	private int tileX;
	private int tileY;
	
	
	SoulWarsTile(int x, int y, int type){
		super(x,y);
		this.tileX = x;
		this.tileY = y;
		this.type = type;

		
		if(type == 59 || type == 60) {
						
			blocked = true;
			
			
			addImageWithBoundingBox(ResourceManager.getImage(SoulWarsGame.TILE_RSC_59), new Vector(31,31));
		}else {
			
			addImage(ResourceManager.getImage(SoulWarsGame.TILE_RSC_101), new Vector(31,31));
		}
	}
	
	public boolean getBlocked() {
		return blocked;
	}
	
	public int getType() {
		return type;
	}
	
	public int getTileX() {
		return tileX;
	}
	
	public int getTileY() {
		return tileY;
	}
	
	public int getHash() {
		int hash = 17 * (int)(this.getX()) ^ 19 * (int)(this.getY());
		return hash;
	}
	


}
