package zeta;

/** 
 * This class is one of the inheritors or heirs of the Material class, that is why we apply the extends expression here.
 */
public class Aminoacid extends Material
{
	
	/** 
	 * The constructor for this class.
	 */
	public Aminoacid()
	{
		super("Aminoacid");
	}
	
	/** 
	 * This method is handles the material picking up action. This is the starter action 
	 * which comes after the wannaPickUp() method in the Storage.
	 * 
	 * @param v 
	 */
	public void pickUp(Virologist v)
	{
		if (!v.getParalyzed() && !v.getDancing() && !v.getBearDancing())
		{
			v.getInventory().addAmino(this);
		}
	}
	
}
