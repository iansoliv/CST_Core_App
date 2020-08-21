package codelets.motor;

import org.json.JSONException;
import org.json.JSONObject;
import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.Random;
import ws3dproxy.model.Creature;

public class HandsActionCodelet extends Codelet {

	private MemoryObject handsMO;
	private JSONObject previousHandsCmd = new JSONObject();
	private Creature c;
	private Random r = new Random();

	public HandsActionCodelet( Creature nc )
	{
		c = nc;
	}

	@Override
	public void accessMemoryObjects()
	{
		handsMO = (MemoryObject) this.getInput( "HANDS" );
	}

	public void proc()
	{

		JSONObject cmd = (JSONObject) handsMO.getI();
		
		if ( ! (cmd.length() == 0 ||  cmd.equals( previousHandsCmd ) ) )
		{
			try
			{
				if (cmd.has( "ACTION" ) && cmd.has( "OBJECT" ))
				{
					String action = cmd.getString( "ACTION" );
					String objectName = cmd.getString( "OBJECT" );
					if (action.equals( "PICKUP" ))
					{
						try
						{
							c.putInSack( objectName );
						}
						catch (Exception e)
						{

						}
						System.out.println( "Sending Put In Sack command to agent:****** " + objectName + "**********" );
					}
					if (action.equals( "EATIT" ))
					{
						try
						{
							c.eatIt( objectName );
						}
						catch (Exception e)
						{

						}
						System.out.println( "Sending Eat command to agent:****** " + objectName + "**********" );
					}
					if (action.equals( "BURY" ))
					{
						try
						{
							c.hideIt( objectName );
						}
						catch (Exception e)
						{

						}
						System.out.println( "Sending Bury command to agent:****** " + objectName + "**********" );
					}
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}

		}
		previousHandsCmd = cmd;
	}//end proc

	@Override
	public void calculateActivation()
	{

	}

}
