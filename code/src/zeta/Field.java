package zeta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/** 
 * This is the base class for the fields like Storage, Lab and Shelter and works also the basic field.
 */
public class Field {

	private ArrayList<Field> Neighbours;
	protected ArrayList<Virologist> Viros;
	
	protected String ID;
	protected static int fieldID=1; 
	
	protected static boolean testMode=false;
	
	/** 
	 * The constructor for this class. We initialize the variables here.
	 */
	public Field()
	{
		Neighbours = new ArrayList<Field>(0);
		Viros = new ArrayList<Virologist>(0);
		this.ID="Field"+String.valueOf(fieldID);
		fieldID++;
	}
	
	public String getID() {
		return ID;
	}
	
	/** 
	 * This is a setter method which sets the current field's Neighbours.
	 * This looks like this only for the skeleton demo.
	 * 
	 * @param f
	 */
	public static void setNeighbours(Field f1, Field f2)
	{
		if (f1 != null && f2 != null)
		{
			f1.Neighbours.add(f2);
			f2.Neighbours.add(f1);
		}	
	}
	
	public static void setTestMode(boolean bTestMode) {
		testMode=bTestMode;
	}
	
	/** 
	 * This is a getter method which gives you back the list of virologists
	 * who stand on the current field.
	 * 
	 * @return the return is a list of array, which gives you back a list of virologists
	 */
	public ArrayList<Virologist> getVirologists()
	{
		return Viros;
	}
	
	/** 
	 * This is a getter method which gives you back the current field's Neighbours.
	 * 
	 * @return the return is a list of array, which gives you back the neighbours fields
	 */
	public ArrayList<Field> getNeighbours()
	{
		return Neighbours;
	}
	
	/** 
	 * The method deals with the available action on this type of field, like calling the base class's arrive
	 * and the wannaSteal and so on.
	 * 
	 * @param v
	 */
	public void arrive(Virologist v)
	{
		if (!v.getParalyzed() && !v.getDancing() && !v.getBearDancing()) {
			for(int i=0; i < Viros.size();i++) {
				if(Viros.get(i).getParalyzed()) {
					boolean b = wannaSteal(Viros.get(i));
					if (b)
					{
						Viros.get(i).stealBy(v);
						break;
					}
				}
			}
		}
	}
	
	/** 
	 * This method asks the user, that if he wants to steal from a paralyzed virologist.
	 * 
	 * @param v	The paralyzed virologist. 
	 * 
	 * @return the return is a boolean type value, which depends on the users input
	 */
	private boolean wannaSteal(Virologist v)
	{
		boolean b = false;
		System.out.println(v.getID() + " paralyzed. Wanna steal? (Y/N)");
		BufferedReader in = Game.getBufferedReader();
		if(in == null)
		{
			in = new BufferedReader(new InputStreamReader(System.in));
			Game.setBufferedReader(in);
		}
		try {
			while(Timer.getRemainingTime() > 0 && !in.ready());
				
			if (in.ready()) {
				String input = in.readLine();
				if (input.toLowerCase().equals("y"))
					b = true;
			} 	
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	/** 
	 * This method adds the newcomer virologist to the current field's virologist list.
	 * 
	 * @param v
	 */
	public void addVirologist(Virologist v)
	{
		Viros.add(v);
		v.setStandingField(this);
	}
	
	/** 
	 * This method removes the passing virologist from the current field's virologist list.
	 * 
	 * @param v
	 */
	public void removeVirologist(Virologist v)
	{
		Viros.remove(v);
	}
	
}
