package codelets.behaviour;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import ws3dproxy.model.Thing;

public class Forage extends Codelet {

	private MemoryObject knownMO;
	private List<Thing> known;
	private MemoryObject legsMO;

	public Forage()
	{

	}

	@Override
	public void proc()
	{
		known = (List<Thing>) knownMO.getI();
		if (known.isEmpty())
		{
			JSONObject message = new JSONObject();
			try
			{
				message.put( "ACTION", "FORAGE" );
				
				legsMO.setI( message );

			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}

	}

	@Override
	public void accessMemoryObjects()
	{
		knownMO	= (MemoryObject) this.getInput( "KNOWN_APPLES" );
		legsMO	= (MemoryObject) this.getOutput( "LEGS" );
	}

	@Override
	public void calculateActivation()
	{

	}

}
