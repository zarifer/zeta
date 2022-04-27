package zeta;

/**
 * The parent class of the Equipments that can be picked up from Shelter.
 */
public abstract class Equipment {
	
	private String name;
	
	protected static boolean testMode=false;
	
	/** 
	 * The default constructor for this class.
	 **/
	public Equipment(String name) {
		this.name=name;
	}
	
	public static void setTestMode(boolean bTestMode) {
		testMode = bTestMode;
	}
	
	public String getName() {
		return name;
	}
	
	/** 
	 * The method is for adding Equipments to the inventory, here it is abstract.
	 */
	public abstract void pickUp(Virologist v);

}
