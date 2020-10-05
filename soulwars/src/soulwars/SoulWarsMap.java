package soulwars;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		// TODO Auto-generated method stub
		if(terrainTiles[tx][ty] == 59 || terrainTiles[tx][ty] == 60) {
			return true;
		}
		return false;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public SoulWarsUnit getUnitAt(int x, int y) {
		if (units[x][y] != null)
			return units[x][y];
		return null;
	}
	
	public CoordinatePair<Integer, Integer> getUnitMapLoc(SoulWarsUnit target) {
		float unitXf = target.getPos().getX();
		float unitYf = target.getPos().getY();
		int unitX = (int) unitXf;
		int unitY =  (int) unitYf;
		CoordinatePair<Integer,Integer> unitTileLoc = new CoordinatePair<Integer,Integer>(unitX,unitY);
		return unitTileLoc;
		
	}
	
	
	public void placeUnit(SoulWarsUnit unit) {
		CoordinatePair<Integer,Integer> unitLoc = getUnitMapLoc(unit);
		if(units[unitLoc.getX()][unitLoc.getY()] == null) {
			units[unitLoc.getX()][unitLoc.getY()] = unit;
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
