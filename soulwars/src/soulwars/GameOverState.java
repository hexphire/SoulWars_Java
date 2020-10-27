package soulwars;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import jig.ResourceManager;

public class GameOverState extends BasicGameState{
	
	private int gameEndState;
	private int timeOut;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		gameEndState = 0;
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		timeOut = 3000;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		SoulWarsGame swg = (SoulWarsGame)game;
		Image gameOver;
		gameOver = ResourceManager.getImage(SoulWarsGame.GAMEOVER_RSC);
		gameOver.draw(100,250);
		if(gameEndState == 1) {
			g.setColor(Color.white);
			g.drawString("You Win!", swg.screenWidth/2 - 50, swg.screenHeight/2);
			g.setColor(Color.black);
		}
		if(gameEndState == 2) {
			g.setColor(Color.white);
			g.drawString("You Lose!", swg.screenWidth/2 - 50, swg.screenHeight/2);
			g.setColor(Color.black);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		if(timeOut > 0) {
			timeOut -= delta;
		}
		if(timeOut <= 0) {
			game.enterState(SoulWarsGame.MAINMENUSTATE, new EmptyTransition(), new HorizontalSplitTransition());
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return SoulWarsGame.GAMEOVERSTATE;
	}
	
	public void setEndState(int state) {
		gameEndState = state;
	}

}
