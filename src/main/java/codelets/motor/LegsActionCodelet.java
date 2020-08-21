package codelets.motor;

import org.json.JSONObject;
import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.Random;
import org.json.JSONException;
import ws3dproxy.model.Creature;

public class LegsActionCodelet extends Codelet {

	private MemoryObject legsActionMO;
	private double previousTargetx = 0;
	private double previousTargety = 0;
	private JSONObject previousLegsAction = new JSONObject();
	private final Creature c;
	double old_angle = 0;
	int k = 0;

	public LegsActionCodelet( Creature nc )
	{
		c = nc;
	}

	@Override
	public void accessMemoryObjects()
	{
		legsActionMO = (MemoryObject) this.getInput( "LEGS" );
	}

	@Override
	public void proc()
	{

		JSONObject cmd = (JSONObject) legsActionMO.getI();

		if ( ! (cmd.length() == 0) )
		{
			try
			{
				if (cmd.has( "ACTION" ))
				{
					int x = 0, y = 0;
					String action = cmd.getString( "ACTION" );
					if (action.equals( "FORAGE" ))
					{
						try
						{
							Random r = new Random();
							x = r.nextInt( 600 );
							y = r.nextInt( 800 );
							if ( ! cmd.equals( previousLegsAction ))
							{
								System.out.println( "Sending Forage command to agent:****** (" + x + "," + y + ") **********" );
							}
							c.rotate( 5 );

							//c.moveto(1,x,y);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (action.equals( "GOTO" ))
					{
						if ( ! cmd.equals( previousLegsAction ))
						{
							double speed = cmd.getDouble( "SPEED" );
							double targetx = cmd.getDouble( "X" );
							double targety = cmd.getDouble( "Y" );
							if ( ! cmd.equals( previousLegsAction ))
							{
								System.out.println( "Sending move command to agent: [" + targetx + "," + targety + "]" );
							}
							try
							{
								c.moveto( speed, targetx, targety );
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							previousTargetx = targetx;
							previousTargety = targety;
						}
					}
					else
					{
						System.out.println( "Sending stop command to agent" );
						try
						{
							c.moveto( 0, 0, 0 );
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
				previousLegsAction = cmd;
				k ++;

			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}

		}

	}//end proc

	@Override
	public void calculateActivation()
	{

	}

}
