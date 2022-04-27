package zeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is one of the inheritors of the Equipment class.
 * This equipment kill's the given virologist.
 * Add's an axe object to the virologist's inventory from the Shelter.
 */
public class Axe extends Equipment{
	
	/** 
	 * Stores that if the axes was used or not.
	 **/
	private boolean Used;
	
	/** 
	 * The default constructor for this class.
	 **/
	public Axe() {
		super("Axe");
		this.Used = false;
	}

	@Override
	/**
	 * Add's this object to the virologist's inventory.
	 */
	public void pickUp(Virologist v) {
		v.getInventory().addAxe(this);
	}
	
	/** 
	 * This method returns if the axes was used or not.
	 */
	public boolean getUsed() {
		return Used;
	}
	
	/** 
	 * The method kill's the given virologist.
	 * 
	 * @param v
	 */
	public void use(Virologist v) {
		v.die();
		Used=true;
	}
	
	public boolean wannaUse() {
		
		boolean wannaUse = false;
		
		System.out.println("Wanna use Axe? (Y/N)");
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
