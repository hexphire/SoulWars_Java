package soulwars;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.Path;

import jig.Vector;

public class PlayingState extends BasicGameState {

	public SoulWarsCamera gameView;
	private SoulWarsUnit selected;
	private Path testPath;
	private Rectangle selector;
	
	
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		SoulWarsGame swg = (SoulWarsGame)game;
		gameView = new SoulWarsCamera(swg.gameMap);
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		SoulWarsGame swg = (SoulWarsGame)game;
		//swg.gameMap.printMapArray();
		swg.spawnUnit((1 * 64)+32 , (1 * 64)+32, 0, 0);
		//selected = swg.gameMap.getUnit(0,0);
	}
		
		


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
		//render map using camera
		gameView.renderView(0, 0, g);
		
		//lets test getting map info from our tiledMap
		//g.drawString("map size: "+swg.map.getWidth()+"x "+swg.map.getHeight()+"y", 30, 30);
		//g.drawString("tile size: "+swg.map.getTileWidth()+"x "+swg.map.getTileHeight()+"y", 30, 60);
			
		if(selected != null) {
			gameView.renderSelected(selected, g);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		// TODO Auto-generated method stub
		
		SoulWarsGame swg = (SoulWarsGame)game;
		Input input = container.getInput();
		
		
	
		//Path testing
		
		//unit test controls
		
		
		if (input.isKeyDown(Input.KEY_UP)) {
			if(selected != null)
				selected.translate(new Vector(0, -2f));
		}
		if (input.isKeyDown(Input.KEY_LEFT)) {
			if(selected != null)
				selected.translate(new Vector(-2f, 0));
		}
		
		if (input.isKeyDown(Input.KEY_DOWN)) {
			if(selected != null)
				selected.translate(new Vector(0, 2f));
		}
		
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			if(selected != null)
				selected.translate(new Vector(2f, 0));
		}
		
		
		//Camera controls
		
		if (input.isKeyDown(Input.KEY_W)){
			gameView.moveCameraY(-1);
			
		}		
		if (input.isKeyDown(Input.KEY_S)){
			gameView.moveCameraY(1);
			
		}

		if (input.isKeyDown(Input.KEY_A)){
			gameView.moveCameraX(-1);
			
		}	
		if (input.isKeyDown(Input.KEY_D)){
			gameView.moveCameraX(1);
		}
		
		
		//Mouse Controls
		
		
		if (input.isKeyDown(Input.KEY_LSHIFT)) {
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				System.out.println("attempting to path");
				if(selected != null) {
					float mouseTileX = (input.getMouseX() / swg.gameMap.getTileWidth()) + gameView.getCameraX();
					float mouseTileY = (input.getMouseY() / swg.gameMap.getTileHeight()) + gameView.getCameraY();
					swg.gameMap.clearVisited();
					selected.clearPath();
					selected.update(delta);
					testPath = swg.APather.findPath(selected, selected.getMapPosX(), selected.getMapPosY(), (int)mouseTileX, (int)mouseTileY);
					if(testPath != null) {
						for (int i = 0; i < testPath.getLength(); i++) {
							System.out.println(testPath.getX(i) + "," + testPath.getY(i));
						}
					}
					selected.setPath(testPath);
				}
			}
		}
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			float mouseTileX = input.getMouseX();
			float mouseTileY = input.getMouseY();
			float dMouseTileX = mouseTileX;
			float dMouseTileY = mouseTileY;
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				float currentMouseX = input.getMouseX(); 
				float currentMouseY = input.getMouseY();
				if(mouseTileX < currentMouseX && mouseTileY < currentMouseY) {
					
				}
			}
			if(!input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				dMouseTileX = input.getMouseX();
				dMouseTileY = input.getMouseX();
			}
			
			if(Math.abs(mouseTileX) - Math.abs(dMouseTileX) < 10 && Math.abs(mouseTileY) - Math.abs(dMouseTileY) < 10) {
				float width = 16;
				float height = 16;
				selector = new Rectangle(mouseTileX - 5, mouseTileY - 5, width, height);
				for(SoulWarsUnit unit : swg.gameMap.getUnits()) {
					if(selector.contains(unit.getX() - (64 * gameView.getCameraX()),unit.getY() - (64 * gameView.getCameraY()))) {
						selected = unit;
						break;
					}
				}
				selector = null;				
			}
			
			System.out.println("x: " + mouseTileX + "y: " + mouseTileY);
			int[][] terrain = swg.gameMap.getTerrain();
			System.out.println("Tile:" + terrain[(int)((mouseTileX/64) + gameView.getCameraX())][(int)((mouseTileY/64) + gameView.getCameraY())]);
			
		}
		
		
		
		if (input.isKeyDown(Input.KEY_LCONTROL)) {
				if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
					float mouseTileX = (input.getMouseX());
					float mouseTileY = (input.getMouseY());
					swg.spawnUnit(mouseTileX, mouseTileY, gameView.getCameraX(), gameView.getCameraY());
				}
			
		}
		
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
				
			selected = null;
			System.out.println("selected should be clear");
		}
		
		
		
		
		//Pathing test controls
		if(input.isKeyDown(Input.KEY_N)) {
			float mouseTileX = (input.getMouseX() / swg.gameMap.getTileWidth()) + gameView.getCameraX();
			float mouseTileY = (input.getMouseY() / swg.gameMap.getTileHeight()) + gameView.getCameraY();
			ArrayList<SoulWarsUnit> units = swg.gameMap.getUnits();
			for (SoulWarsUnit unit : units) {
					if(selected != null) {
						if(unit.getHash() != selected.getHash())
							if(swg.gameMap.getNear(unit, 5).size() != 0) {
								SoulWarsUnit target = swg.gameMap.getNear(unit, 5).get(0);
								unit.clearPath();
								unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY() , target.getMapPosX(), target.getMapPosY()));
					}
				}
			}
		}
		if(input.isKeyPressed(Input.KEY_C)) {
			float mouseTileX = (input.getMouseX() / swg.gameMap.getTileWidth()) + gameView.getCameraX();
			float mouseTileY = (input.getMouseY() / swg.gameMap.getTileHeight()) + gameView.getCameraY();
			ArrayList<SoulWarsUnit> units = swg.gameMap.getUnits();
			for (SoulWarsUnit unit : units) {
				unit.clearPath();
				unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY(), (int)mouseTileX, (int)mouseTileY));
			}
		}
		if(input.isKeyDown(Input.KEY_L)) {
			if(selected != null) {
				int[] coords = swg.gameMap.findUnit(selected);
				System.out.println("unit location x:"+ coords[0] +"y:"+ coords[1]);
			}
		}
		
		ArrayList<SoulWarsUnit> units = swg.gameMap.getUnits();
		if(units.size() != 0) {
			for(SoulWarsUnit unit : units) {
				unit.update(delta);
			}
		}
	}

	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return SoulWarsGame.PLAYINGSTATE;
	}


}
