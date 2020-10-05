package soulwars;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import jig.ResourceManager;

public class SoulWarsCamera {
	
	private int xOffSet;
	private int yOffSet;
	private int tileWidth;
	private int tileHeight;
	private int mapHeight;
	private int mapWidth;
	
	SoulWarsMap currentGame;
	
	
	public SoulWarsCamera(SoulWarsMap map) {
		super();
		xOffSet = 0;
		yOffSet = 0;
		currentGame = map;
		tileWidth = currentGame.getTileWidth();
		tileHeight = currentGame.getTileHeight();
		mapHeight = currentGame.getHeightInTiles();
		mapWidth = currentGame.getWidthInTiles();
	}
	//horizontal move
	public void moveCameraX(int dx) {
		if (dx > 0 && xOffSet < mapWidth - 16) {
			xOffSet += 1;
		}else if(dx < 0 && xOffSet > 0){
			xOffSet -= 1;
		}
		return;
	}
	//vertical move
	public void moveCameraY(int dy) {
		if (dy > 0 && yOffSet < mapHeight - 14) {
			yOffSet += 1;
		}else if(dy < 0 && yOffSet > 0) {
			yOffSet -= 1;
		}	
		return;
	}
	
	//getters for camera offset
	public int getCameraX() {
		return xOffSet;
	}
	
	public int getCameraY() {
		return yOffSet;
	}
	//renders the current camera view on screen
	public void renderView(int x, int y, Graphics g) {
		int[][] terrain = currentGame.getTerrain();
		SoulWarsUnit[][] units = currentGame.getUnits();
		int tileId;
		for (int xTile = 0; xTile < 16; xTile++) {
			for (int yTile = 0; yTile < 14; yTile++) {
				tileId = terrain[xTile + xOffSet][yTile + yOffSet];
				
				if(tileId == 101) {
					g.drawImage(ResourceManager.getImage(SoulWarsGame.TILE_RSC_101), xTile*tileWidth, yTile*tileHeight);
					g.flush();
				}else {
					g.drawImage(ResourceManager.getImage(SoulWarsGame.TILE_RSC_59), xTile*tileWidth, yTile*tileHeight);
					g.flush();
				}
				if(units[xTile + xOffSet][yTile + yOffSet] != null) {
					units[xTile + xOffSet][yTile + yOffSet].render(g);
					g.flush();
				}
			}
		}
		return;
	}
}
