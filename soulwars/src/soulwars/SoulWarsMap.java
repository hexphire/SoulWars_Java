package soulwars;

import java.util.ArrayList;
import java.util.Arrays;


import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import jig.Entity;
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
	private ArrayList<Projectile> projectiles;
	private ArrayList<SoulWarsSoul> souls;
	private SoulWarsHQ playerHQ;
	private SoulWarsHQ enemyHQ;
	private SoulWarsHQ enemyHQTower1;
	private SoulWarsHQ enemyHQTower2; 
	private SoulWarsHQ enemyEastTower;
	private SoulWarsHQ enemySouthTower;
	
	private WizardCharacter player;
	private boolean[][] visited;
	

	
	
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
	
	public void addSoul(SoulWarsSoul soul) {
		souls.add(soul);
	}
	
	public ArrayList<SoulWarsSoul> getSoulList() {
		return souls;
	}
	
	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}
	
	public void removeProjectile(Projectile target) {
		projectiles.remove(target);
	}
	
	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}
	
	public WizardCharacter getPlayer() {
		return player;
	}
	
	public ArrayList<SoulWarsHQ> getHQs() {
		ArrayList<SoulWarsHQ> HQList = new ArrayList<SoulWarsHQ>(6);
		HQList.add(playerHQ);
		HQList.add(enemyHQ);
		HQList.add(enemyHQTower1);
		HQList.add(enemyHQTower2);
		HQList.add(enemyEastTower);
		HQList.add(enemySouthTower);
		
		
		return HQList;
	}
	
	public SoulWarsHQ getPlayerHQ() {
		// TODO Auto-generated method stub
		return playerHQ;
	}
	public SoulWarsHQ getEnemyEastTower() {
		// TODO Auto-generated method stub
		return enemyEastTower;
	}
	public SoulWarsHQ getEnemySouthTower() {
		// TODO Auto-generated method stub
		return enemySouthTower;
	}	
	
	public SoulWarsHQ getEnemyHQ() {
		return enemyHQ;
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
		projectiles = new ArrayList<Projectile>(mapWidth * mapHeight);
		souls = new ArrayList<SoulWarsSoul>(mapWidth * mapHeight);
		visited = new boolean[mapWidth][mapHeight];
		playerHQ = new SoulWarsHQ((2*tileWidth) + 32,(2*tileHeight) + 32, 0, 0);
		enemyHQ = new SoulWarsHQ((mapWidth-2)*tileWidth, (mapHeight -2 )*tileHeight, 1, 1);
		enemyHQTower1 = new SoulWarsHQ((mapWidth - 9)*tileWidth, (mapHeight - 7)*tileHeight, 1, 2);
		enemyHQTower2 = new SoulWarsHQ(((mapWidth- 6)*tileWidth)-20, (mapHeight - 9)*tileHeight, 1, 2);
		enemyEastTower = new SoulWarsHQ(((mapWidth - 4)*tileWidth)-32, ((mapHeight/2)-1)*tileHeight, 1, 2);
		enemySouthTower = new SoulWarsHQ((((mapWidth/2) - 1)*tileWidth)-32, (mapHeight - 5)*tileHeight, 1, 2);

		
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
		
		units.add(new SoulWarsUnit(4*tileWidth,4*tileHeight, 1, 0, 0));
		units.add(new SoulWarsUnit((4*tileWidth)-16,(3*tileHeight)+16, 1, 0, 0));
		units.add(new SoulWarsUnit((3*tileWidth)+16,(4*tileHeight)+16, 1, 0, 0));
		
		
		
	}
	
	public void printMapArray() {
		System.out.println(mapWidth);
		System.out.println(mapHeight);
		System.out.println(Arrays.deepToString(terrainTiles).replace("],", "]\n"));
	}

	

}
