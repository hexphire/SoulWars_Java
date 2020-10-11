package soulwars;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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
		if(testPath != null) {
			gameView.renderPath(testPath, g);
		}	
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
			selected.translate(new Vector(0, -.1f));
		}
		if (input.isKeyDown(Input.KEY_LEFT)) {
			selected.translate(new Vector(-.1f, 0));
		}
		
		if (input.isKeyDown(Input.KEY_DOWN)) {
			selected.translate(new Vector(0, .1f));
		}
		
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			selected.translate(new Vector(.1f, 0));
		}
		
		
		//Camera Controls
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
					testPath = swg.APather.findPath(null, swg.gameMap.getUnitMapLoc(selected).getX(), swg.gameMap.getUnitMapLoc(selected).getY(), (int)mouseTileX, (int)mouseTileY);
					if(testPath != null) {
						for (int i = 0; i < testPath.getLength(); i++) {
							System.out.println(testPath.getStep(i).toString());
						}
					}
					selected.setPath(testPath);
				}
			}
		}
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			float mouseTileX = (input.getMouseX() / swg.gameMap.getTileWidth()) + gameView.getCameraX();
			float mouseTileY = (input.getMouseY() / swg.gameMap.getTileHeight()) + gameView.getCameraY();
			if(swg.gameMap.getUnit((int) mouseTileX, (int)mouseTileY) != null) {
				System.out.println("someone here!");
				selected = swg.gameMap.getUnit((int) mouseTileX, (int)mouseTileY);
					
				
				
			}else {
				System.out.println("no one here!");
				
			}
			System.out.println("x: " + mouseTileX + "y: " + mouseTileY);	
			
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
		
		
		for (int xTile = 0; xTile < swg.gameMap.getWidthInTiles(); xTile++) {
			for (int yTile =0; yTile < swg.gameMap.getHeightInTiles(); yTile++) {
				SoulWarsUnit unitToUpdate = swg.gameMap.getUnit(xTile, yTile);
				if(unitToUpdate != null) {
					swg.gameMap.updateUnit(unitToUpdate);
					unitToUpdate.update(delta);
				}
			}
			
		}
		if(input.isKeyDown(Input.KEY_L)) {
			if(selected != null) {
				CoordinatePair<Integer,Integer> unitLoc = swg.gameMap.findUnit(selected);
				System.out.println("unit location x:"+ unitLoc.getX() +"y:"+ unitLoc.getY());
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
