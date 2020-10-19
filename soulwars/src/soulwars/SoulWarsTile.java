package soulwars;

import java.awt.Color;

import org.newdawn.slick.Image;

import jig.ConvexPolygon;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class SoulWarsTile extends Entity {
	
	
	boolean blocked;
	int type;
	
	
	SoulWarsTile(int x, int y, int type){
		this.type = type;
		
		Image image;
		
		if(type == 101) {
			image = ResourceManager.getImage(SoulWarsGame.TILE_RSC_101);
			image.setFilter(Image.FILTER_LINEAR);
			this.addImageWithBoundingBox(image);
		}else {
			image = ResourceManager.getImage(SoulWarsGame.TILE_RSC_59);
			image.setFilter(Image.FILTER_LINEAR);
			this.addImage(image);
		}
	}

}
