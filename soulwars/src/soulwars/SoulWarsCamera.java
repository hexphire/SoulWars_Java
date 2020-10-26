package soulwars;

import java.util.ArrayList;
import java.util.Stack;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import jig.ResourceManager;
import jig.Vector;

public class SoulWarsCamera {
	
	private float xOffSet;
	private float yOffSet;
	private int tileWidth;
	private int tileHeight;
	private int mapHeight;
	private int mapWidth;
	
	
	SoulWarsMap currentGame;
	
	
	public SoulWarsCamera(SoulWarsMap map) {
		super();
		xOffSet = 0;
		yOffSet = 0;
		currentGame = map;
		tileWidth = currentGame.getTileWidth();
		tileHeight = currentGame.getTileHeight();
		mapHeight = currentGame.getHeightInTiles();
		mapWidth = currentGame.getWidthInTiles();

	}
	//horizontal move
	public void moveCameraX(int dx, int delta) {
		if (dx > 0 && xOffSet < mapWidth - 16) {
			xOffSet += .00195f * delta;
		}else if(dx < 0 && xOffSet > 0){
			xOffSet -= .00195f * delta;
		}
		return;
	}
	//vertical move
	public void moveCameraY(int dy, int delta) {
		if (dy > 0 && yOffSet < mapHeight - 16) {
			yOffSet += .00195f * delta;
		}else if(dy < 0 && yOffSet > 0) {
			yOffSet -= .00195f * delta;
		}	
		return;
	}
	
	//getters for camera offset
	public float getCameraX() {
		return xOffSet;
	}
	
	public float getCameraY() {
		return yOffSet;
	}
	
	public void renderHud(Graphics g, WizardCharacter player) {
		Image uiBackground = ResourceManager.getImage(SoulWarsGame.UI_BACK_RSC);
		float barWidth = 125;
		float barHeight = 25;
		
		float healthBar = ((float) player.getHealth() / (float) player.getMaxHealth()) * barWidth;
		float manaBar = ((float) player.getMana() / (float) player.getMaxMana()) * barWidth;
		uiBackground.draw(1024,0);
		g.drawString("Health", 1050, 25);
		g.fillRect(1050, 50, barWidth, barHeight);
		g.setColor(Color.red);
		g.fillRect(1050, 50, healthBar, barHeight);
		g.setColor(Color.black);
		g.drawString("Mana", 1050, 80);
		g.fillRect(1050, 100, barWidth, barHeight);
		g.setColor(Color.blue);
		g.fillRect(1050, 100, manaBar, barHeight);
		g.setColor(Color.black);
		
		g.drawString("Souls: " + player.getSoulCount(), 1050, 140);
		g.drawString("Unit mana boost: ", 1050, 160);
		
		
		g.drawString("Spells: Mana & Soul cost", 1050, 200);
		if(player.getMana() < 10 || !player.fireballCooldownCheck()) {
			g.setColor(Color.white);
		}
		g.drawString("Fireball: 10 & 0", 1050, 220);
		g.setColor(Color.black);
		if(player.getMana() < 15 || !player.hasteCooldownCheck()) {
			g.setColor(Color.white);
		}
		g.drawString("Haste: 15 & 0", 1050, 240);
		g.setColor(Color.white);
		if(player.getMana() >= 25 && player.getSoulCount() >= 5) {
			if(player.hasteCooldownCheck())
				g.setColor(Color.black);
		}
		g.drawString("Heal: 25 & at least 5", 1050, 260);
		g.setColor(Color.white);
		if(player.getMana() > 50 && player.getSoulCount() > 1) {
			if(player.summonCooldownCheck())
				g.setColor(Color.black);
		}
		g.drawString("Summon Unit: 50 & 1", 1050, 280);
		g.setColor(Color.black);
		
		
		g.drawString("Movement: ", 1050, 320);
		Image wasd = ResourceManager.getImage(SoulWarsGame.UI_WASD_RSC);
		wasd.draw(1140, 305);
		
		g.drawString("Cast spell at mouse: ", 1050, 400);
		Image leftClick = ResourceManager.getImage(SoulWarsGame.UI_LMOUSE_RSC).getScaledCopy(.5f);
		leftClick.draw(1100, 425);
		
		g.drawString("Send units to mouse: ", 1050, 500);
		Image rightClick = ResourceManager.getImage(SoulWarsGame.UI_RMOUSE_RSC).getScaledCopy(.5f);
		rightClick.draw(1100, 525);
		
		g.drawString("Set units to follow: ", 1050, 600);
		Image eKey = ResourceManager.getImage(SoulWarsGame.UI_EKEY_RSC).getScaledCopy(.5f);
		eKey.draw(1100, 620);
		g.drawString("Send all units to HQ: ", 1050, 700);
		Image qKey = ResourceManager.getImage(SoulWarsGame.UI_QKEY_RSC).getScaledCopy(.5f);
		qKey.draw(1100, 720);
		
		g.drawString("Fireball: ", 1050, 780);
		Image Key1 = ResourceManager.getImage(SoulWarsGame.UI_1KEY_RSC).getScaledCopy(.5f);
		Key1.draw(1150, 765);
		
		g.drawString("Haste: ", 1050, 830);
		Image Key2 = ResourceManager.getImage(SoulWarsGame.UI_2KEY_RSC).getScaledCopy(.5f);
		Key2.draw(1150, 815);
		
		g.drawString("Heal: ", 1050, 880);
		Image Key3 = ResourceManager.getImage(SoulWarsGame.UI_3KEY_RSC).getScaledCopy(.5f);
		Key3.draw(1150, 865);
		
		g.drawString("Summon Unit: ", 1050, 930);
		Image Key4 = ResourceManager.getImage(SoulWarsGame.UI_4KEY_RSC).getScaledCopy(.5f);
		Key4.draw(1150, 915);
		
		
	}
	
	public void renderPath(Stack<Step> currentPath, Graphics g) {
		boolean[][] pathMap = new boolean[mapWidth][mapHeight];
		for (int i = 0; i < currentPath.size(); i++) {
			pathMap[currentPath.get(i).getX()][currentPath.get(i).getY()] = true;
		}
		for (int xTile = 0; xTile < 16; xTile++) {
			for (int yTile = 0; yTile < 16; yTile++) {
		
				if(pathMap[xTile + (int)xOffSet][yTile + (int)yOffSet] != false) {
					g.drawString("X", (xTile*tileWidth)+16, (yTile*tileHeight)+16);
					
				}
			}
		}
		
	}
	
	public void renderHQ(ArrayList<SoulWarsHQ> headquatersList, Graphics g) {
		for(SoulWarsHQ headquarters : headquatersList) {
			if((headquarters.getX()/tileWidth) > this.xOffSet && (headquarters.getY()/tileHeight) > this.yOffSet ) {	
				if(headquarters.getX() < 1024 + (xOffSet*64)) {
					int healthY, armorY;
					float barWidth = 75;
					float barHeight = 10;
					if(headquarters.getType() == 1) {
						healthY = 90;
						armorY = 80;
					}else {
						healthY = 65;
						armorY = 55;
					}
					
					float healthBar = ((float) headquarters.getHealth() / (float) headquarters.getMaxHealth()) * barWidth;
					float armorBar = ((float) headquarters.getArmor() / (float) headquarters.getMaxArmor()) * barWidth;
					g.translate(-xOffSet*64,-yOffSet*64);
					g.fillRect(headquarters.getX()-20, headquarters.getY()-healthY, barWidth, barHeight);
					g.setColor(Color.red);
					g.fillRect(headquarters.getX()-20, headquarters.getY()-healthY, healthBar, barHeight);
					g.setColor(Color.black);
					g.fillRect(headquarters.getX()-20, headquarters.getY()-armorY, barWidth, barHeight);
					g.setColor(Color.gray);
					g.fillRect(headquarters.getX()-20, headquarters.getY()-armorY, armorBar, barHeight);
					g.setColor(Color.black);
					headquarters.render(g);
					g.translate(xOffSet*64, yOffSet*64);
				}
			}
		}
	}
	
	public void renderSouls(ArrayList<SoulWarsSoul> soulList, Graphics g) {
		for(SoulWarsSoul soul : soulList) {
			if((soul.getX()/tileWidth) > this.xOffSet && (soul.getY()/tileHeight) > this.yOffSet ) {	
				if(soul.getX() < 1024 + (xOffSet*64)) {
					g.translate(-xOffSet*64,-yOffSet*64);
					soul.render(g);
					g.translate(xOffSet*64, yOffSet*64);
				}
			}
		}
	}
	
	public void renderSelected(ArrayList<SoulWarsUnit> selectedList, Graphics g) {
		g.setColor(Color.black);
		for(SoulWarsUnit selected : selectedList) {
			if((selected.getX()/tileWidth) > this.xOffSet && (selected.getY()/tileHeight) > this.yOffSet ) {
				g.drawRect((selected.getX()-10) - (xOffSet * 64), (selected.getY()-15) - (yOffSet*64), 18, 28);
			}
		}
	}
	
	
	
	public void renderTerrain(ArrayList<SoulWarsTile> terrain, Graphics g) {
		for(SoulWarsTile tile : terrain) {
			if((tile.getX()/tileWidth) >= this.xOffSet-1 && (tile.getY()/tileHeight) >= this.yOffSet-1 ) {
				if(tile.getX() < 1024 + (xOffSet*64)) {
					g.translate(-xOffSet*64,-yOffSet*64);
					tile.render(g);
					g.translate(xOffSet*64, yOffSet*64);
				
				}
			}
		}
	}
	
	public void renderProjectile(ArrayList<Projectile> projectiles, Graphics g) {
		for(Projectile projectile : projectiles) {
			if((projectile.getX()/tileWidth) > this.xOffSet && (projectile.getY()/tileHeight) > this.yOffSet ) {
				if(projectile.getX() < 1024 + (xOffSet*64)) {
					g.translate(-xOffSet*64,-yOffSet*64);
					projectile.render(g);
					g.translate(xOffSet*64, yOffSet*64);
				
				}
			}
		}
	}
	
	public void renderUnits(ArrayList<SoulWarsUnit> units, Graphics g) {
		for (SoulWarsUnit unit : units) {
			if((unit.getX()/tileWidth) > this.xOffSet && (unit.getY()/tileHeight) > this.yOffSet ) {	
				if(unit.getX() < 1024 + (xOffSet*64)) {
					float barWidth = 20;
					float barHeight = 5;
					float healthBar = ((float) unit.getHealth() / (float) unit.getMaxHealth()) * barWidth;
					g.translate(-xOffSet*64,-yOffSet*64);
					unit.render(g);
					if(unit.getHealth() < unit.getMaxHealth()) {
						g.fillRect(unit.getX()-8, unit.getY()-18, barWidth, barHeight);
						g.setColor(Color.red);
						g.fillRect(unit.getX()-8, unit.getY()-18, healthBar, barHeight);
						g.setColor(Color.black);
					}
					g.translate(xOffSet*64, yOffSet*64);
				}
			}
			
			if(unit.getPath() != null) {
				renderPath(unit.getPath(), g);
			}
			g.flush();
		}
	}
	
	public void renderPlayer(WizardCharacter player, Graphics g) {
		if((player.getX()/tileWidth) > this.xOffSet && (player.getY()/tileHeight) > this.yOffSet ) {	
			if(player.getX() < 1024 + (xOffSet*64)) {
				g.translate(-xOffSet*64,-yOffSet*64);
				player.render(g);
				g.translate(xOffSet*64, yOffSet*64);
			}
		}
	}
	

	//renders the current camera view on screen
	public void renderView(int x, int y, Graphics g) {
		WizardCharacter player = currentGame.getPlayer();
		ArrayList<SoulWarsTile> terrain = currentGame.getTileList();
		ArrayList<SoulWarsUnit> units = currentGame.getUnits();
		ArrayList<Projectile> projectiles = currentGame.getProjectiles();
		ArrayList<SoulWarsSoul> souls = currentGame.getSoulList();
		ArrayList<SoulWarsHQ> playerHQ = currentGame.getHQs();
		
		if(terrain != null)
			renderTerrain(terrain, g);
		if(units != null)
			renderUnits(units, g);
		if(player != null)
			renderPlayer(player,g);
		if(projectiles != null)
			renderProjectile(projectiles, g);
		if(souls != null)
			renderSouls(souls, g);
		if(playerHQ != null) {
			renderHQ(playerHQ, g);
		
		renderHud(g, player);
		}
		
	}
	
}
