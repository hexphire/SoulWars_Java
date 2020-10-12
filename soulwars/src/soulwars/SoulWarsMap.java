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
	private SoulWarsUnit[][] units;
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
	public SoulWarsUnit[][] getUnits(){
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
		//if(units[tx][ty] != null) {
		//	return true;
		//}
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
		return units[x][y];
		
	}
	
	public ArrayList<SoulWarsUnit> getNear(SoulWarsUnit unit, int range){
		ArrayList<SoulWarsUnit> possibleTargets = new ArrayList<SoulWarsUnit>(range*2);
		for (int xTile = unit.getMapPosX() - range; xTile < unit.getMapPosX() + range; xTile++) {
			for (int yTile = unit.getMapPosY() - range; yTile < unit.getMapPosX() + range; yTile++) {
				if (xTile > -1 && yTile > -1) {
					if(xTile < getWidthInTiles() && yTile < getHeightInTiles()) {
						if(units[xTile][yTile] != null) {
							possibleTargets.add(units[xTile][yTile]);
						}
					}
				}
				
			}
		}
		return possibleTargets;
	}
	
	public ArrayList<SoulWarsUnit> getUnitList(){
		ArrayList<SoulWarsUnit> unitsList = new ArrayList<SoulWarsUnit>(mapWidth * mapHeight);
	
		for (int xTile = 0; xTile < mapWidth; xTile++) {
			for (int yTile =0; yTile < mapHeight; yTile++) {
				if(units[xTile][yTile] != null) {
					unitsList.add(units[xTile][yTile]);
				}
			}
		}
		return unitsList;
	}
	
	public int[] findUnit(SoulWarsUnit unit) {
		for(int xTile = 0; xTile < mapWidth; xTile++) {
			for(int yTile = 0; yTile < mapHeight; yTile++) {
				if (units[xTile][yTile] == unit) {
					int[] coords = {xTile,yTile};
					return coords ;
				}
			}
		}
		return null;
	}
	
	
	public void placeUnit(SoulWarsUnit unit, int cameraX, int cameraY) {
		int unitX = unit.getMapPosX();
		int unitY = unit.getMapPosY();
		if(units[unitX + cameraX][unitY + cameraY] == null) {
			units[unitX + cameraX][unitY + cameraY] = unit;
		}
	}
	
	public void updateUnit(SoulWarsUnit unit) {
		int newX = unit.getMapPosX();
		int newY = unit.getMapPosY();
		int [] mapPos = findUnit(unit);
		int oldX = mapPos[0];
		int oldY = mapPos[1];
		if(newX != oldX && newY != oldY) {
			units[oldX][oldY] = null;
			units[newX][newY] = unit;
					
		}
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
		units = new SoulWarsUnit[mapWidth][mapHeight];
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
