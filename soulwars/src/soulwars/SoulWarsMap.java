package soulwars;

import java.util.Arrays;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import jig.ResourceManager;

import org.newdawn.slick.state.StateBasedGame;

public class SoulWarsMap implements TileBasedMap{
	
	private int mapWidth;
	private int mapHeight;
	
	private int tileWidth;
	private int tileHeight;
	
	private int[][] terrainTiles;
	//private int[][] units;
	//private boolean[][] visited;
	
	//private int[][] redBasePath;
	//private int[][] blueBasePath;

	@Override
	public int getWidthInTiles() {
		if (mapWidth != 0) {
			return mapWidth;
		}
		return 0;
	}

	@Override
	public int getHeightInTiles() {
		if (mapHeight != 0) {
			return mapHeight;
		}
		return 0;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public void renderMe(int x, int y, Graphics g) {
		int tileId;
		for (int xTile = 0; xTile < mapWidth; xTile++) {
			for (int yTile =0; yTile < mapHeight; yTile++) {
				tileId = terrainTiles[xTile][yTile];
				if(tileId == 101) {
				g.drawImage(ResourceManager.getImage(SoulWarsGame.TILE_RSC_101), xTile*tileWidth, yTile*tileHeight);
				}else
				g.drawImage(ResourceManager.getImage(SoulWarsGame.TILE_RSC_59), xTile*tileWidth, yTile*tileHeight);
			}
		}
	}
	
	public void loadNewMap(TiledMap mapPlan) {
		mapWidth = mapPlan.getWidth();
		mapHeight = mapPlan.getHeight();
		
		tileWidth = mapPlan.getTileWidth();
		tileHeight = mapPlan.getTileHeight();
		
		terrainTiles = new int[mapWidth][mapHeight];
		
		for (int xTile = 0; xTile < mapWidth; xTile++) {
			for (int yTile =0; yTile < mapHeight; yTile++) {
				terrainTiles[xTile][yTile] = mapPlan.getTileId(xTile, yTile, 0);
			}
		}
		
		
	}
	
	public void printMapArray() {
		System.out.println(Arrays.deepToString(terrainTiles).replace("],", "]\n"));
	}

}
