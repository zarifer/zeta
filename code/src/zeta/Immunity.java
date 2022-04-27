/**
 * 
 */
package zeta;

/**
 * This class is one of the inheritors of the Agent class.
 * This agent makes the effected virolgist immune to other virologists attack.
 */
public class Immunity extends Agent
{
	private static int ID=1;
	
	/** 
	 * The default constructor for this class.
	 **/
	public Immunity()
	{
		super("Immunity"+String.valueOf(ID),"Immunity",4);
		ID++;
	}
	
	@Override
	public void setVirologistUnderEffect(Virologist v) {
		super.setVirologistUnderEffect(v);
		virologistUnderEffect.setImmunity(true);
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
				if(remainingTime == 0) 
				{
					virologistUnderEffect.setImmunity(false);
					Timer.removeSteppable(this);
				}
			}
			else
				Timer.removeSteppable(this);
		}
	}
	
}
