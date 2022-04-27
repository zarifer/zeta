package zeta;

/** 
 * This class is the inheritor of the Lab class.
 * On this field virologists can be infected with BearDance agent.
 */
public class InfectedLab extends Lab{

	private static int ilabID=1; 
	/** 
	 * The constructor for this class. We initialize the variable here.
	 * 
	 * @param gcname, am, nuk
	 */
	public InfectedLab(String gcname){
		super(gcname);
		super.ID="InfectedLab"+String.valueOf(ilabID);
		Lab.labID--;
		ilabID++;
	}
	
	/** 
	 * The method deals with the available action on this type of field: calling the base class's arrive
	 * and infect the virologist, if it is possible.
	 * 
	 * @param v
	 */
	public void arrive(Virologist v) {
		
		boolean bDefenseSucceed = false;
		
		super.arrive(v);
		
		if(!v.getImmunity()) {
			if(v.getWornCloaks().size()>0) {
				for(int i=0; i < v.getWornCloaks().size();i++) {
					Cloak c = v.getWornCloaks().get(i);
					bDefenseSucceed = c.evade();
					if(bDefenseSucceed) {
						v.getWornCloaks().remove(i);
						System.out.println("BearDance infection defended, cloak lost.");
						break;
					}
				}
			}
			if (!bDefenseSucceed)
			{
				v.setBearDancing(true);
				BearDance bd = new BearDance();
				bd.setVirologistUnderEffect(v);
				Timer.addSteppable(bd);
				System.out.println("BearDance infection successful.");
			}
		}
	}
}
