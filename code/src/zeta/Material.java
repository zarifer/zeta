package zeta;

/** 
 * This is the abstract base class for the Materials like Aminodacid and the Nukleotid. 
 */
public abstract class Material 
{
	private String name;
	
	/** 
	 * The constructor for this class.
	 */ 
	public Material(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	/** 
	 * This is an abstract method, so we, as a matter of fact, do not even utilize it, but the heirs like Aminoacid inherits it. 
	 * 
	 * @param v
	 */ 
	public abstract void pickUp(Virologist v);
}
