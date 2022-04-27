package zeta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/** 
 * This class is one of the inheritors or heirs of the Field class, that is why we apply the extends expression here.
 * The purpose of this field is to "store" the equipments on one field.
 */
public class Storage extends Field implements Steppable{

	
	private static int storageID=1; 
	
	/** 
	 * Stores that if material can be picked up from the storage.
	 **/
	private boolean Empty;
	
	/** 
	 * Stores that for how many rounds the storage is empty.
	 **/
	private int remainingTime;
	
	/** 
	 * The constructor for this class.
	 */
	public Storage(){
		super.ID="Storage"+String.valueOf(storageID);
		Field.fieldID--;
		storageID++;
		Empty = false;
		Timer.addSteppable(this);
	}
	
	/** 
	 * The method deals with the available action on this type of field, like calling the base class's arrive
	 * and the pickup and so on.
	 * 
	 * @param v
	 */
	public void arrive(Virologist v)
	{
		boolean b = false;
		
		super.arrive(v);
		
		if (!v.getParalyzed() && !v.getDancing() && !v.getBearDancing()) {
			if(v.getBearDancing()) {
				Empty=true;
				remainingTime=2; //?????
			}
			else {
				b = wannaPickUp();
			
				if(b && !Empty) {
					addMat(v);
				}
			}
		}
	}
	
	/** 
	 * This method creates the material by using a random number generator from 0 to 1 and calls the 
	 * correspondent pickup method of it.
	 * 
	 * @param v
	 */
	private void addMat(Virologist v)
	{
		Aminoacid a = null;
		Nukleotid n = null;
		
		if(testMode) {
			String input="";
			boolean bCorrectInput = false;
			System.out.println("What to pick up? (Aminoacid/Nukleotid)");
			BufferedReader in = Game.getBufferedReader();
			if(in == null)
			{
				in = new BufferedReader(new InputStreamReader(System.in));
				Game.setBufferedReader(in);
			}
			try {
				while (!bCorrectInput && Timer.getRemainingTime() > 0)
				{
	
					while(Timer.getRemainingTime() > 0 && !in.ready());
						
					if (in.ready()) {
						input = in.readLine();
						input = input.trim().toLowerCase();
						bCorrectInput = input.equals("aminoacid") || input.equals("nukleotid");
					} 
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch(input)
			{
				case "aminoacid":
					a=new Aminoacid();
					a.pickUp(v);
					break;
				
				case "nukleotid":
					n=new Nukleotid();
					n.pickUp(v);
					break;
			}
			
		}
		else {
			Random r = new Random();
			int x = r.nextInt(1);
		
			switch(x)
			{
				case 0:
					a=new Aminoacid();
					a.pickUp(v);
					break;
				
				case 1:
					n=new Nukleotid();
					n.pickUp(v);
					break;
			}
		}
	}
	
	/** 
	 * This method asks the user, that if one wants to pick up the available materials there or not.
	 * 
	 * @return the return is a boolean type value, which depends on the users input
	 */
	private boolean wannaPickUp()
	{
		boolean b = false;
		System.out.println("Wanna pick up? (Y/N)");
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
	 * Decrements the remaining time, if it is 0 sets empty to false.
	 */
	public void step() {
		boolean bFound=false;
		for(int i=0;i<Viros.size();i++) {
			if(Viros.get(i).getBearDancing()) {
				bFound=true;
				break;
			}
		}
		if(!bFound)
			remainingTime--;
		if(remainingTime==0) {
			Empty=false;
		}
	}
}
