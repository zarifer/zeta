package zeta;
import java.lang.String;

/** 
 * This class handles the Genetic Codes and the whole agent creating process.
 */
public class GCode {

	/** 
	 * The GCName variable is the actual name of the GCode.
	 * The NeededAmino variable is the amount of amino we need to create a fix agent.
	 * The NeededNukleo variable is the amount of nukleo we need to create a fix agent.
	 */
	private String GCName;
	private int NeededAmino;
	private int NeededNukleo;
	
	/** 
	 * The constructor for this class. We initialize the variables here.
	 */
	public GCode(String gcname)
	{
		switch(gcname) {
		
			case "Oblivion":
				NeededAmino = 1;
				NeededNukleo = 1;
				break;
				
			case "Immunity":
				NeededAmino = 2;
				NeededNukleo = 4;
				break;
				
			case "Paralysis":
				NeededAmino = 1;
				NeededNukleo = 1;
				break;
			
			case "VitusDance":
				NeededAmino = 2;
				NeededNukleo = 1;
				break;
		}
		GCName = gcname;
	}
	
	/** 
	 * This is a getter method to get the name of the genetic code.
	 * 
	 * @return the return is a String type value, which gives back a GCname
	 */
	public String getGCName()
	{
		return GCName;
	}

	/** 
	 * This method handles the whole agent creating process, starting from initializing an agent object, 
	 *  taking out the required amount of material from the ones inventory and (in our case)
	 *  destroying these material. 
	 *  
	 *  @param v
	 *  @return the return is a Agent type value, which depends on the GCName's value
	 */
	public Agent create (Virologist v)
	{
		Agent a = null;
		
		if (!v.getParalyzed() && !v.getDancing() && !v.getBearDancing())
		{
			Inventory inv = v.getInventory();
			
			if (inv.getStoredAminoCount() >= NeededAmino && inv.getStoredNukleotidCount() >= NeededNukleo)
			{
				int loopTakeMaterial = NeededAmino;
				while(loopTakeMaterial > 0)
				{
					inv.removeAmino();
					loopTakeMaterial --;
				}
				
				loopTakeMaterial = NeededNukleo;
				while(loopTakeMaterial > 0)
				{
					inv.removeNukleo();
					loopTakeMaterial--;
				}
		
				switch(GCName)
				{
					case "Oblivion": 
						a = new Oblivion();
						break;
						
					case "Paralysis": 
						a = new Paralysis();
						break;
						
					case "Immunity": 
						a = new Immunity();
						break;
						
					case "VitusDance": 
						a = new VitusDance();
						break;		
				}
				System.out.println("Create agent successful.");
				v.setInventory(inv);
			}
			else
			{
				System.out.println("Not enough material.");
			}
		}	
		return a; 
	}
}
