package soulwars;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.pathfinding.Path;

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
	
	public void renderPath(Path currentPath, Graphics g) {
		boolean[][] pathMap = new boolean[mapWidth][mapHeight];
		for (int i = 0; i < currentPath.getLength(); i++) {
			pathMap[currentPath.getX(i)][currentPath.getY(i)] = true;
		}
		for (int xTile = 0; xTile < 16; xTile++) {
			for (int yTile = 0; yTile < 14; yTile++) {
		
				if(pathMap[xTile + xOffSet][yTile + yOffSet] != false) {
					g.drawString("X", (xTile*tileWidth)+16, (yTile*tileHeight)+16);
					
				}
			}
		}
		
	}
	
	public void renderTerrain(int[][] terrain, Graphics g) {
		Image image;
		int tileId;
		for (int xTile = 0; xTile < 16; xTile++) {
			for (int yTile = 0; yTile < 14; yTile++) {
				tileId = terrain[xTile + xOffSet][yTile + yOffSet];
				
				if(tileId == 101) {
					image = ResourceManager.getImage(SoulWarsGame.TILE_RSC_101);
					image.setFilter(Image.FILTER_LINEAR);
					g.drawImage(image, xTile*tileWidth, yTile*tileHeight);
				}else {
					image = ResourceManager.getImage(SoulWarsGame.TILE_RSC_59);
					image.setFilter(Image.FILTER_LINEAR);
					g.drawImage(image , xTile*tileWidth, yTile*tileHeight);
				}
			}
		}
	}
	public void renderUnits(SoulWarsUnit[][] units, Graphics g) {
		for (int xTile = 0; xTile < 16; xTile++) {
			for (int yTile = 0; yTile < 14; yTile++) {
		
				if(units[xTile + xOffSet][yTile + yOffSet] != null) {
					units[xTile + xOffSet][yTile + yOffSet].render(g);
					g.flush();
				}
			}
		}
	}
	//renders the current camera view on screen
	public void renderView(int x, int y, Graphics g) {
		int[][] terrain = currentGame.getTerrain();
		SoulWarsUnit[][] units = currentGame.getUnits();
		renderTerrain(terrain, g);
		renderUnits(units, g);
		
	}
}
