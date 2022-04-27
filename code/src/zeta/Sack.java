package zeta;

/**
 * This class is one of the inheritors of the Equipment class.
 * This equipment increases the invetory's maximum material capacity, if the sack is equipped .
 * Add's a sack object to the virologist's inventory from the Shelter.
 */
public class Sack extends Equipment
{
	
	/** 
	 * The inventory's material capacity is increased by this capacity, if the sack is equipped.
	 */
	private static final int capacity = 5;
	
	/** 
	 * The default constructor for this class.
	 **/
	public Sack() {
		super("Sack");
	}
	
	@Override
	/**
	 * Add's this object to the virologist's inventory.
	 */
	public void pickUp(Virologist v) 
	{
		v.getInventory().addSack(this);
	}
	
	/** 
	 * This method returns the capacity of the sack.
	 */
	public int getCapacity() 
	{
		return capacity;
	}
}
