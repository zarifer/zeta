package zeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is one of the inheritors of the Agent class.
 * This agent makes effected virologist move randomly, unable to do anything and infect other virologists on the field.
 */
public class BearDance extends Agent{
	
	private static int ID=1;
	
	
	/** 
	 * The default constructor for this class.
	 **/
	public BearDance() {
		super("BearDance"+String.valueOf(ID),"BearDance",-1);
		ID++;
	}
	
	@Override
	public void setVirologistUnderEffect(Virologist v) {
		super.setVirologistUnderEffect(v);
		virologistUnderEffect.setBearDancing(true);
	}
	
	/** 
	 * Applies the agent's effect and decrements the remaining time.
	 **/
	public void step() {
		if(virologistUnderEffect != null) {
			infect();
			if(!virologistUnderEffect.getDied()) {
				Field f1 = virologistUnderEffect.getStandingField();
				ArrayList<Field> neighbours = f1.getNeighbours();
				Field field =null;
				if(testMode) {
					BufferedReader in = Game.getBufferedReader();
					if(in == null)
					{
						in = new BufferedReader(new InputStreamReader(System.in));
						Game.setBufferedReader(in);
					}
					Timer.startEnemyRound();
					try {
						while (Timer.getEnemyRemainingTime() > 0)
						{
							Game.printNeighbors(virologistUnderEffect);

							while(Timer.getEnemyRemainingTime() > 0 && !in.ready());
							
							if (in.ready()) {
								field = Game.getFieldFromID(in.readLine().toLowerCase());
								if(field!=null) {
									break;
								}
							} 	
							else {
								return;
							}
						}
							
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					Random r = new Random();
					if (neighbours.size() > 0)
					{
						int x = r.nextInt(neighbours.size());
						field=neighbours.get(x);
					}
				}
				if (field!=null)
				{
					virologistUnderEffect.move(field, false);
					infect();
				}
				
			}
			if(virologistUnderEffect.getDied())
				Timer.removeSteppable(this);
		}
	}
	
	/** 
	 * Attack's every other virologist on this field.
	 **/
	private void infect() {
		Field f = virologistUnderEffect.getStandingField();
		for(Virologist v: f.getVirologists()) {
			if (v!=virologistUnderEffect)
			{
				virologistUnderEffect.attack(v,this);
			}
		}
	}
}
