package zeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is one of the inheritors of the Equipment class.
 * This equipment throw's back the anointed agent to the attacker.
 * Add's a sack object to the virologist's inventory from the Shelter.
 */
public class Gloves extends Equipment{
	
	/**
	 * The number of times the gloves can be used.
	 */
	private int usable;
	
	/** 
	 * The default constructor for this class.
	 **/
	public Gloves(){
		super("Gloves");
		setUsable(3);
	}
	
	/** 
	 * This method sets how many times the gloves can be used, if it is less than the current value.
	 * 
	 * @param bUsable
	 */
	public void setUsable(int bUsable) {
		this.usable = bUsable;
	}
	
	/** 
	 * This method returns how many times the gloves can be used.
	 */
	public int getUsable() {
		return usable;
	}
	
	@Override
	/**
	 * Add's this object to the virologist's inventory.
	 */
	public void pickUp(Virologist v)
	{
		v.getInventory().addGloves(this);
	}
	
	/** 
	 * The method throw's back the anointed agent to the attacker.
	 * 
	 * @param vFr, vTo, a
	 */
	public void use(Virologist vFr, Virologist vTo, Agent a) 
	{
		vTo.attack(vFr, a);
		usable--;
	}
	
	/** 
	 * The method asks the user, that if one wants to use the gloves or not.
	 * 
	 * @return the return is a boolean type value, which depends on the users input
	 */
	public boolean wannaUse() 
	{
		boolean wannaUse = false;
		
		System.out.println("Wanna use Gloves? (Y/N)");
		BufferedReader in = Game.getBufferedReader();
		if(in == null)
		{
			in = new BufferedReader(new InputStreamReader(System.in));
			Game.setBufferedReader(in);
		}
		Timer.startEnemyRound();
		try {
			while(Timer.getEnemyRemainingTime() > 0 && !wannaUse) {
			
				while(Timer.getEnemyRemainingTime() > 0 && !in.ready());
					
				if (in.ready()) {
					String input = in.readLine();
					if (input.toLowerCase().equals("y"))
						wannaUse = true;
					else if(input.toLowerCase().equals("n"))
						return false;
				}
				else
					return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Timer.increaseRemainingTime(Timer.timeSpentByEnemy());

		return wannaUse;
	}
}
