package zeta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/** 
 * This class is one of the inheritors or heirs of the Field class, that is why we apply the extends expression here.
 * The purpose of this field is to "store" the materials on one field.
 */
public class Shelter extends Field{

	
	private static int shelterID=1; 
	/** 
	 * The constructor for this class.
	 */
	public Shelter(){
		super.ID="Shelter"+String.valueOf(shelterID);
		Field.fieldID--;
		shelterID++;
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
			b = wannaPickUp();
		
			if (b)
			{
				addEq(v);
			}
		}
	}
	
	/** 
	 * This method creates the equipment by using a random number generator from 0 to 3 and calls the 
	 * correspondent pickup method of it.
	 * 
	 * @param v
	 */
	private void addEq(Virologist v)
	{
		Sack s = null;
		Gloves g = null;
		Cloak c = null;
		Axe a = null;
		if(testMode) {
			String input="";
			boolean bCorrectInput = false;
			System.out.println("What to pick up? (Axe/Gloves/Cloak/Sack)");
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
						bCorrectInput = input.equals("axe") || input.equals("gloves")
								|| input.equals("cloak") || input.equals("sack");
					} 
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch(input)
			{
			case "sack":
				s=new Sack();
				s.pickUp(v);
				break;
			
			case "gloves":
				g=new Gloves();
				g.pickUp(v);
				break;
			
			case "cloak":
				c=new Cloak();
				c.pickUp(v);
				break;
			
			case "axe":
				a=new Axe();
				a.pickUp(v);
				break;
			}
		}
		else {
			Random r = new Random();
			int x = r.nextInt(3);
		
			switch(x)
			{
				case 0:
					s=new Sack();
					s.pickUp(v);
					break;
				
				case 1:
					g=new Gloves();
					g.pickUp(v);
					break;
				
				case 2:
					c=new Cloak();
					c.pickUp(v);
					break;
				
				case 3:
					a=new Axe();
					a.pickUp(v);
					break;
			}
		}
	}
	
	/** 
	 * This method asks the user, that if one wants to pick up the available equipment there or not.
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
}
