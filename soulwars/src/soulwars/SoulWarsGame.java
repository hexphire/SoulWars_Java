package soulwars;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import java.util.Random;


public class SoulWarsGame extends StateBasedGame {
	
	
	//State Identitifiers
	public static final int SPLASHSCREENSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int PLAYINGSTATE = 2;
	
	//Tile Identifiers
	public static final String TILE_RSC_59 = "soulwars/resources/Tile/medievalTile_15.png";
	public static final String TILE_RSC_101 = "soulwars/resources/Tile/medievalTile_57.png";
	public static final String UNIT_RSC_REDW = "soulwars/resources/Unit/medievalUnit_09.png";
	//App properties
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	public static final int FPS = 60;
	public static final double VERSION = .05;
	
	
	
	public final int screenWidth;
	public final int screenHeight;
	
	private boolean mapReady;
	
	//map variables
	public SoulWarsMap gameMap;
	
	public AStarPathFinder APather;
	
	public TiledMap map;
	
	private Random rndm;
	
	
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartSplashState());
		addState(new MainMenuState());
		addState(new PlayingState());
		ResourceManager.setFilterMethod(ResourceManager.FILTER_LINEAR);
		ResourceManager.loadImage(TILE_RSC_59);
		ResourceManager.loadImage(TILE_RSC_101);
		ResourceManager.loadImage(UNIT_RSC_REDW);
		
		rndm = new Random();
		Entity.antiAliasing = false;
		Entity.setCoarseGrainedCollisionBoundary(Entity.CIRCLE);
		mapReady = false;
		gameMap = new SoulWarsMap();		
		loadMap();
		if(mapReady == true) {
			System.out.println("map loading");
			gameMap.loadNewMap(map);
			APather = new AStarPathFinder(gameMap, 500, true, new ManhattanHeuristic());
		}
	}
	
	public SoulWarsGame(String title, int width, int height) throws SlickException {
		super(title);
		screenWidth = width;
		screenHeight = height;
	}
	
	public void spawnUnit(float x, float y, int cameraX, int cameraY) {
		SoulWarsUnit newUnit = new SoulWarsUnit(x, y, 1);
		gameMap.placeUnit(newUnit);
	}
	
	public boolean mapCheck() {
		return mapReady;
	}
	
	public void loadMap() throws SlickException {
		map = new TiledMap("src/soulwars/resources/smallTestmap64px.tmx", false);
		mapReady = true;
	}
	
	public int getRandom(int ceiling) {
		int randint = rndm.nextInt(ceiling);
		return randint;
	}
	
	
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new SoulWarsGame("SoulWars " + VERSION, WIDTH, HEIGHT));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setTargetFrameRate(FPS);
			app.setShowFPS(true);
			app.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	
}

