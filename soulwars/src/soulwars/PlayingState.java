package soulwars;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class PlayingState extends BasicGameState {

	private int pOneCameraX;
	private int pOneCameraY;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		pOneCameraX = 0;
		pOneCameraY = 0;
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		SoulWarsGame swg = (SoulWarsGame)game;
		swg.gameMap.printMapArray();
		
	}
		
		


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		SoulWarsGame swg = (SoulWarsGame)game;
		//lets test getting map info from our tiledMap
		g.drawString("map size: "+swg.map.getWidth()+"x "+swg.map.getHeight()+"y", 30, 30);
		g.drawString("tile size: "+swg.map.getTileWidth()+"x "+swg.map.getTileHeight()+"y", 30, 60);
		swg.gameMap.renderView(0, 0, pOneCameraX, pOneCameraY, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		SoulWarsGame swg = (SoulWarsGame)game;
		Input input = container.getInput();
		//Camera Controls for player one
		if (input.isKeyDown(Input.KEY_W)){
			if(pOneCameraY > 0)
				pOneCameraY -= 1;
			
		}		
		if (input.isKeyDown(Input.KEY_S)){
			if(pOneCameraY < swg.gameMap.getHeightInTiles() - 10)
				pOneCameraY += 1;
			
		}

		if (input.isKeyDown(Input.KEY_A)){
			if(pOneCameraX > 0)
				pOneCameraX -= 1;
			
		}
		if (input.isKeyDown(Input.KEY_D)){
			if(pOneCameraX < swg.gameMap.getWidthInTiles() - 10)
				pOneCameraX += 1;
			
		}
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			float mouseX = (input.getMouseX() / 64) + pOneCameraX;
			float mouseY = (input.getMouseY() / 64) + pOneCameraY;
			System.out.println("x: " + mouseX + "y: " + mouseY);	
			
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
