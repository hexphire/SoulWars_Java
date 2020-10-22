package soulwars;

import java.util.ArrayList;
import java.util.Stack;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.Path;

import jig.Collision;
import jig.Vector;

public class PlayingState extends BasicGameState {

	public SoulWarsCamera gameView;
	private ArrayList<SoulWarsUnit> selectedList;
	private Path testPath;
	private Rectangle selector;
	private SoulWarsGame swg;
	private Stack<Collision> collisions;
	private WizardCharacter player;
	
	private boolean dragged;
	int sMouseX = -1;
	int sMouseY = -1;
	int fMouseX = -1;
	int fMouseY = -1;
	
	
	
	

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		SoulWarsGame swg = (SoulWarsGame)game;
		gameView = new SoulWarsCamera(swg.gameMap);
		collisions = new Stack<Collision>();
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		swg = (SoulWarsGame)game;
		selectedList = new ArrayList<SoulWarsUnit>(100);
		//swg.gameMap.printMapArray();
		swg.spawnUnit((1 * 64)+32 , (1 * 64)+32, 0, 0);
		swg.spawnPlayer((2*64)+32, (2*64)+32);
		//selected = swg.gameMap.getUnit(0,0);
		player = swg.gameMap.getPlayer();
	}
		
		


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
		//render map using camera
		gameView.renderView(0, 0, g);
		
		//lets test getting map info from our tiledMap
		//g.drawString("map size: "+swg.map.getWidth()+"x "+swg.map.getHeight()+"y", 30, 30);
		//g.drawString("tile size: "+swg.map.getTileWidth()+"x "+swg.map.getTileHeight()+"y", 30, 60);
			
		if(selectedList != null) {
			gameView.renderSelected(selectedList, g);
		}
		if(selector != null) {
			if(dragged)
				g.draw(selector);
		}
		
		gameView.renderPlayer(g);
		
	}
	//need to overwrite the mouselistener methods of this state, to handle drag select.
	@Override
	public void mousePressed(int button, int x, int y) {
		if(button == 0) {
			selectedList.clear();
			sMouseX = x;
			sMouseY = y;
			dragged = true;
		}
	}
	@Override
	public void mouseReleased(int button, int x, int y) {
		if(button == 0) {
			System.out.println("x: " + sMouseX/64 + "y: " + sMouseY/64);
			System.out.println("x: " + fMouseX/64 + "y: " + fMouseY/64);
			dragged = false;
			if(selector != null)
				updateSelectedList();
		}
	}
	//need to handle the position of the origin and opposite corner, Rectangles with negative area render, but don't actually exist #JustJavathings.
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		fMouseX = newx;
		fMouseY = newy;
		if(dragged) {
			if(sMouseX > fMouseX && sMouseY > fMouseY) {
				selector = new Rectangle(fMouseX, fMouseY, (sMouseX - fMouseX), (sMouseY - fMouseY));
			}else if(sMouseX > fMouseX && sMouseY < fMouseY) {
				selector = new Rectangle(fMouseX, sMouseY, (sMouseX - fMouseX), (fMouseY - sMouseY));
			}else if(sMouseX < fMouseX && sMouseY < fMouseY){
				selector = new Rectangle(sMouseX, sMouseY, (fMouseX - sMouseX), (fMouseY - sMouseY));
			}else {
				selector = new Rectangle(sMouseX, fMouseY, (fMouseX - sMouseX), (sMouseY - fMouseY));
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		// TODO Auto-generated method stub
		
		
		Input input = container.getInput();
		float mouseTileX;
		float mouseTileY;
		
		
		
	
		//Path testing
		
		//unit test controls
		
		
		if(player != null){
			if (input.isKeyDown(Input.KEY_UP)) {
				player.translate(new Vector(0, -2f));
			}
			if (input.isKeyDown(Input.KEY_LEFT)) {
				player.translate(new Vector(-2f, 0));
			}
		
			if (input.isKeyDown(Input.KEY_DOWN)) {
				player.translate(new Vector(0, 2f));
			}
		
			if (input.isKeyDown(Input.KEY_RIGHT)) {
				player.translate(new Vector(2f, 0));
			}
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
		
		
		
		if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			System.out.println("attempting to path");
			if(selectedList != null) {
				mouseTileX = (input.getMouseX() / swg.gameMap.getTileWidth()) + gameView.getCameraX();
				mouseTileY = (input.getMouseY() / swg.gameMap.getTileHeight()) + gameView.getCameraY();
				swg.gameMap.clearVisited();
				for(SoulWarsUnit selected : selectedList) {
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
			selectedList.clear();
			mouseTileX = input.getMouseX();
			mouseTileY = input.getMouseY();
			
			float width = 16;
			float height = 16;
			selector = new Rectangle(mouseTileX - 8, mouseTileY - 8, width, height);
			for(SoulWarsUnit unit : swg.gameMap.getUnits()) {
				if(selector.contains(unit.getX() - (64 * gameView.getCameraX()), unit.getY() - (64 * gameView.getCameraY()))) {
						selectedList.add(unit);
						break;
				}
			}
				selector = null;
			
		
			
			//System.out.println("x: " + mouseTileX + "y: " + mouseTileY);
			//System.out.println("x: " + dMouseTileX + "y: " + dMouseTileY);
			SoulWarsTile[][] terrain = swg.gameMap.getTerrain();
			System.out.println("Tile:" + terrain[(int)((mouseTileX/64) + gameView.getCameraX())][(int)((mouseTileY/64) + gameView.getCameraY())].getType());
			
		}
		
		
		
		
		
		if (input.isKeyPressed(Input.KEY_LCONTROL)) {
				
				mouseTileX = (input.getMouseX());
				mouseTileY = (input.getMouseY());
				swg.spawnUnit(mouseTileX, mouseTileY, gameView.getCameraX(), gameView.getCameraY());
				
			
		}
		
				
		
		//Pathing test controls
		if(input.isKeyDown(Input.KEY_N)) {
			mouseTileX = (input.getMouseX() / swg.gameMap.getTileWidth()) + gameView.getCameraX();
			mouseTileY = (input.getMouseY() / swg.gameMap.getTileHeight()) + gameView.getCameraY();
			ArrayList<SoulWarsUnit> units = swg.gameMap.getUnits();
			for (SoulWarsUnit unit : units) {
					if(selectedList.size() == 1) {
						if(unit.getHash() != selectedList.get(0).getHash())
							if(swg.gameMap.getNear(unit, 5).size() != 0) {
								SoulWarsUnit target = swg.gameMap.getNear(unit, 5).get(0);
								unit.clearPath();
								unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY() , target.getMapPosX(), target.getMapPosY()));
					}
				}
			}
		}
		if(input.isKeyPressed(Input.KEY_C)) {
			mouseTileX = (input.getMouseX() / swg.gameMap.getTileWidth()) + gameView.getCameraX();
			mouseTileY = (input.getMouseY() / swg.gameMap.getTileHeight()) + gameView.getCameraY();
			ArrayList<SoulWarsUnit> units = swg.gameMap.getUnits();
			for (SoulWarsUnit unit : units) {
				unit.clearPath();
				unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY(), (int)mouseTileX, (int)mouseTileY));
			}
		}
		if(input.isKeyDown(Input.KEY_L)) {
			if(selectedList.size() == 1) {
				int[] coords = swg.gameMap.findUnit(selectedList.get(0));
				System.out.println("unit location x:"+ coords[0] +"y:"+ coords[1]);
			}
		}
		
		ArrayList<SoulWarsUnit> units = swg.gameMap.getUnits();
		if(units.size() != 0) {
			for(SoulWarsUnit unit : units) {
				for(SoulWarsTile tile : swg.gameMap.getCollideList()) {
					if(unit.collides(tile) != null) {
						unit.translate(unit.collides(tile).getMinPenetration().scale(delta/4));
					}
				}
				for(SoulWarsUnit collideCheck : units) {
					if(unit.getVelocity().getX() != 0 & unit.getVelocity().getY() != 0) {
						if(unit.getHash() != collideCheck.getHash()) {
							if(unit.collides(collideCheck) != null) {
								unit.translate(unit.collides(collideCheck).getMinPenetration());
							}
						}
					}
				}
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
	
	private void updateSelectedList() {
		for(SoulWarsUnit unit: swg.gameMap.getUnits()) {
			if(selector.contains(unit.getX() - (64 * gameView.getCameraX()), unit.getY() - (64 * gameView.getCameraY()))) {
				selectedList.add(unit);
			}
		}
		
	}


}
