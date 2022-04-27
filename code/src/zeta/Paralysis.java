package zeta;

/**
 * This class is one of the inheritors of the Agent class.
 * This agent makes the effected virologist unable to move or act.
 */
public class Paralysis extends Agent
{
	private static int ID=1;
	
	/** 
	 * The default constructor for this class.
	 **/
	public Paralysis() 
	{
		super("Paralysis"+String.valueOf(ID),"Paralysis",3);
		ID++;
	}
	
	@Override
	public void setVirologistUnderEffect(Virologist v) {
		super.setVirologistUnderEffect(v);
		virologistUnderEffect.setParalyzed(true);
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
				setRemainingTime(remainingTime-1);
				if(remainingTime == 0)
				{
					virologistUnderEffect.setParalyzed(false);
					Timer.removeSteppable(this);
				}
			}
			else
				Timer.removeSteppable(this);
		}		
	}

}
