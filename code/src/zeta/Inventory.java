package zeta;
import java.util.ArrayList;

public class Inventory {
	private int maxMaterial;
	private static final int maxEquipment = 5;
	private ArrayList<Gloves> storedGloves;
	private ArrayList<Sack> storedSacks;
	private ArrayList<Cloak> storedCloaks;
	private ArrayList<Axe> storedAxes;
	private ArrayList<GCode> learntGCode;
	private ArrayList<Aminoacid> aminos;
	private ArrayList<Nukleotid> nukleotids;
	
	public Inventory()
	{
		init();
	}
	
	public Inventory(int aminos, int nukleotids,int storedGloves, int storedSacks, int storedCloaks, int storedAxes)
	{
		init();
		
		if(aminos+nukleotids <=  maxMaterial) {
			if(aminos > 0) {
				for(int i=0; i< aminos;i++) {
					this.aminos.add(new Aminoacid());
				}
			}
			if(nukleotids > 0) {
				for(int i=0; i< nukleotids;i++) {
					this.nukleotids.add(new Nukleotid());
				}
			}
		}
		
		if(storedGloves+storedCloaks+storedSacks+storedAxes <= maxEquipment) {
			if(storedGloves > 0) {
				for(int i=0; i< storedGloves;i++) {
					this.storedGloves.add(new Gloves());
				}
			}
			if(storedCloaks > 0) {
				for(int i=0; i< storedCloaks;i++) {
					this.storedCloaks.add(new Cloak());
				}
			}
			if(storedSacks > 0) {
				for(int i=0; i< storedSacks;i++) {
					this.storedSacks.add(new Sack());
				}
			}
			if(storedAxes > 0) {
				for(int i=0; i< storedAxes;i++) {
					this.storedAxes.add(new Axe());
				}
			}
		}
	}
	
	private void init()
	{
		storedGloves = new ArrayList<Gloves>();
		storedSacks = new ArrayList<Sack>();
		storedCloaks = new ArrayList<Cloak>();
		storedAxes = new ArrayList<Axe>();
		learntGCode = new ArrayList<GCode>();
		aminos = new ArrayList<Aminoacid>();
		nukleotids = new ArrayList<Nukleotid>();
		maxMaterial = 20;
	}
	
	public void setMaxMaterial(int maxMaterial)
	{
		if (this.maxMaterial > maxMaterial)
		{
			int overFlow = aminos.size() + nukleotids.size() - maxMaterial;
			if (overFlow > 0)
			{
				if (aminos.size() > nukleotids.size())
				{
					for (int i=0; i<overFlow; i++)
					{
						aminos.remove(0);
					}
				}
				else 
				{
					for (int i=0; i<overFlow; i++)
					{
						nukleotids.remove(0);
					}
				}
			}
		}
		
		this.maxMaterial = maxMaterial;
	}
	
	public int getMaxMaterial()
	{
		return maxMaterial;
	}
	
	public void addGloves(Gloves g)
	{
		if(g != null) {
			if (canAddEq())
			{
				storedGloves.add(g);
				System.out.println("Gloves added to inventory.");
			}
			else
			{
				System.out.println("Inventory full, drop Gloves.");
			}
		}
	}
	
	public void addSack(Sack s)
	{
		if(s != null) {
			if (canAddEq())
			{
				storedSacks.add(s);
				System.out.println("Sack added to inventory.");
			}
			else
			{
				System.out.println("Inventory full, drop Sack.");
			}
		}
	}
	
	public void addCloak(Cloak c)
	{
		if(c != null) {
			if (canAddEq())
			{
				storedCloaks.add(c);
				System.out.println("Cloak added to inventory.");
			}
			else
			{
				System.out.println("Inventory full, drop Cloak.");
			}
		}
	}
	
	public void addAxe(Axe a)
	{
		if(a != null) {
			if (canAddEq())
			{
				storedAxes.add(a);
				System.out.println("Axe added to inventory.");
			}
			else
			{
				System.out.println("Inventory full, drop Axe.");
			}
		}
	}
	
	public Gloves removeGloves()
	{
		Gloves g = null;
		if (storedGloves.size() > 0)
		{
			g = storedGloves.remove(0);
			System.out.println("Gloves found.");
		}
		else
		{
			System.out.println("Gloves not found.");
		}
		return g;
	}
	
	public Sack removeSack()
	{
		Sack s = null;
		if (storedSacks.size() > 0)
		{
			s = storedSacks.remove(0);
			System.out.println("Sack found.");
		}
		else
		{
			System.out.println("Sack not found.");
		}
		return s;
	}
	
	public Cloak removeCloak()
	{
		Cloak c = null;
		if (storedCloaks.size() > 0)
		{
			c = storedCloaks.remove(0);
			System.out.println("Cloak found.");
		}
		else
		{
			System.out.println("Cloak not found.");
		}
		return c;
	}
	
	public Axe removeAxe() {
		Axe a=null;
		if(storedAxes.size() > 0) {
			a = storedAxes.remove(0);
			System.out.println("Axe found.");
			if(a==null)
				System.out.println("null");
		}
		else
		{
			System.out.println("Axe not found.");
		}
		return a;
	}
	
	//it is enough to give over the GCode here, don't need to use the Lab object inside this function
	public int learnGCode(GCode g)
	{
		if (!learntGCode.contains(g) && g != null)
		{
			learntGCode.add(g);
		}
		
		//instead of returning a boolean value representing, if we learnt a new code,
		//we return the size of lertGCode, which can be used directly for checking if we have all code learnt.
		return learntGCode.size();
	}
	
	public void forgetGCodes()
	{
		learntGCode.clear();
	}
	
	public void addAmino(Aminoacid am)
	{
		if(am != null) {
			if (canAddMaterial())
			{
				aminos.add(am);
				System.out.println("Aminoacid added to inventory.");
			}
			else
			{
				System.out.println("Inventory full, drop Aminoacid.");
			}
		}
	}
	
	public void addNukleo(Nukleotid n)
	{
		if(n != null) {
			if (canAddMaterial())
			{
				nukleotids.add(n);
				System.out.println("Nukleotid added to inventory.");
			}
			else
			{
				System.out.println("Inventory full, drop Nukleotid.");
			}
		}
	}
	
	public Aminoacid removeAmino()
	{
		Aminoacid a = null;
		if (aminos.size() > 0)
		{
			a = aminos.remove(0);
			System.out.println("Aminoacid found.");
		}
		else
		{
			System.out.println("Aminoacid not found.");
		}
		return a;
	}
	
	public Nukleotid removeNukleo()
	{
		Nukleotid n = null;
		if (nukleotids.size() > 0)
		{
			n = nukleotids.remove(0);
			System.out.println("Nukleotid found.");
		}
		else
		{
			System.out.println("Nukleotid not found.");
		}
		return n;
	}
	
	public int getStoredAminoCount()
	{
		return aminos.size();
	}
	
	public int getStoredNukleotidCount()
	{
		return nukleotids.size();
	}
	
	public boolean canAddEq() 
	{
		return storedGloves.size() + storedSacks.size() + storedCloaks.size() + storedAxes.size() < maxEquipment;
	}
	
	private boolean canAddMaterial()
	{
		return aminos.size() + nukleotids.size() < maxMaterial;
	}
	
	public int getStoredGlovesCount() {
		return storedGloves.size();
	}
	
	public int getStoredAxesCount() {
		return storedAxes.size();
	}
	
	public int getStoredCloaksCount() {
		return storedCloaks.size();
	}
	
	public int getStoredSacksCount() {
		return storedSacks.size();
	}
	
	public int getLearntGCodeCount() {
		return learntGCode.size();
	}
	
	public ArrayList<GCode> getLearntGCode(){
		return learntGCode;
	}
}
