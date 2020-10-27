package soulwars;

import org.newdawn.slick.util.pathfinding.AStarHeuristic;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class ChebyshevHeuristic implements AStarHeuristic{

	@Override
	public float getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty) {
			float dx = Math.abs(tx - x);
			float dy = Math.abs(ty - y);
			
			float result = Math.max(dx,dy);
			
		return result;
	}

}
