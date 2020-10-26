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
	private int maxHealth;
	private int health;
	private int soulCount;
	private int attack;
	private int team;
	private boolean isRanged;
	private int unitType;
	private Stack<Step> currentPath;
	Image unitSprite;
	
	
	
	
	public SoulWarsUnit(final float x, final float y, int type, int team) {
		super(x, y);
		this.unitType = type;
		this.team = team;
		if(unitType == 1) {
			isRanged = false;
			maxHealth = 5;
			health = 5;
			soulCount = 1;
			currentPath = new Stack<Step>();
			if(team == 0) {
				unitSprite = ResourceManager.getImage(SoulWarsGame.UNIT_RSC_BLUW);
			}else if(team == 1) {
				unitSprite = ResourceManager.getImage(SoulWarsGame.UNIT_RSC_REDW);
			}
			
			
		}
		if(unitType == 0) {
			isRanged = false;
			maxHealth = 2;
			health = 2;
			soulCount = 1;
			unitSprite = ResourceManager.getImage(SoulWarsGame.UNIT_RSC_WDMN);
		}
		addImageWithBoundingBox(unitSprite);
		setVelocity(0,0);
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

	
	public void update(final int delta) {
		if(currentPath.empty() != true) {
			velocity = followPath();
			translate(velocity.scale(delta));
		}
			
	}
	
	public int getHash() {
		int hash = 17 * (int)(this.getX()) ^ 19 * (int)(this.getY());
		return hash;
	}

	public int getHealth() {
		
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public int getTeam() {
		
		return this.team;
	}
	
	
	
}
