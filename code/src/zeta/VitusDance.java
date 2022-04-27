/**
 * 
 */
package zeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is one of the inheritors of the Agent class.
 * This agent makes effected virologist move randomly and unable to do anything.
 */
public class VitusDance extends Agent
{
	
	private static int ID=1;
	
	/** 
	 * The default constructor for this class.
	 **/
	public VitusDance() 
	{
		super("VitusDance"+String.valueOf(ID),"VitusDance",6);
		ID++;
	}
	
	@Override
	public void setVirologistUnderEffect(Virologist v) {
		super.setVirologistUnderEffect(v);
		virologistUnderEffect.setDancing(true);
	}
	
	@Override
	/** 
	 * Applies the agent's effect and decrements the remaining time.
	 **/
	public void step()
	{
		if(virologistUnderEffect != null) 
		{
			if(!virologistUnderEffect.getDied()) {
				setRemainingTime(remainingTime - 1);
				Field f1 = virologistUnderEffect.getStandingField();
				ArrayList<Field> neighbours = f1.getNeighbours();
				Field field =null;
				if(testMode) {
					BufferedReader in = Game.getBufferedReader();
					if(in == null)
					{
						in = new BufferedReader(new InputStreamReader(System.in));
						Game.setBufferedReader(in);
					}
					
					try {
						while (Timer.getRemainingTime() > 0)
						{
							Game.printNeighbors(virologistUnderEffect);

							while(Timer.getRemainingTime() > 0 && !in.ready());
							
							if (in.ready()) {
								field = Game.getFieldFromID(in.readLine().toLowerCase());
								if(field!=null) {
									break;
								}
							} 	
							else {
								return;
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					Random r = new Random();
					if (neighbours.size() > 0)
					{
						int x = r.nextInt(neighbours.size());
						field=neighbours.get(x);
				
						
					}
				}
				virologistUnderEffect.move(field, false);
			
				if(remainingTime==0) {
					virologistUnderEffect.setDancing(false);
					Timer.removeSteppable(this);
				}
			}
			if(virologistUnderEffect.getDied())
				Timer.removeSteppable(this);
		}
	}

}
