package soulwars;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;


public class SoulWarsGame extends StateBasedGame {
	
	
	//State Identitifiers
	public static final int SPLASHSCREENSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int PLAYINGSTATE = 2;
	
	//App properties
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	public static final int FPS = 60;
	public static final double VERSION = .01;
	
	
	public final int screenWidth;
	public final int screenHeight;
	
	private boolean mapReady;
	
	public TiledMap map;
	
	
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartSplashState());
		addState(new MainMenuState());
		addState(new PlayingState());
		
	}
	
	public SoulWarsGame(String title, int width, int height) throws SlickException {
		super(title);
		screenWidth = width;
		screenHeight = height;
		mapReady = false;
		loadMap();
		if(mapReady == true) {
			System.out.println("map loaded");
		}
		
		
		
		
	}
	
	public boolean mapCheck() {
		return mapReady;
	}
	
	public void loadMap() throws SlickException {
		map = new TiledMap("src/soulwars/resources/smallTestmap64px.tmx", false);
		mapReady = true;
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

