package soulwars;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;


import java.util.Random;


public class SoulWarsGame extends StateBasedGame {
	
	
	//State Identitifiers
	public static final int SPLASHSCREENSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int PLAYINGSTATE = 2;
	public static final int GAMEOVERSTATE = 3;
	
	//Tile Identifiers
	public static final String TILE_RSC_59 = "soulwars/resources/medievalTile_15.png";
	public static final String TILE_RSC_101 = "soulwars/resources/medievalTile_57.png";
	public static final String UNIT_RSC_REDW = "soulwars/resources/medievalUnit_09.png";
	public static final String UNIT_RSC_BLUW = "soulwars/resources/medievalUnit_03.png";
	public static final String CHAR_RSC_MAIN = "soulwars/resources/necromancerSheet.png";
	public static final String UNIT_RSC_WDMN = "soulwars/resources/medievalUnit_18.png";
	public static final String CHAR_RSC_ATK = "soulwars/resources/necroattackSheet.png";
	public static final String SPELL_RSC_FRBL = "soulwars/resources/fireballsheet.png";
	public static final String TWR_ARW_RSC = "soulwars/resources/TowerArrow.png";
	public static final String SOUL_RSC_MAIN = "soulwars/resources/sSoul.png";
	public static final String HQ_TOP_RSC = "soulwars/resources/medievalStructure_02.png";
	public static final String HQ_BOTTOM_RSC = "soulwars/resources/medievalStructure_06.png";
	public static final String HQ_PLAYER_RSC = "soulwars/resources/pyramidMayan.png";
	public static final String HQ_TOWER_RSC = "soulwars/resources/towerSmallAlt.png";
	public static final String UI_BACK_RSC = "soulwars/resources/UI.png";
	public static final String UI_WASD_RSC = "soulwars/resources/wasd.png";
	public static final String UI_LMOUSE_RSC = "soulwars/resources/Mouse_Left_Key_Light.png";
	public static final String UI_RMOUSE_RSC = "soulwars/resources/Mouse_Right_Key_Light.png";
	public static final String UI_EKEY_RSC = "soulwars/resources/E_Key_Light.png";
	public static final String UI_QKEY_RSC = "soulwars/resources/Q_Key_Light.png";
	public static final String UI_1KEY_RSC = "soulwars/resources/1_Key_Light.png";
	public static final String UI_2KEY_RSC = "soulwars/resources/2_Key_Light.png";
	public static final String UI_3KEY_RSC = "soulwars/resources/3_Key_Light.png";
	public static final String UI_4KEY_RSC = "soulwars/resources/4_Key_Light.png";
	
	
	//App properties
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	public static final int FPS = 60;
	public static final double VERSION = .05;
	public boolean Debug = false;
	
	
	
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
		addState(new GameOverState());
		ResourceManager.setFilterMethod(ResourceManager.FILTER_LINEAR);
		ResourceManager.loadImage(TILE_RSC_59);
		ResourceManager.loadImage(TILE_RSC_101);
		ResourceManager.loadImage(UNIT_RSC_REDW);
		ResourceManager.loadImage(UNIT_RSC_BLUW);
		ResourceManager.loadImage(CHAR_RSC_MAIN);
		ResourceManager.loadImage(CHAR_RSC_ATK);
		ResourceManager.loadImage(SPELL_RSC_FRBL);
		ResourceManager.loadImage(TWR_ARW_RSC);
		ResourceManager.loadImage(SOUL_RSC_MAIN);
		ResourceManager.loadImage(HQ_TOP_RSC);
		ResourceManager.loadImage(HQ_BOTTOM_RSC);
		ResourceManager.loadImage(HQ_PLAYER_RSC);
		ResourceManager.loadImage(HQ_TOWER_RSC);
		ResourceManager.loadImage(UI_BACK_RSC);
		ResourceManager.loadImage(UI_WASD_RSC);
		ResourceManager.loadImage(UI_LMOUSE_RSC);
		ResourceManager.loadImage(UI_RMOUSE_RSC);
		ResourceManager.loadImage(UI_EKEY_RSC);
		ResourceManager.loadImage(UI_QKEY_RSC);
		ResourceManager.loadImage(UI_1KEY_RSC);
		ResourceManager.loadImage(UI_2KEY_RSC);
		ResourceManager.loadImage(UI_3KEY_RSC);
		ResourceManager.loadImage(UI_4KEY_RSC);
		ResourceManager.loadImage(UNIT_RSC_WDMN);
		
		
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
	
	public void spawnUnit(float x, float y, int type, int team, int group) {
		SoulWarsUnit newUnit = new SoulWarsUnit(x, y, 1, team, group);
		gameMap.placeUnit(newUnit);
	}
	
	public void spawnPlayer(float x, float y) throws SlickException {
		WizardCharacter player = new WizardCharacter(x,y);
		gameMap.setPlayer(player);
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
	
	public void resetMap() {
		gameMap.loadNewMap(map);
	}
	
	
}

