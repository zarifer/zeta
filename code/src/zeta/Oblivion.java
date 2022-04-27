/**
 * 
 */
package zeta;

/**
 * This class is one of the inheritors of the Agent class.
 * This agent deletes the effected virologist's stored genetic codes.
 */
public class Oblivion extends Agent
{
	private static int ID=1;
	
	/** 
	 * The default constructor for this class.
	 **/
	public Oblivion()
	{
		super("Oblivion"+String.valueOf(ID),"Oblivion",1);
		ID++;
	}
	
	@Override
	public void setVirologistUnderEffect(Virologist v) {
		super.setVirologistUnderEffect(v);
		v.getInventory().forgetGCodes();
		virologistUnderEffect.setOblivioned(true);
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
					virologistUnderEffect.setOblivioned(false);
					Timer.removeSteppable(this);
				}
			}
			else
				Timer.removeSteppable(this);
		}
	}

}
