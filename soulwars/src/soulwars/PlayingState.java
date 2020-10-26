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
import jig.Entity;
import jig.Vector;

public class PlayingState extends BasicGameState {

	public SoulWarsCamera gameView;
	private ArrayList<SoulWarsUnit> selectedList;
	private Path pathToTarget;
	private Rectangle selector;
	private SoulWarsGame swg;
	private Stack<Collision> collisions;
	private WizardCharacter player;
	private Vector prevPlayerPostion;
	private boolean playerPosChange;
	private boolean unitsFollow;
	private boolean unitsDismiss;
	private boolean unitsAttack;
	private boolean currentSpellFireBall;
	private boolean currentSpellHaste;
	private boolean currentSpellHeal;
	private boolean currentSpellSummon;
	
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
		unitsFollow = true;
		unitsDismiss = false;
		unitsAttack = false;
		currentSpellFireBall = true;
		currentSpellHaste = false;
		currentSpellHeal = false;
		currentSpellSummon = false;
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		swg = (SoulWarsGame)game;
		selectedList = new ArrayList<SoulWarsUnit>(100);
		//swg.gameMap.printMapArray();
		swg.spawnPlayer((6*64)+32, (6*64)+32);
		//selected = swg.gameMap.getUnit(0,0);
		player = swg.gameMap.getPlayer();
		gameView.resetCamera();
	}
		
		


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		int spell = -1;
		if(currentSpellFireBall) {
			spell = 0;
		}else if(currentSpellHaste) {
			spell = 1;
		}else if(currentSpellHeal) {
			spell = 2;
		}else if(currentSpellSummon) {
			spell = 3;
		}
		//render map using camera
		gameView.renderView(0, 0, g);
		gameView.renderSelectedSpell(g, spell);
		
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
		

		
	}
	//need to overwrite the mouselistener methods of this state, to handle drag select.
	@Override
	public void mousePressed(int button, int x, int y) {
		if(swg.Debug) {
			if(button == 0) {
				selectedList.clear();
				sMouseX = x;
				sMouseY = y;
				dragged = true;
			}
		}
	}
	@Override
	public void mouseReleased(int button, int x, int y) {
		if(swg.Debug) {
			if(button == 0) {
				System.out.println("x: " + sMouseX/64 + "y: " + sMouseY/64);
				System.out.println("x: " + fMouseX/64 + "y: " + fMouseY/64);
				dragged = false;
				if(selector != null)
					updateSelectedList();
			}
		}
	}
	//need to handle the position of the origin and opposite corner, Rectangles with negative area render, but don't actually exist #JustJavathings.
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(swg.Debug) {
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
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		// TODO Auto-generated method stub
		
		
		Input input = container.getInput();
		float mouseTileX;
		float mouseTileY;
		ArrayList<SoulWarsSoul> soulList = swg.gameMap.getSoulList();
		ArrayList<SoulWarsSoul> collectedSoul = new ArrayList<SoulWarsSoul>(100);
		ArrayList<Projectile> projectiles = swg.gameMap.getProjectiles();
		ArrayList<SoulWarsUnit> units = swg.gameMap.getUnits();
		ArrayList<Projectile> deadProject = new ArrayList<Projectile>(projectiles.size());
		ArrayList<SoulWarsUnit> deadUnit = new ArrayList<SoulWarsUnit>(units.size());
		
		
	
		
			
		if(player != null){
			if (input.isKeyDown(Input.KEY_W)) {
				player.translate(new Vector(0, -2f));
				if (player.getY()-(gameView.getCameraY()*64) < 512 && gameView.getCameraY() > 0) {
					gameView.moveCameraY(-1, delta);
				}	
			}
			if (input.isKeyDown(Input.KEY_A)) {
				player.translate(new Vector(-2f, 0));
				if (player.getX()-(gameView.getCameraX()*64) < 512 && gameView.getCameraX() > 0) {
					gameView.moveCameraX(-1, delta);
				}
			}
		
			if (input.isKeyDown(Input.KEY_S)) {
				player.translate(new Vector(0, 2f));
				if (player.getY()-(gameView.getCameraY()*64) > 512 && gameView.getCameraY() < swg.gameMap.getHeightInTiles()) {
					gameView.moveCameraY(1, delta);
				}
			}
		
			if (input.isKeyDown(Input.KEY_D)) {
				player.translate(new Vector(2f, 0));
				if (player.getX()-(gameView.getCameraX()*64) > 512 && gameView.getCameraX() < swg.gameMap.getWidthInTiles()) {
					gameView.moveCameraX(1, delta);
				}
			}
		}
		
		if(input.isKeyPressed(Input.KEY_1)) {
			currentSpellFireBall = true;
			currentSpellHaste = false;
			currentSpellHeal = false;
			currentSpellSummon = false;
		}
		if(input.isKeyPressed(Input.KEY_2)) {
			currentSpellFireBall = false;
			currentSpellHaste = true;
			currentSpellHeal = false;
			currentSpellSummon = false;
		}
		if(input.isKeyPressed(Input.KEY_3)) {
			currentSpellFireBall = false;
			currentSpellHaste = false;
			currentSpellHeal = true;
			currentSpellSummon = false;
		}
		if(input.isKeyPressed(Input.KEY_4)) {
			currentSpellFireBall = false;
			currentSpellHaste = false;
			currentSpellHeal = false;
			currentSpellSummon = true;
		}
		
		//Camera controls
		if (input.isKeyDown(Input.KEY_UP)){
			gameView.moveCameraY(-1, delta*2);
			
		}	
		if (input.isKeyDown(Input.KEY_DOWN)){
			gameView.moveCameraY(1, delta*2);
		}	
		

		if (input.isKeyDown(Input.KEY_LEFT)){
			gameView.moveCameraX(-1, delta*2);
			
		}	
		if (input.isKeyDown(Input.KEY_RIGHT)){
			gameView.moveCameraX(1, delta*2);
		}
		
		
		//Mouse Controls
		
		
		
		if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			System.out.println("attempting to path");
			if(units != null) {
				mouseTileX = (input.getMouseX() / swg.gameMap.getTileWidth()) + gameView.getCameraX();
				mouseTileY = (input.getMouseY() / swg.gameMap.getTileHeight()) + gameView.getCameraY();
				for(SoulWarsUnit unit : units) {
					if(unit.getTeam() == 0) {
						if(unit.getCurrentTarget() == null) {
							unitsFollow = false;
							unitsDismiss = false;
							unitsAttack = true;
							swg.gameMap.clearVisited();
							unit.clearPath();
							unit.update(delta);
							pathToTarget = swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY(), (int)mouseTileX, (int)mouseTileY);
							if(pathToTarget != null) {
								for (int i = 0; i < pathToTarget.getLength(); i++) {
									System.out.println(pathToTarget.getX(i) + "," + pathToTarget.getY(i));
								}
							}
							unit.setPath(pathToTarget);
						}
					}	
				}
			}
		}
		
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(currentSpellFireBall) {
				if(player.fireballCooldownCheck()) {
					if(player.castFireball()) {
						Vector mouseVec = new Vector(input.getMouseX()+ gameView.getCameraX()*64 ,input.getMouseY() + gameView.getCameraY()*64);
						Vector playerPos = player.getPosition();
						double shotAngle = playerPos.angleTo(mouseVec);
						Projectile fireball = new Projectile(playerPos.getX() + 2 ,playerPos.getY() + 2, Vector.getVector(shotAngle, 2f), 1);
						fireball.rotate(180);
						fireball.rotate(shotAngle);
						swg.gameMap.addProjectile(fireball);
					}
				}
			}
			if(currentSpellHaste) {
				if(player.hasteCooldownCheck()) {
					if(player.castHaste()) {
						for(SoulWarsUnit unit : units) {
							if(unit.getTeam() == 0) {
								unit.underHaste();
							}
						}
					}
				}
			}
			if(currentSpellHeal) {
				if(player.healCooldownCheck()) {
					if(player.castHeal()){
						for(SoulWarsUnit unit : units) {
							if(unit.getTeam() == 0) {
								unit.heal(unit.getMaxHealth());
							}
						}
					}
				}
			}
			if(currentSpellSummon) {
				if(player.summonCooldownCheck()) {
					if(player.summonUnit()) {
						mouseTileX = (input.getMouseX() + gameView.getCameraX()*swg.gameMap.getTileWidth());
						mouseTileY = (input.getMouseY() + gameView.getCameraY()*swg.gameMap.getTileHeight());
						swg.spawnUnit(mouseTileX, mouseTileY, 1, 0, 0);												
					}
				}
			}
			
			if(swg.Debug) {
				mouseTileX = input.getMouseX();
				mouseTileY = input.getMouseY();

				
				
				
			
				
				//System.out.println("x: " + mouseTileX + "y: " + mouseTileY);
				//System.out.println("x: " + dMouseTileX + "y: " + dMouseTileY);
				SoulWarsTile[][] terrain = swg.gameMap.getTerrain();
				System.out.println("Tile:" + terrain[(int)((mouseTileX/64) + gameView.getCameraX())][(int)((mouseTileY/64) + gameView.getCameraY())].getType());
			}
			
			
		}
		
		
		
		
		
		if(input.isKeyPressed(Input.KEY_E)) {
			unitsDismiss = false;
			unitsAttack = false;
			unitsFollow = true;
		}
		
		if(input.isKeyPressed(Input.KEY_Q)) {
			unitsAttack = false;
			unitsFollow = false;
			unitsDismiss = true;
		}
		
		//test controls
		if(input.isKeyDown(Input.KEY_LCONTROL)) {
			if(input.isKeyPressed(Input.KEY_INSERT)) {
				if(swg.Debug == false) {
					swg.Debug = true; 
				}else {
					swg.Debug = false;
				}
				if(gameView.Debug == false) {
					gameView.Debug = true;
				}else {
					gameView.Debug = false;
				}
			}
		}
		
		if(input.isKeyPressed(Input.KEY_END)) {
			if(swg.Debug == true) {
				swg.gameMap.clearEnemyHQ();
			}
		}
		
		if(swg.gameMap.getEnemyHQ() != null) {
			if(player.getPosition().epsilonEquals(swg.gameMap.getEnemyHQ().getPosition(), 190)) {
				if(swg.gameMap.getEnemyHQ().attackCooldownCheck()) {
					Vector thisVec = swg.gameMap.getEnemyHQ().getPosition();
					Vector playerPos = player.getPosition();
					double shotAngle = thisVec.angleTo(playerPos);
					Projectile arrow = new Projectile(swg.gameMap.getEnemyHQ().getX(), swg.gameMap.getEnemyHQ().getY(), Vector.getVector(shotAngle, 2f), 0);
					arrow.rotate(-90);
					arrow.rotate(shotAngle);
					swg.gameMap.addProjectile(arrow);
				}	
			}
		}
		if(swg.gameMap.getEnemyHQTower1() != null) {
			if(player.getPosition().epsilonEquals(swg.gameMap.getEnemyHQTower1().getPosition(), 190)) {
				if(swg.gameMap.getEnemyHQTower1().attackCooldownCheck()) {
					Vector thisVec = swg.gameMap.getEnemyHQTower1().getPosition();
					Vector playerPos = player.getPosition();
					double shotAngle = thisVec.angleTo(playerPos);
					Projectile arrow = new Projectile(swg.gameMap.getEnemyHQTower1().getX(), swg.gameMap.getEnemyHQTower1().getY(), Vector.getVector(shotAngle, 2f), 0);
					arrow.rotate(-90);
					arrow.rotate(shotAngle);
					swg.gameMap.addProjectile(arrow);
				}
			}
		}
		if(swg.gameMap.getEnemyHQTower2() != null) {
			if(player.getPosition().epsilonEquals(swg.gameMap.getEnemyHQTower2().getPosition(), 190)) {
				if(swg.gameMap.getEnemyHQTower2().attackCooldownCheck()) {
					Vector thisVec = swg.gameMap.getEnemyHQTower2().getPosition();
					Vector playerPos = player.getPosition();
					double shotAngle = thisVec.angleTo(playerPos);
					Projectile arrow = new Projectile(swg.gameMap.getEnemyHQTower2().getX(), swg.gameMap.getEnemyHQTower2().getY(), Vector.getVector(shotAngle, 2f), 0);
					arrow.rotate(-90);
					arrow.rotate(shotAngle);
					swg.gameMap.addProjectile(arrow);
				}
			}
		}
		if(swg.gameMap.getEnemyEastTower() != null) {
			if(player.getPosition().epsilonEquals(swg.gameMap.getEnemyEastTower().getPosition(), 190)) {
				if(swg.gameMap.getEnemyEastTower().attackCooldownCheck()) {
					Vector thisVec = swg.gameMap.getEnemyEastTower().getPosition();
					Vector playerPos = player.getPosition();
					double shotAngle = thisVec.angleTo(playerPos);
					Projectile arrow = new Projectile(swg.gameMap.getEnemyEastTower().getX(), swg.gameMap.getEnemyEastTower().getY(), Vector.getVector(shotAngle, 2f), 0);
					arrow.rotate(-90);
					arrow.rotate(shotAngle);
					swg.gameMap.addProjectile(arrow);
				}
			}
		}
		if(swg.gameMap.getEnemySouthTower() != null) {
			if(player.getPosition().epsilonEquals(swg.gameMap.getEnemySouthTower().getPosition(), 190)) {
				if(swg.gameMap.getEnemySouthTower().attackCooldownCheck()) {
					Vector thisVec = swg.gameMap.getEnemySouthTower().getPosition();
					Vector playerPos = player.getPosition();
					double shotAngle = thisVec.angleTo(playerPos);
					Projectile arrow = new Projectile(swg.gameMap.getEnemySouthTower().getX(), swg.gameMap.getEnemySouthTower().getY(), Vector.getVector(shotAngle, 2f), 0);
					arrow.rotate(-90);
					arrow.rotate(shotAngle);
					swg.gameMap.addProjectile(arrow);
				}
			}
		}
		
		if(soulList != null) {
			for(SoulWarsSoul soul : soulList) {
				if(player.collides(soul) != null) {
					player.collectSoul(soul.getCount());
					collectedSoul.add(soul);
				}
			}
			soulList.removeAll(collectedSoul);
		}
		for(SoulWarsTile tile : swg.gameMap.getCollideList()) {
			if(player.collides(tile) != null) {
				player.translate(player.collides(tile).getMinPenetration().scale(delta));
			}
		}
		
		if(units.size() != 0) {
			for(SoulWarsUnit unit : units) {
				if(unit.getTeam() == 0) {
					if(unit.getPosition().epsilonEquals(swg.gameMap.getPlayerHQ().getPosition(), 164)) {
						if(unit.getHealth() < unit.getMaxHealth()) {
							if(swg.gameMap.getPlayerHQ().healCooldownCheck()) {
								unit.heal(1);
							}
						}
					}
					if(unitsFollow) {
						if(!unit.getPosition().epsilonEquals(player.getPosition(), 64)) {
							unit.clearPath();
							unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY(), player.getMapPosX(), player.getMapPosY()));
						}else {
							unit.clearPath();
							
						}
					}
					if(unitsDismiss) {
						if(!unit.getPosition().epsilonEquals(swg.gameMap.getPlayerHQ().getPosition(), 128)) {
							unit.clearPath();
							unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY(), swg.gameMap.getPlayerHQ().getMapPosX(), swg.gameMap.getPlayerHQ().getMapPosY()));
						}else {
							unit.clearPath();
						}
					}
					
				}
				
				if(unit.getTeam() == 1) {
					if(swg.gameMap.getEnemyHQ() != null) {
						if(unit.getPosition().epsilonEquals(swg.gameMap.getEnemyHQ().getPosition(), 164)) {
							if(unit.getHealth() < unit.getMaxHealth()) {
								if(swg.gameMap.getEnemyHQ().healCooldownCheck()) {
									unit.heal(1);
								}
							}
						}
					}
					
					if(unit.getPosition().epsilonEquals(player.getPosition(), 190) && unit.getType() != 0) {
						unit.setTarget(player);
						unit.clearPath();
						unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY(), player.getMapPosX(), player.getMapPosY()));
					}else {
						unit.clearTarget();
						
					}
					
					if((float)unit.getHealth() < (float) 2 && unit.getGroup() < 3) {
						if(swg.gameMap.getEnemyHQ() != null) {
							if(!unit.getPosition().epsilonEquals(swg.gameMap.getEnemyHQ().getPosition(), 164)) {
								unit.clearPath();
								unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY(), swg.gameMap.getEnemyHQ().getMapPosX(), swg.gameMap.getEnemyHQ().getMapPosY()));
							}else {
								unit.clearPath();
							}
						}
					}else if (unit.getHealth() == unit.getMaxHealth() && unit.getCurrentTarget() == null){
						if(unit.getGroup() == 0) {
							if(swg.gameMap.getEnemyHQ() != null) {
								if(!unit.getPosition().epsilonEquals(swg.gameMap.getEnemyHQ().getPosition(), 190)) {
									unit.clearPath();
									unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY(), swg.gameMap.getEnemyHQ().getMapPosX(), swg.gameMap.getEnemyHQ().getMapPosY()));
								}else {
									unit.clearPath();
								}
							}
						}else if(unit.getGroup() == 1) {
							if(swg.gameMap.getEnemyEastTower() != null) {
								if(!unit.getPosition().epsilonEquals(swg.gameMap.getEnemyEastTower().getPosition(), 128)) {
									unit.clearPath();
									unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY(), swg.gameMap.getEnemyEastTower().getMapPosX(), swg.gameMap.getEnemyEastTower().getMapPosY()));
								}
							}else {
									unit.clearPath();
								}
							
						}else if(unit.getGroup() == 2) {
							if(swg.gameMap.getEnemySouthTower() != null) {
								if(!unit.getPosition().epsilonEquals(swg.gameMap.getEnemySouthTower().getPosition(), 128)) {
									unit.clearPath();
									unit.setPath(swg.APather.findPath(unit, unit.getMapPosX(), unit.getMapPosY(), swg.gameMap.getEnemySouthTower().getMapPosX(), swg.gameMap.getEnemySouthTower().getMapPosY()));
								}
							}else {
								unit.clearPath();
							}	
						}
						
					}
				}
				for(SoulWarsTile tile : swg.gameMap.getCollideList()) {
					if(unit.collides(tile) != null) {
						unit.translate(unit.collides(tile).getMinPenetration().scale(delta/4));
					}
				}
				for(Projectile projectile : projectiles) {
					if(swg.gameMap.getEnemyHQ() != null) {
						if(projectile.collides(swg.gameMap.getEnemyHQ()) != null && projectile.getType() != 0) {
							if(swg.gameMap.getEnemyHQ().getArmor() <= 0) {
								deadProject.add(projectile);
								swg.gameMap.getEnemyHQ().takeDamage(5);
							}
							if(swg.gameMap.getEnemyHQ().getHealth() <= 0) {
								swg.gameMap.clearEnemyHQ();
							}
						}
					}
					if(swg.gameMap.getEnemyEastTower() != null) {
						if(projectile.collides(swg.gameMap.getEnemyEastTower()) != null && projectile.getType() != 0) {
							if(swg.gameMap.getEnemyEastTower().getArmor() <= 0) {
								deadProject.add(projectile);
								swg.gameMap.getEnemyEastTower().takeDamage(5);
							}
							if(swg.gameMap.getEnemyEastTower().getHealth() <= 0) {
								swg.gameMap.clearEnemyEastTower();
							}
						}
					}
					if(swg.gameMap.getEnemySouthTower() != null) {
						if(projectile.collides(swg.gameMap.getEnemySouthTower()) != null && projectile.getType() != 0) {
							if(swg.gameMap.getEnemySouthTower().getArmor() <= 0) {
								deadProject.add(projectile);
								swg.gameMap.getEnemySouthTower().takeDamage(5);
							}	
							if(swg.gameMap.getEnemySouthTower().getHealth() <= 0) {
								swg.gameMap.clearEnemySouthTower();
							}
						}
					}
					if(swg.gameMap.getEnemyHQTower1() != null) {
						if(projectile.collides(swg.gameMap.getEnemyHQTower1()) != null && projectile.getType() != 0) {
							if(swg.gameMap.getEnemyHQTower1().getArmor() <= 0) {
								deadProject.add(projectile);
								swg.gameMap.getEnemyHQTower1().takeDamage(5);
							}
							if(swg.gameMap.getEnemyHQTower1().getHealth() <= 0) {
								swg.gameMap.clearEnemyHQTower1();
							}
						}
					}
					if(swg.gameMap.getEnemyHQTower2() != null) {
						if(projectile.collides(swg.gameMap.getEnemyHQTower2()) != null && projectile.getType() != 0) {
							if(swg.gameMap.getEnemyHQTower2().getArmor() <= 0) {
								deadProject.add(projectile);
								swg.gameMap.getEnemyHQTower2().takeDamage(5);
							}
							if(swg.gameMap.getEnemyHQTower2().getHealth() <= 0) {
								swg.gameMap.clearEnemyHQTower2();
							}
						}
					}
					
					
					if(projectile.collides(player) != null && projectile.getType() == 0) {
						player.takeDamage(10);
						deadProject.add(projectile);
					}
					if(unit.collides(projectile) != null) {
						if(projectile.getType() == 1) {
							if(unit.getTeam() == 1) {
								unit.takeDamage(2);
								unit.translate(unit.collides(projectile).getMinPenetration().scale(delta));
								deadProject.add(projectile);
								if(unit.getHealth() <= 0) {
									deadUnit.add(unit);
									swg.gameMap.addSoul(new SoulWarsSoul(unit.getX(), unit.getY(), unit.getSoulCount(), false));
								}
							}
						}else if(projectile.getType() == 0) {
							if(unit.getTeam() == 0) {
								unit.takeDamage(2);
								unit.translate(unit.collides(projectile).getMinPenetration().scale(delta));
								deadProject.add(projectile);
								if(unit.getHealth() <= 0) {
									deadUnit.add(unit);
									swg.gameMap.addSoul(new SoulWarsSoul(unit.getX(), unit.getY(), unit.getSoulCount(), false));
								}
							}
						}
					}
				}
				projectiles.removeAll(deadProject);
				deadProject.clear();
				for(SoulWarsUnit collideCheck : units) {
					if(unit.getVelocity().getX() != 0 & unit.getVelocity().getY() != 0) {
						if(unit.getHash() != collideCheck.getHash()) {
							if(unit.collides(collideCheck) != null && collideCheck.getTeam() == unit.getTeam()) {
								unit.translate(unit.collides(collideCheck).getMinPenetration());
							}else if(unit.collides(collideCheck) != null) {
								if(unit.attackCooldownCheck()) {
									unit.attack(collideCheck);
									collideCheck.translate(collideCheck.collides(unit).getMinPenetration().scale(2));
									if(collideCheck.getHealth() <= 0) {
										deadUnit.add(collideCheck);
										swg.gameMap.addSoul(new SoulWarsSoul(collideCheck.getX(), collideCheck.getY(), collideCheck.getSoulCount(), false));
									}
								}
								
							}
						}
					}
				}
				if(swg.gameMap.getEnemyHQ() != null) {
					if(unit.collides(swg.gameMap.getEnemyHQ()) != null && unit.getTeam() == 0) {
						if(swg.gameMap.getEnemyHQ().getArmor() > 0) {
							deadUnit.add(unit);
							swg.gameMap.getEnemyHQ().damageArmor();
						}
					}
				}
				if(swg.gameMap.getEnemyHQTower1() != null) {
					if(unit.collides(swg.gameMap.getEnemyHQTower1()) != null && unit.getTeam() == 0) {
						if(swg.gameMap.getEnemyHQTower1().getArmor() > 0) {
							deadUnit.add(unit);
							swg.gameMap.getEnemyHQTower1().damageArmor();
						}
					}
				}
				if(swg.gameMap.getEnemyHQTower2() != null) {
					if(unit.collides(swg.gameMap.getEnemyHQTower2()) != null && unit.getTeam() == 0) {
						if(swg.gameMap.getEnemyHQTower2().getArmor() > 0) {
							deadUnit.add(unit);
							swg.gameMap.getEnemyHQTower2().damageArmor();
						}
					}
				}
				if(swg.gameMap.getEnemyEastTower() != null) {
					if(unit.collides(swg.gameMap.getEnemyEastTower()) != null && unit.getTeam() == 0) {
						if(swg.gameMap.getEnemyEastTower().getArmor() > 0) {
							deadUnit.add(unit);
							swg.gameMap.getEnemyEastTower().damageArmor();
						}
					}
				}
				if(swg.gameMap.getEnemySouthTower() != null) {
					if(unit.collides(swg.gameMap.getEnemySouthTower()) != null && unit.getTeam() == 0) {
						if(swg.gameMap.getEnemySouthTower().getArmor() > 0) {	
							deadUnit.add(unit);
							swg.gameMap.getEnemySouthTower().damageArmor();
						}
					}
				}
				if(unit.collides(player) != null && unit.getTeam() == 0) {
					unit.translate(unit.collides(player).getMinPenetration().scale(delta/4));
				}
				if(unit.collides(player) != null && unit.getTeam() == 1) {
					player.takeDamage(2);
					player.translate(player.collides(unit).getMinPenetration().scale(delta*4));
					if (player.getX()-(gameView.getCameraX()*64) > 512 && gameView.getCameraX() < swg.gameMap.getWidthInTiles()) {
						gameView.moveCameraX(1, delta*4);
					}
					if (player.getY()-(gameView.getCameraY()*64) > 512 && gameView.getCameraY() < swg.gameMap.getHeightInTiles()) {
						gameView.moveCameraY(1, delta*4);
					}
					if (player.getX()-(gameView.getCameraX()*64) < 512 && gameView.getCameraX() > 0) {
						gameView.moveCameraX(-1, delta*4);
					}
					if (player.getY()-(gameView.getCameraY()*64) < 512 && gameView.getCameraY() > 0) {
						gameView.moveCameraY(-1, delta*4);
					}
				}
				
			unit.update(delta);
			}

		}
		units.removeAll(deadUnit);
		
		
		for(Projectile projectile : projectiles) {
			for(SoulWarsTile tile : swg.gameMap.getCollideList()) {
				if(projectile.collides(tile) != null)
					deadProject.add(projectile);
	
			}
			projectile.update(delta);
		}
		projectiles.removeAll(deadProject);
		swg.gameMap.getPlayerHQ().update(delta);
		
		if(swg.gameMap.getEnemyHQ() != null)
			swg.gameMap.getEnemyHQ().update(delta);
		
		if(swg.gameMap.getEnemyEastTower() != null)
			swg.gameMap.getEnemyEastTower().update(delta);
		
		if(swg.gameMap.getEnemySouthTower() != null)
			swg.gameMap.getEnemySouthTower().update(delta);
		
		if(swg.gameMap.getEnemyHQTower1() != null) 
			swg.gameMap.getEnemyHQTower1().update(delta);
		
		if(swg.gameMap.getEnemyHQTower2() != null)
			swg.gameMap.getEnemyHQTower2().update(delta);			
		
		if(player.getPosition().epsilonEquals(swg.gameMap.getPlayerHQ().getPosition(), 190)) {
			player.heal(1);
		}
		player.update(delta);
		prevPlayerPostion = player.getPosition();
		System.out.println(delta);
		if(player.getHealth() <= 0) {
			((GameOverState)game.getState(SoulWarsGame.GAMEOVERSTATE)).setEndState(2);
			game.enterState(SoulWarsGame.GAMEOVERSTATE);
			
		}
		if(swg.gameMap.getEnemyHQ() == null) {
			((GameOverState)game.getState(SoulWarsGame.GAMEOVERSTATE)).setEndState(1);
			game.enterState(SoulWarsGame.GAMEOVERSTATE);
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
