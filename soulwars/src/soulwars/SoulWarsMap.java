package soulwars;

import java.util.ArrayList;
import java.util.Arrays;


import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;



public class SoulWarsMap implements TileBasedMap{
	
	private int mapWidth;
	private int mapHeight;
	
	private int tileWidth;
	private int tileHeight;
	
	private int[][] terrainTiles;
	private ArrayList<SoulWarsUnit> units;
	private boolean[][] visited;
	
	private int[][] redBasePath;
	private int[][] blueBasePath;
	
	
	public SoulWarsMap() {
		super();
	}

	@Override
	public int getWidthInTiles() {
		
		return mapWidth;
	}

	@Override
	public int getHeightInTiles() {
		
		return mapHeight;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	public ArrayList<SoulWarsUnit> getUnits(){
		return units;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
		
	}
	
	public void clearVisited() {
		for(int x=0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				visited[x][y] = false;
			}
		}
	}

	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		// TODO Auto-generated method stub
		if(terrainTiles[tx][ty] == 59 || terrainTiles[tx][ty] == 60) {
			return true;
		}
		/*
		if(getUnit(tx,ty) != null) {
				return true;
		}
		*/
		return false;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		// TODO Auto-generated method stub
		if(terrainTiles[tx][ty] == 59 || terrainTiles[tx][ty] == 60) {
			return Float.MAX_VALUE;
		}
		return 1;
	}
	
	public SoulWarsUnit getUnit(int x, int y) {
		for(SoulWarsUnit unit : units) {
			if (unit.getMapPosX() == x && unit.getMapPosY() == y) {
				return unit;
			}
		}
		return null;		
	}
	
	public ArrayList<SoulWarsUnit> getNear(SoulWarsUnit unit, int range){
		ArrayList<SoulWarsUnit> possibleTargets = new ArrayList<SoulWarsUnit>(range*range);
		for (SoulWarsUnit possible : units) {
			if (possible.getMapPosX() > (unit.getMapPosX()-range)) {
				if(possible.getMapPosX() < (unit.getMapPosX()+range)) {
					if(possible.getMapPosY() > (unit.getMapPosY()-range)) {
						if(possible.getMapPosX() < (unit.getMapPosY()+ range)) {
							if(possible.getHash() != unit.getHash()) {
								possibleTargets.add(possible);
							}
						}
					}
				}
			}
			
		}
		return possibleTargets;
	}
	
	
	public int[] findUnit(SoulWarsUnit unit) {
		int coords[] = new int[2];
		for (SoulWarsUnit unitChecking : units) {
			if(unitChecking.getHash() == unit.getHash()) {
				coords[0] = unitChecking.getMapPosX();
				coords[1] = unitChecking.getMapPosY();
				return coords;
			}
		}
		return null;
	}
	
	
	public void placeUnit(SoulWarsUnit unit) {
		units.add(unit);
	}
	
	
	
	public int[][] getTerrain(){
		return terrainTiles;
	}
	
	public void loadNewMap(TiledMap mapPlan) {
		mapWidth = mapPlan.getWidth();
		mapHeight = mapPlan.getHeight();
		
		tileWidth = mapPlan.getTileWidth();
		tileHeight = mapPlan.getTileHeight();
		
		terrainTiles = new int[mapWidth][mapHeight];
		units = new ArrayList<SoulWarsUnit>(mapWidth * mapHeight);
		visited = new boolean[mapWidth][mapHeight];
		redBasePath = new int[mapWidth][mapHeight];
		blueBasePath = new int[mapWidth][mapHeight];
		
		for (int xTile = 0; xTile < mapWidth; xTile++) {
			for (int yTile =0; yTile < mapHeight; yTile++) {
				terrainTiles[xTile][yTile] = mapPlan.getTileId(xTile, yTile, 0);
			}
		}
		
		
	}
	
	public void printMapArray() {
		System.out.println(mapWidth);
		System.out.println(mapHeight);
		System.out.println(Arrays.deepToString(terrainTiles).replace("],", "]\n"));
	}

}
