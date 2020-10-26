package soulwars;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import jig.ResourceManager;

public class MainMenuState extends BasicGameState {

	


	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		SoulWarsGame swg = (SoulWarsGame)game;
		swg.resetMap();
		swg.spawnPlayer((6*64)+32, (6*64)+32);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		SoulWarsGame swg = (SoulWarsGame)game;
		Image title;
		title = ResourceManager.getImage(SoulWarsGame.TITLE_RSC);
		title.draw(150,250);
		g.setColor(Color.white);
		g.drawString("press space to begin", swg.screenWidth/2 - 100, swg.screenHeight/2);
		g.setColor(Color.black);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		
		if(input.isKeyDown(Input.KEY_SPACE)) {
			game.enterState(SoulWarsGame.PLAYINGSTATE);
		}
	}


	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return SoulWarsGame.MAINMENUSTATE;
	}


}
