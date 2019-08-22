package memory_objects;

import java.awt.Polygon;
import ws3dproxy.model.WorldPoint;

public class CreatureInnerSense {

	public WorldPoint position;
	public double pitch;
	public double fuel;
	public Polygon FOV;

	@Override
	public String toString()
	{
		if (position != null)
		{
			return ( "Position: " + (int) position.getX() + "," + (int) position.getY() + " Pitch: " + pitch + " Fuel: " + fuel );
		}
		else
		{
			return ( "Position: null,null" + " Pitch: " + pitch + " Fuel: " + fuel );
		}
	}
}
