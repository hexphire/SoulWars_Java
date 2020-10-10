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
		//swg.spawnUnit(1 * 64 , 1 * 64, 0, 0);
		selected = swg.gameMap.getUnit(0,0);
	}
		
		


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
		//render map using camera
		gameView.renderView(0, 0, g);
		
		//lets test getting map info from our tiledMap
		//g.drawString("map size: "+swg.map.getWidth()+"x "+swg.map.getHeight()+"y", 30, 30);
		//g.drawString("tile size: "+swg.map.getTileWidth()+"x "+swg.map.getTileHeight()+"y", 30, 60);
		if(testPath != null)
			gameView.renderPath(testPath, g);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		// TODO Auto-generated method stub
		
		SoulWarsGame swg = (SoulWarsGame)game;
		Input input = container.getInput();
		//unit test controls
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			selected.translate(new Vector(64, 0));
			selected.translate(new Vector(64, 0));
			selected.translate(new Vector(0, 64));
			selected.translate(new Vector(64, 0));
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
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			float mouseTileX = (input.getMouseX() / swg.gameMap.getTileWidth()) + gameView.getCameraX();
			float mouseTileY = (input.getMouseY() / swg.gameMap.getTileHeight()) + gameView.getCameraY();
			if(swg.gameMap.getUnit((int) mouseTileX, (int)mouseTileY) != null) {
				System.out.println("someone here!");
			}else {
				System.out.println("no one here!");
			}
			System.out.println("x: " + mouseTileX + "y: " + mouseTileY);	
			testPath = swg.APather.findPath(null, 1, 1, (int)mouseTileX, (int)mouseTileY);
		}
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON) && input.isKeyPressed(Input.KEY_LCONTROL)) {
			float mouseTileX = (input.getMouseX());
			float mouseTileY = (input.getMouseY());
			swg.spawnUnit(mouseTileX, mouseTileY, gameView.getCameraX(), gameView.getCameraY());	
			
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
