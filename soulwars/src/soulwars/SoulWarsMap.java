package soulwars;

import java.util.ArrayList;
import java.util.Arrays;


import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import jig.ResourceManager;



public class SoulWarsMap implements TileBasedMap{
	
	private int mapWidth;
	private int mapHeight;
	
	private int tileWidth;
	private int tileHeight;
	
	private SoulWarsTile[][] terrainTiles;
	private ArrayList<SoulWarsTile> collidables;
	private ArrayList<SoulWarsTile> tileList;
	private ArrayList<SoulWarsUnit> units;
	private WizardCharacter player;
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
	
	public WizardCharacter getPlayer() {
		return player;
	}
	
	public void setPlayer(WizardCharacter player) {
		this.player = player; 
	}

	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		// TODO Auto-generated method stub
		if(terrainTiles[tx][ty].getBlocked()) {
			return true;
		}
		
		return false;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		// TODO Auto-generated method stub
		if(terrainTiles[tx][ty].getBlocked()) {
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
			if (possible.getMapPosX() > (unit.getMapPosX()-(range))) {
				if(possible.getMapPosX() < (unit.getMapPosX()+(range))) {
					if(possible.getMapPosY() > (unit.getMapPosY()-(range))) {
						if(possible.getMapPosX() < (unit.getMapPosY()+ (range))) {
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
	
	public Boolean isPlayerNear(SoulWarsUnit unit, int range){
			if (player.getMapPosX() > (unit.getMapPosX()-(range))) {
				if(player.getMapPosX() < (unit.getMapPosX()+(range))) {
					if(player.getMapPosY() > (unit.getMapPosY()-(range))) {
						if(player.getMapPosX() < (unit.getMapPosY()+ (range))) {
							return true;
							
						}
					}
				}
			}
	return false;
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
	
	
	public ArrayList<SoulWarsTile> getCollideList() {
		return collidables;	
	}
	
	public ArrayList<SoulWarsTile> getTileList(){
		return tileList;
	}
	
	
	public SoulWarsTile[][] getTerrain(){
		return terrainTiles;
	}
	
	public void loadNewMap(TiledMap mapPlan) {
		mapWidth = mapPlan.getWidth();
		mapHeight = mapPlan.getHeight();
		
		tileWidth = mapPlan.getTileWidth();
		tileHeight = mapPlan.getTileHeight();
		
		terrainTiles = new SoulWarsTile[mapWidth][mapHeight];
		collidables = new ArrayList<SoulWarsTile>(mapWidth * mapHeight);
		units = new ArrayList<SoulWarsUnit>(mapWidth * mapHeight);
		tileList = new ArrayList<SoulWarsTile>(mapWidth * mapHeight);
		visited = new boolean[mapWidth][mapHeight];
		redBasePath = new int[mapWidth][mapHeight];
		blueBasePath = new int[mapWidth][mapHeight];
		
		for (int xTile = 0; xTile < mapWidth; xTile++) {
			for (int yTile =0; yTile < mapHeight; yTile++) {
				SoulWarsTile tile = new SoulWarsTile(xTile*64, yTile*64, (mapPlan.getTileId(xTile, yTile, 0)));
				tileList.add(tile);
				terrainTiles[xTile][yTile] = tile;
				if(mapPlan.getTileId(xTile, yTile, 0) == 59 || mapPlan.getTileId(xTile, yTile, 0) == 60){
					collidables.add(tile);
				}
			}
		}
		
		
	}
	
	public void printMapArray() {
		System.out.println(mapWidth);
		System.out.println(mapHeight);
		System.out.println(Arrays.deepToString(terrainTiles).replace("],", "]\n"));
	}

}
