package soulwars;

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
	private Path currentPath;
	private int stepCount;
	private int lastStepDir;
	Image unitSprite;
	
	
	
	
	public SoulWarsUnit(final float x, final float y, int type) {
		super(x, y);
		if(type == 1) {
			unitType = 1;
			isRanged = false;
			health = 5;
			soulCount = 1;
			stepCount = 0;
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
	
	public CoordinatePair<Float,Float> getPos() {
		CoordinatePair<Float,Float> unitPos = new CoordinatePair<Float,Float>(this.getX(),this.getY());
		return unitPos;
	}
	public void setVelocity(int dx, int dy) {
		velocity = new Vector(dx,dy);		
	}
	
	public void attack(SoulWarsUnit target) {
		target.takeDamage(attack);
	}
	
	public void setPath(Path newPath) {
		currentPath = newPath;
	}
	
	public void takeDamage(int dmg) {
		health = health - dmg;
	}
	
	public void clearPath() {
		currentPath = null;
	}
	
	public Vector followPath(final int delta) {
		Step currentStep = currentPath.getStep(stepCount);
		CoordinatePair<Integer,Integer> unitMapPos = new CoordinatePair<Integer,Integer>((int)this.getX()/64,(int)this.getY()/64);
		
		if(currentStep.getY() < unitMapPos.getY() ) {//north
			System.out.println("moving north");
			lastStepDir = 1;
			return new Vector(0, -.5f*delta);			
		}else if(currentStep.getX() > unitMapPos.getX() ) {//east
			System.out.println("moving east");
			lastStepDir = 2;
			return new Vector(.5f*delta, 0);
		}else if(currentStep.getY() > unitMapPos.getY() ) {//south
			System.out.println("moving south");
			lastStepDir = 3;
			return new Vector(0, .5f*delta);			
		}else if(currentStep.getX() < unitMapPos.getX() ) {//west
			System.out.println("moving west");
			lastStepDir = 4;
			return new Vector(-.5f*delta, 0);
		}else if(currentStep.getY() == unitMapPos.getY() && currentStep.getX() == unitMapPos.getX() ) {//we are there
			System.out.println("at step goal");
			stepCount = stepCount + 1;
			lastStepDir = 0;
			
		}
		return null;
	}
	
	public void update(final int delta) {
		if(currentPath != null) {
			if(stepCount < currentPath.getLength()) {
				Vector nextStep = followPath(delta);
				if(nextStep != null) {
					translate(nextStep);
				}
				else if (stepCount == currentPath.getLength()){
					clearPath();
					stepCount = 0;
				}
				
			}
		}
				
	}
	
	
}
