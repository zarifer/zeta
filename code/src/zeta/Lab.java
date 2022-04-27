package zeta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** 
 * This class is one of the inheritors or heirs of the Field class, that is why we apply the extends expression here.
 * The purpose of this field is to "store" the gcode in the game.
 */
public class Lab extends Field{

	protected static int labID=1; 
	/** 
	 * The GCName variable is the actual name of the GCode.
	 */
	private GCode gc;
	
	/** 
	 * The constructor for this class. We initialize the variable here.
	 * 
	 * @param gcname, am, nuk
	 */
	public Lab(String gcname)
	{
		gc = new GCode(gcname);
		super.ID="Lab"+String.valueOf(labID);
		Field.fieldID--;
		labID++;
	}
	
	/** 
	 * The method deals with the available action on this type of field, like calling the base class's arrive
	 * and the touchy and so on.
	 * 
	 * @param v
	 */
	public void arrive(Virologist v)
	{
		boolean b = false;
		
		super.arrive(v);
		
		if (!v.getParalyzed() && !v.getDancing() && !v.getBearDancing()) {
			b = wannaTouchy();
		
			if (b)
			{
				v.touchy(gc);
			}
		}
	}
	
	/** 
	 * The method asks the user, that if one wants to read the available code there or not.
	 * 
	 * @return the return is a boolean type value, which depends on the users input
	 */
	private boolean wannaTouchy()
	{
		boolean b = false;
		System.out.println("Wanna touchy? (Y/N)");
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
