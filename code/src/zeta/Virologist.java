package zeta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Virologist implements Steppable {
	private boolean immunity;
	private boolean paralyzed;
	private boolean dancing;
	private boolean BearDancing;
	private boolean Died;
	private boolean Oblivioned;
	private Field standingField;
	private ArrayList<Agent> agents;
	private ArrayList<Gloves> wornGloves;
	private ArrayList<Sack> wornSack;
	private ArrayList<Cloak> wornCloak;
	private ArrayList<Axe> wornAxe;
	private Inventory inventory;
	private String ID;
	private static int vID=1;
	
	public Virologist(Field standingField)
	{
		init(standingField);
		inventory = new Inventory(); 
	}
	
	public Virologist(Field standingField, boolean immunity, boolean paralyzed, boolean dancing, boolean beardancing,
			int aminos, int nukleotids, int storedGloves,
			int storedSack, int storedCloak, int storedAxe, ArrayList<GCode> learntGCode)
	{
		init(standingField);
		
		inventory = new Inventory(aminos,nukleotids, storedGloves,storedSack, storedCloak, storedAxe);
		
		this.immunity = immunity;
		this.paralyzed = paralyzed;
		this.dancing = dancing;
		this.BearDancing = beardancing;
		
		// convert ArrayList to HastSet to get only unique values.
	    HashSet<GCode> hset = new HashSet<GCode>(learntGCode);
	    //convert back to ArrayList
	    learntGCode = new ArrayList<GCode> (hset);
	    
	    if(learntGCode.size() < Game.getMaxGCode()) {
	    	for(int i=0; i < learntGCode.size();i++)
	    		inventory.learnGCode(learntGCode.get(i));
	    }
	}
	
	private void init(Field standingField)
	{
		ID = "V"+String.valueOf(vID);
		vID++;
		
		immunity = false;
		paralyzed = false;
		dancing = false;
		BearDancing = false;
		
		if (standingField != null)
		{
			this.standingField = standingField;
			standingField.addVirologist(this);
		}
		else
		{
			this.standingField = new Field();
		}
		
		agents = new ArrayList<Agent>();
		wornGloves = new ArrayList<Gloves>();
		wornSack = new ArrayList<Sack>();
		wornCloak = new ArrayList<Cloak>();
		wornAxe = new ArrayList<Axe>();
	}
	
	//-------------------BEGIN SETTER FUNCTIONS-------------------
	
	public void setImmunity(boolean bImmune)
	{
		this.immunity = bImmune;
	}
	
	public void setParalyzed(boolean bParalyzed)
	{
		this.paralyzed = bParalyzed;
	}
	
	public void setDancing(boolean bDancing)
	{
		this.dancing = bDancing;
	}
	
	public void setStandingField(Field f)
	{
		if (f != null)
		{
			this.standingField = f;
		}
	}
	
	public void setBearDancing(boolean bBearDancing) {
		this.BearDancing=bBearDancing;
	}
	
	public void setOblivioned(boolean bOblivioned) {
		this.Oblivioned=bOblivioned;
	}
	
	public void setInventory(Inventory inv) {
		this.inventory=inv;
	}
	
	//-------------------END SETTER FUNCTIONS-------------------
	
	//-------------------BEGIN GETTER FUNCTIONS-------------------
	
	public boolean getImmunity()
	{
		return immunity;
	}
	
	public boolean getParalyzed()
	{
		return paralyzed;
	}
	
	public boolean getDancing()
	{
		return dancing;
	}
	
	public Field getStandingField()
	{
		return standingField;
	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
	
	public ArrayList<Cloak> getWornCloaks(){
		return wornCloak;
	}
	
	public boolean getDied() {
		return Died;
	}
	
	public boolean getBearDancing() {
		return BearDancing;
	}
	
	public String getID() {
		return ID;
	}
	
	public ArrayList<Agent> getAgents(){
		return agents;
	}
	
	//-------------------END GETTER FUNCTIONS-------------------
	
	public void die() {
		this.Died = true;
		Timer.removeSteppable(this);
		standingField.removeVirologist(this);
		standingField=null;
	}
	
	public void step()
	{
		for (int i=0; i<agents.size(); i++)
		{
			Agent a = agents.get(i);
			a.setRemainingTime(a.getRemainingTime() - 1);
			
			if (a.getRemainingTime() == 0)
			{
				agents.remove(i);
			}
			else
			{
				agents.set(i, a);
			}
		}

	}
	
	public void equipGloves()
	{
		if (!paralyzed && !dancing && !BearDancing && canEquipEq())
		{
			Gloves glovesToEquip = inventory.removeGloves();
			if (glovesToEquip != null)
			{
				wornGloves.add(glovesToEquip);
			}
		}
	}
	
	public void equipSack()
	{
		if (!paralyzed && !dancing && !BearDancing && canEquipEq())
		{
			Sack sackToEquip = inventory.removeSack();
			if (sackToEquip != null)
			{
				wornSack.add(sackToEquip);
				int currentMaxMaterial = inventory.getMaxMaterial();
				inventory.setMaxMaterial(currentMaxMaterial + sackToEquip.getCapacity());
			}
		}
	}
	
	public void equipCloak()
	{
		if (!paralyzed && !dancing && !BearDancing && canEquipEq())
		{
			Cloak cloakToEquip = inventory.removeCloak();
			if (cloakToEquip != null)
			{
				wornCloak.add(cloakToEquip);
			}
		}
	}
	
	public void equipAxe()
	{
		if (!paralyzed && !dancing && !BearDancing && canEquipEq())
		{
			Axe axeToEquip = inventory.removeAxe();
			if (axeToEquip != null)
			{
				wornAxe.add(axeToEquip);
			}
		}
	}
	
	public boolean defense(Virologist vFr, Agent a)
	{
		boolean bDefenseSucceed = false;
		if (a == null || vFr == null)
		{
			bDefenseSucceed = true;
		}
		else if(!dancing && !paralyzed && !BearDancing)
		{
			if(wornAxe.size() > 0) {
				Axe ax = wornAxe.get(0);
				if(ax.wannaUse()) {
					ax.use(vFr);
					bDefenseSucceed = true;
					System.out.println(this.ID+" "+a.ID+" infection defended, Axe used.");
				}
			}
			
			if (!bDefenseSucceed && wornGloves.size() > 0)
			{
				Gloves g = wornGloves.get(0);
				if (g.wannaUse())
				{
					g.use(vFr, this, a);
					if(g.getUsable()==0) {
						wornGloves.remove(0);
						System.out.println(this.ID+" "+a.ID+" infection defended, Gloves lost.");
					}
					else
						System.out.println(this.ID+" "+a.ID+" infection defended, Gloves usable: "+g.getUsable()+".");
					bDefenseSucceed = true;
				}
				
			}
			
			if (!bDefenseSucceed)
			{
				for(int i = 0; i < wornCloak.size(); i++)
				{
					Cloak c = wornCloak.get(i);
					bDefenseSucceed = c.evade();
					if (bDefenseSucceed)
					{
						wornCloak.remove(i);
						System.out.println(this.ID+" "+a.ID+" infection defended, Cloak lost.");
						break;
					}
				}
			}
			if(!bDefenseSucceed) {
				System.out.println(this.ID+" "+a.ID+" infection succesful.");
			}
		}
		return bDefenseSucceed;
	}
	
	public boolean wannaDrop() {
		boolean wannaDrop = false;
		
		System.out.println("Put it to inventory? (Y/N)");
		int str=' ';
		while(true) {
			try {
				str = System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if((char)str=='Y' || (char)str=='y' || (char)str=='N' || (char)str=='n')
				break;
		}
		if((char)str=='Y' || (char)str=='y') {
			wannaDrop=false;
		}
		else {
			wannaDrop=true;
		}

		return wannaDrop;
	}
	
	public void takeOffGloves()
	{
		if (!paralyzed && !dancing && !BearDancing)
		{
			if(wornGloves.size()>0) {
				if(inventory.canAddEq() && !wannaDrop()) {
					takeOffGloves(inventory);
					System.out.println("Equipment added to inventory.");
				}
				else {
					wornGloves.remove(0);
					if(!inventory.canAddEq())
						System.out.println("Inventoty full, drop equipment");
					else
						System.out.println("Drop equipment.");
				}
			}
			else 
				System.out.println("You haven't equipped this kind of equipment.");
		}
	}
	
	private boolean takeOffGloves(Inventory inv)
	{
		boolean bFound = false;
		if (wornGloves.size() > 0)
		{
			Gloves glovesToRemove = wornGloves.remove(0);
			if (inv != this.inventory)
			{
				System.out.println("Gloves found.");
			}
			inv.addGloves(glovesToRemove);
			bFound = true;
		}
		return bFound;
	}
	
	public void takeOffSack()
	{
		if (!paralyzed && !dancing && !BearDancing)
		{
			if(wornSack.size()>0) {
				if(inventory.canAddEq() && !wannaDrop()) {
					takeOffSack(inventory);
					System.out.println("Equipment added to inventory.");
				}	
				else {
					wornSack.remove(0);
					if(!inventory.canAddEq())
						System.out.println("Inventoty full, drop equipment");
					else
						System.out.println("Drop equipment.");
				}
			}
			else 
				System.out.println("You haven't equipped this kind of equipment.");
		}
	}
	
	private boolean takeOffSack(Inventory inv)
	{
		boolean bFound = false;
		if (wornSack.size() > 0)
		{
			Sack SackToUnequip = wornSack.remove(0);
			if (inv != this.inventory)
			{
				System.out.println("Sack found.");
			}
			
			inv.addSack(SackToUnequip);
			
			int currentMaxMaterial = inventory.getMaxMaterial();
			inventory.setMaxMaterial(currentMaxMaterial - SackToUnequip.getCapacity());
			bFound = true;
		}
		return bFound;
	}
	
	public void takeOffCloak()
	{
		if (!paralyzed && !dancing && !BearDancing)
		{
			if(wornCloak.size()>0) {
				if(inventory.canAddEq() && !wannaDrop()) {
					takeOffCloak(inventory);
					System.out.println("Equipment added to inventory.");
				}		
				else {
					wornCloak.remove(0);
					if(!inventory.canAddEq())
						System.out.println("Inventoty full, drop equipment");
					else
						System.out.println("Drop equipment.");
				}
			}
			else 
				System.out.println("You haven't equipped this kind of equipment.");
		}
	}
	
	private boolean takeOffCloak(Inventory inv)
	{
		boolean bFound = false;
		if (wornCloak.size() > 0)
		{
			Cloak cloakToRemove = wornCloak.remove(0);
			if (inv != this.inventory)
			{
				System.out.println("Cloak found.");
			}
			inv.addCloak(cloakToRemove);
			bFound = true;
		}
		return bFound;
	}
	
	public void takeOffAxe() {
		if (!paralyzed && !dancing && !BearDancing)
		{
			if(wornAxe.size()>0) {
				if(inventory.canAddEq() && !wannaDrop()) {
					takeOffAxe(inventory);
					System.out.println("Equipment added to inventory.");
				}	
				else {
					wornAxe.remove(0);
					if(!inventory.canAddEq())
						System.out.println("Inventoty full, drop equipment");
					else
						System.out.println("Drop equipment.");
				}
			}
			else 
				System.out.println("You haven't equipped this kind of equipment.");
		}
	}
	
	private boolean takeOffAxe(Inventory inv) {
		boolean bFound = false;
		if (wornAxe.size() > 0)
		{
			Axe axeToRemove = wornAxe.remove(0);
			if (inv != this.inventory)
			{
				System.out.println("Axe found.");
			}
			inv.addAxe(axeToRemove);
			bFound = true;
		}
		return bFound;
	}
	
	//it is enough to give over the GCode here, don't need to use the Lab object inside this function
	public void touchy(GCode gc)
	{
		if (!paralyzed && !dancing && !BearDancing && gc != null)
		{
			int currentGCodeCount = inventory.learnGCode(gc);
			if (currentGCodeCount == Game.getMaxGCode())
			{
				Game.endGame(this);
			}
			else
			{
				System.out.println("Known codes: " + String.valueOf(currentGCodeCount));
			}
		}
	}
	
	
	public void createAgent(GCode gc)
	{
		if (!paralyzed && !dancing && !BearDancing && gc != null)
		{
			Agent a = gc.create(this);
			if (a != null)
			{
				agents.add(a);
			}
		}
	}
	
	public void attack(Virologist vTo, Agent a)
	{
		boolean bDefenseSuccessful = false;
		if(a!=null && vTo!=null && standingField != null &&
				((!paralyzed && !dancing && !BearDancing) || (BearDancing && a.name.toLowerCase().contentEquals("beardance"))) &&  this.standingField==vTo.standingField)
		{
			bDefenseSuccessful = vTo.assaulted(this, a);
			
			if (!bDefenseSuccessful && vTo.getParalyzed() && !BearDancing)
			{
				if (wannaSteal(vTo))
				{
					vTo.stealBy(this);
				}
			}			
		}
	}
	
	private boolean wannaSteal(Virologist vTo) {
		boolean b = false;
		System.out.println("Wanna steal? (Y/N)");
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

	public void move(Field f, boolean bEmittedByUser)
	{
		if (!paralyzed && ((!dancing && !BearDancing) || !bEmittedByUser) && f != null
				&& standingField != null && standingField.getNeighbours().contains(f))
		{
			standingField.removeVirologist(this);
			f.addVirologist(this);
			System.out.println("Virologist moved to " + standingField.getID());
			f.arrive(this);
		}
	}
	
	public void stealBy(Virologist v)
	{
		if (v != null && !v.paralyzed && !v.dancing && !v.BearDancing && this.paralyzed
				&& standingField != null && this.standingField == v.standingField)
		{
			Inventory stealerInv = v.getInventory();
			
			String objectName = v.chooseObjectToSteal();
			
			boolean bEquippedFound = false;
			
			switch(objectName)
			{
			case "aminoacid":
				stealerInv.addAmino(inventory.removeAmino());
				break;
			case "nukleotid":
				stealerInv.addNukleo(inventory.removeNukleo());
				break;
			case "gloves":
				bEquippedFound = takeOffGloves(stealerInv);
				if (!bEquippedFound)
				{
					stealerInv.addGloves(inventory.removeGloves());
				}
				break;
			case "sack":
				bEquippedFound = takeOffSack(stealerInv);
				if (!bEquippedFound)
				{
					stealerInv.addSack(inventory.removeSack());
				}
				break;
			case "cloak":
				bEquippedFound = takeOffCloak(stealerInv);
				if (!bEquippedFound)
				{
					stealerInv.addCloak(inventory.removeCloak());
				}
				break;
			case "axe":
				bEquippedFound = takeOffAxe(stealerInv);
				if (!bEquippedFound)
				{
					stealerInv.addAxe(inventory.removeAxe());
				}
				break;
			}
			v.inventory=stealerInv;
		}
	}
	
	public Agent takeAgentToUse(String name)
	{
		Agent a = null;
		if (name != null) 
		{
			for (int i=0; i < agents.size(); i++)
			{
				if (agents.get(i).getName() == name)
				{
					a = agents.remove(i);
					break;
				}
			}
		}
		return a;
	}
	
	private boolean assaulted(Virologist vFr, Agent a)
	{
		boolean bDefenseSucceed = false;
		
		if (vFr == null || a == null) {
			return true;
		}
		
		if ((getHasEffectedAgent() && !a.getName().contentEquals("BearDance")) || (immunity && a.getName().contentEquals("BearDance")))
		{
			String agentName = "";
			if (immunity)
			{
				agentName = "Immunity";
			}
			if (dancing)
			{
				agentName = "VitusDance";
			}
			if (Oblivioned)
			{
				agentName = "Oblivion";
			}
			if(BearDancing)
			{
				agentName = "BearDance";
			}
			if (paralyzed)
			{
				agentName = "Paralysis";
			}
			System.out.println("Virologist under "+agentName+", cannot get effect.");
		}
		else
		{
			if (vFr != this)
			{
				bDefenseSucceed = defense(vFr, a);
			}
			
			if (!bDefenseSucceed)
			{
				a.setVirologistUnderEffect(this);
				a.setRemainingTime(a.getEffectTime());
			}
		}
		return bDefenseSucceed;
	}
	
	private String chooseObjectToSteal()
	{
		
		String input="";
		boolean bCorrectInput = false;
		if(!paralyzed && !dancing && !BearDancing)
		{
			System.out.println("What to steal? (Aminoacid/Nukleotid/Axe/Gloves/Cloak/Sack)");
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
						bCorrectInput = input.equals("aminoacid") || input.equals("nukleotid")
								|| input.equals("axe") || input.equals("gloves")
								|| input.equals("cloak") || input.equals("sack");
					} 
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return input;
	}
	
	public boolean canEquipEq()
	{
		boolean canEquip = (wornGloves.size() + wornSack.size() + wornCloak.size() + wornAxe.size()) < 3;
		if (!canEquip)
		{
			System.out.println("Too many equipment.");
		}
		return canEquip;
	}
	
	public boolean getHasEffectedAgent()
	{	
		return paralyzed || dancing || immunity || BearDancing || Oblivioned;
	}
}
