/**
 * 
 */
package zeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * This class is one of the inheritors of the Equipment class.
 * This equipment with a percentage chance prevents the wearer of the cloak to be effected by the anointed agent.
 * Add's a cloak object to the virologist's inventory from the Shelter.
 */
public class Cloak extends Equipment
{
	
	/**
	 * The percentage chance of evading the anointed agent's effect.
	 */
	private double evadePercentage;
	
	/** 
	 * The default constructor for this class.
	 **/
	public Cloak() 
	{
		super("Cloak");
		evadePercentage = 0.823;
	}
	
	@Override
	/**
	 * Add's this object to the virologist's inventory.
	 */
	public void pickUp(Virologist v) 
	{
		v.getInventory().addCloak(this);
	}
	
	/**
	 * Generates a random number between 1-1000, if it is less than the evadePercent*1000+1 returns true.
	 * If if it greater return false.
	 * */
	public boolean evade()
	{
		boolean evadeSuccess = false;
		
		if(testMode) {
			boolean b = false;
			System.out.println("Wanna defend with cloak? (Y/N)");
			BufferedReader in = Game.getBufferedReader();
			if(in == null)
			{
				in = new BufferedReader(new InputStreamReader(System.in));
				Game.setBufferedReader(in);
			}
			Timer.startEnemyRound();
			try {
				while(Timer.getEnemyRemainingTime() > 0 && !b) {
					
					while(Timer.getEnemyRemainingTime() > 0 && !in.ready());
						
					if (in.ready()) {
						String input = in.readLine();
						if (input.toLowerCase().equals("y"))
							b = true;
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
			return b;
		}
		else {
			Random r = new Random();
		
			int x = r.nextInt(1000)+1;
		
			if(x < evadePercentage*1000+1) {
				evadeSuccess = true;
			}
			else {
				evadeSuccess = false;
			}
		}
		return evadeSuccess;
	}
	
}
