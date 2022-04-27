package zeta;

/**
 * The parent class for all the agents.
 * Stores the agent's name, ID, the effected virologist, effect time, remaining time and expiration. 
 * Manages the agent's effects.
 */
public abstract class Agent implements Steppable
{
	protected static boolean testMode=false;
	
	/** 
	 * The virologist who is effected by this agent.
	 */
	protected Virologist virologistUnderEffect;
	
	/** 
	 * The agent's name.
	 */
	protected String name;
	
	/** 
	 * The number of rounds after the agent will disappear.
	 */
	protected int remainingTime;
	
	/** 
	 * The number of rounds the agent is usable.
	 */
	protected final static int expiration = 10;
	
	/** 
	 * The number of rounds the agent's effect lasts.
	 */
	protected int effectTime;
	
	/** 
	 * The agent's unique identifier.
	 */
	protected String ID;
	
	/** 
	 * The constructor for this class. We initialize the variable here.
	 * 
	 * @param ID, name, remainingTime, effectTime
	 */
	public Agent(String ID,String name, int effectTime) {
		this.name = name;
		remainingTime = expiration;
		this.effectTime = effectTime;
		this.ID=ID;
	}
	
	/** 
	 * The method is for stepping Agents, here it is abstract.
	 */
	public abstract void step();
	
	
	public static void setTestMode(boolean bTestMode) {
		testMode = bTestMode;
	}
	
	/** 
	 * This method sets the remaningTime.
	 * 
	 * @param remaningTime
	 */
	public void setRemainingTime(int remainingTime) 
	{
		this.remainingTime = remainingTime;
	}
	
	/** 
	 * This method sets the effectTime.
	 * 
	 * @param effectTime
	 */
	public void setEffectTime(int effectTime) 
	{
		this.effectTime = effectTime;
	}
	
	/** 
	 * This method sets the name.
	 * 
	 * @param name
	 */
	public void setName(String s) 
	{
		this.name = s;
	}
	
	/** 
	 * This method returns remaningTime.
	 */
	public int getRemainingTime()
	{
		return remainingTime;
	}
	
	/** 
	 * This method returns expiration.
	 */
	public int getExpiration()
	{
		return expiration;
	}
	
	/** 
	 * This method returns effectTime.
	 */
	public int getEffectTime()
	{
		return effectTime;
	}
	
	/** 
	 * This method returns name.
	 */
	public String getName() 
	{
		return name;
	}

	/** 
	 * This method sets the virologistUnderEffect.
	 * 
	 * @param virologist
	 */
	public void setVirologistUnderEffect(Virologist virologist) {
		this.virologistUnderEffect = virologist;
		Timer.addSteppable(this);
	}
}
