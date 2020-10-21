package soulwars;

import java.util.ArrayList;
import java.util.Stack;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import jig.ResourceManager;
import jig.Vector;

public class SoulWarsCamera {
	
	private int xOffSet;
	private int yOffSet;
	private int tileWidth;
	private int tileHeight;
	private int mapHeight;
	private int mapWidth;
	private int mapActualHeight;
	private int mapActualWidth;
	
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
		mapActualHeight = tileHeight * mapHeight;
		mapActualWidth = tileWidth * mapWidth;
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
		if (dy > 0 && yOffSet < mapHeight - 16) {
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
	
	public void renderPath(Stack<Step> currentPath, Graphics g) {
		boolean[][] pathMap = new boolean[mapWidth][mapHeight];
		for (int i = 0; i < currentPath.size(); i++) {
			pathMap[currentPath.get(i).getX()][currentPath.get(i).getY()] = true;
		}
		for (int xTile = 0; xTile < 16; xTile++) {
			for (int yTile = 0; yTile < 16; yTile++) {
		
				if(pathMap[xTile + xOffSet][yTile + yOffSet] != false) {
					g.drawString("X", (xTile*tileWidth)+16, (yTile*tileHeight)+16);
					
				}
			}
		}
		
	}
	
	public void renderSelected(ArrayList<SoulWarsUnit> selectedList, Graphics g) {
		g.setColor(Color.black);
		for(SoulWarsUnit selected : selectedList) {
			if((selected.getX()/tileWidth) > this.xOffSet && (selected.getY()/tileHeight) > this.yOffSet ) {
				g.drawRect((selected.getX()-10) - (xOffSet * 64), (selected.getY()-15) - (yOffSet*64), 18, 28);
			}
		}
	}
	
	
	
	public void renderTerrain(SoulWarsTile[][] terrain, Graphics g) {
		for (int xTile = 0; xTile < 16; xTile++) {
			for (int yTile = 0; yTile < 16; yTile++) {
				SoulWarsTile tile = terrain[xTile + xOffSet][yTile + yOffSet];
				g.translate(-xOffSet*64,-yOffSet*64);
				tile.render(g);
				g.translate(xOffSet*64, yOffSet*64);
			}
		}
	}
	
	public void renderUnits(ArrayList<SoulWarsUnit> units, Graphics g) {
		for (SoulWarsUnit unit : units) {
			if((unit.getX()/tileWidth) > this.xOffSet && (unit.getY()/tileHeight) > this.yOffSet ) {	
				if(unit.getX() < 1024 + (xOffSet*64)) {
					g.translate(-xOffSet*64,-yOffSet*64);
					unit.render(g);
					g.translate(xOffSet*64, yOffSet*64);
				}
			}
			
			if(unit.getPath() != null) {
				renderPath(unit.getPath(), g);
			}
			g.flush();
		}
	}
	

	//renders the current camera view on screen
	public void renderView(int x, int y, Graphics g) {
		SoulWarsTile[][] terrain = currentGame.getTerrain();
		ArrayList<SoulWarsUnit> units = currentGame.getUnits();
		renderTerrain(terrain, g);
		renderUnits(units, g);
		
	}
	
}
