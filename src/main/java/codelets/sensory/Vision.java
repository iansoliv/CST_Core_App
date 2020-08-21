package codelets.sensory;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.List;
import ws3dproxy.model.Creature;

/**
 * Vision codelet is responsible for getting vision information from the
 * environment. It returns all objects in the visual field an its properties.
 */
public class Vision extends Codelet {

	private MemoryObject visionMO;
	private final Creature c;

	public Vision( Creature nc )
	{
		c = nc;
	}

	@Override
	public void accessMemoryObjects()
	{
		visionMO = (MemoryObject) this.getOutput( "VISION" );
	}

	@Override
	public void proc()
	{
		c.updateState();
		synchronized (visionMO)
		{
			List viewedThings = c.getThingsInVision();
			visionMO.setI( viewedThings );
		}
	}//end proc()

	@Override
	public void calculateActivation()
	{

	}

}// class end

