package soulwars;

import java.util.Stack;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


public class SoulWarsUnit extends Entity implements Mover {
	
	private Vector velocity;
	private int health;
	private int soulCount;
	private int attack;
	private boolean isRanged;
	private int unitType;
	private Stack<Step> currentPath;
	private int stepCount;
	Image unitSprite;
	
	
	
	
	public SoulWarsUnit(final float x, final float y, int type) {
		super(x, y);
		if(type == 1) {
			unitType = 1;
			isRanged = false;
			health = 5;
			soulCount = 1;
			stepCount = 0;
			currentPath = new Stack<Step>();
			unitSprite = ResourceManager.getImage(SoulWarsGame.UNIT_RSC_REDW);
			unitSprite.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(unitSprite);
			
		}
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public int getSoulCount() {
		return soulCount;
	}
	
	public int getType() {
		return unitType;
	}
	
	public boolean ranged() {
		return isRanged;
	}
	
	public int getMapPosX() {
		int mapPos = (int)(this.getX()/64);
		return mapPos;
	}
	
	public int getMapPosY() {
		int mapPos = (int)(this.getY()/64);
		return mapPos;
	}
	

	public void setVelocity(int dx, int dy) {
		velocity = new Vector(dx,dy);		
	}
	
	public void attack(SoulWarsUnit target) {
		target.takeDamage(attack);
	}
	
	public void setPath(Path newPath) {
		if(newPath != null) {
			for(int i = newPath.getLength() - 1; i > 0; i--) {
				if(currentPath.search(newPath.getStep(i)) == -1) {
					currentPath.push(newPath.getStep(i));
				}
			}
		}
	}
	
	public Stack<Step> getPath() {
		return this.currentPath;
	}
	
	public void takeDamage(int dmg) {
		health = health - dmg;
	}
	
	public void clearPath() {
		currentPath.removeAllElements();
	}
	
	public int getHash() {
		int hash = 17 * (int)(this.getX()) ^ 19 * (int)(this.getY());
		return hash;
	}
	
	public Vector followPath() {
		Step nextStep = this.currentPath.peek();
		Vector curPos = new Vector(this.getX(),this.getY());
		Vector tarPos = new Vector((nextStep.getX() * 64) + 32, (nextStep.getY() * 64) + 32);
		final double angleToStep = curPos.angleTo(tarPos);
		if(curPos.epsilonEquals(tarPos, 10f)) {
			currentPath.pop();
		}

		
		Vector newVec = Vector.getVector(angleToStep, .1f);
		return newVec;		
	}
	
	//An exact copy of the Entity.render(g) method, except this accounts for the camera position
	public void cameraRender(final Graphics g, int cameraX, int cameraY ) {
		Vector actualPos = this.getPosition();
		Vector cameraPos = new Vector(actualPos.getX() - (cameraX * 64), actualPos.getY() - (cameraY * 64));
		this.setPosition(cameraPos);
		this.render(g);
		this.setPosition(actualPos);
	}
	
	public void update(final int delta) {
		if(currentPath.empty() != true) {
			Vector nextStep = followPath();
			translate(nextStep.scale(delta));
		}
			
	}
	
	
}
