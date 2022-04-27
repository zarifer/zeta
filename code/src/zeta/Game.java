package zeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Game {
	
	private static final int maxGCode = 4;
	private static int activeVirologist=-1;
	private static boolean moved;
	private static ArrayList<Field> fields = new ArrayList<Field>();
	private static ArrayList<Virologist> virologists = new ArrayList<Virologist>();
	private static boolean inProgress=false;
	private static BufferedReader in;
	
	public static BufferedReader getBufferedReader() {
		return in;
	}
	
	public static boolean setBufferedReader(BufferedReader bf)
	{
		boolean bSuccess = false;
		if (in == null && bf != null)
		{
			in = bf;
			bSuccess = true;
		}
		return bSuccess;
	}
	
	public static void startGame(boolean bTestMode) {
		Field.setTestMode(bTestMode);
		Agent.setTestMode(bTestMode);
		Equipment.setTestMode(bTestMode);
		inProgress = true;
		System.out.println("Commands:");
		System.out.println("\tnext");
		System.out.println("\tcreate Field <field_objectname>");
		System.out.println("\tcreate Virologist <fieldID> <immunity> <paralyzed> <dancing> <beardancing> <aminoacidNR> <nukleotidNR> <glovesNR> <sackNR> <cloakNR> <axeNR> <GCode_name1> <GCode_name2> ... <GCode_nameN>");
		System.out.println("\tsetneighbor <fieldID1> <fieldID2>");
		System.out.println("\tmove");
		System.out.println("\tequip <first character>");
		System.out.println("\ttakeoff <first character>");
		System.out.println("\tcreateagent");
		System.out.println("\tattack");
		System.out.println("\tquit");
		in = new BufferedReader(new InputStreamReader(System.in));
		boolean isNext=false;
		try {
			while(!isNext){
				isNext=doCommand(in.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (inProgress)
		{
			next();
		}
	}
	
	
	public static int getMaxGCode()
	{
		return maxGCode;
	}
	
	public static boolean getMoved() {
		return moved;
	}
	
	public static Virologist getActiveVirologist() {
		return virologists.get(activeVirologist);
	}
	
	public static void removeVirologist(Virologist v) {
		virologists.remove(v);
	}
	
	public static void endGame(Virologist v)
	{
		
		for (int i = 0; i < virologists.size(); i++)
		{
			virologists.get(i).die();
		}
		Timer.clearSteppable();
		inProgress=false;
		System.out.print("Game over");
		if (v != null)
		{
			System.out.println(", " + v.getID() + " won.");
		}
		else
		{
			System.out.println(".");
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Field> getFields() 
	{
		return fields;
	}

	public static void setFields(ArrayList<Field> field)
	{
		fields = field;
	}
	
	private static void next() {
		if (virologists.size() == 0)
		{
			return;
		}
		activeVirologist = (++activeVirologist) % virologists.size(); 
		if(activeVirologist ==0) {
			Timer.tick();
		}
		Virologist v = virologists.get(activeVirologist);
		if(v.getParalyzed())
			System.out.println(v.getID()+" under Paralysis, skip step.");
		else if(v.getDancing())
			System.out.println(v.getID()+" under VitusDance, skip step.");
		else if(v.getBearDancing())
			System.out.println(v.getID()+" under BearDance, skip step.");
		else
		{
			System.out.println(v.getID()+" turn.");
			moved = false;
			Timer.startRound();
			boolean bFinished = false; 
			try {
				while (!bFinished && Timer.getRemainingTime() > 0)
				{
	
					while(Timer.getRemainingTime() > 0 && !in.ready());
					
					if (in.ready()) {
						bFinished = doCommand(in.readLine());
					} 	
					else {
						bFinished = true;
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(inProgress) {
			next();
		}
		
	}
	
	private static boolean doCommand(String command) {
		command=command.toLowerCase();
		String[] c = command.split(" ");
		if(c.length > 0) {
			switch(c[0]) {
		
				case "create":
					if(c.length>1) {
						if(c[1].equals("field")) {
							createField(c);
						}
						else if(c[1].equals("virologist")) {
							createVirologist(c);
						}
					}
					break;
					
				case "next": 
					return true;
					
				case "setneighbor":
					setNeighbor(c);
					break;
					
				case "move":
					move(c);
					break;
				
				case "equip":
					equip(c);
					break;
					
				case "takeoff":
					takeOff(c);
					break;
					
				case "createagent":
					createAgent(c);
					break;
					
				case "attack":
					attack(c);
					break;
				case "quit":
					Game.endGame(null);
					return true;
				default:
					wtf();
					break;
			
			}
		}
		return false;
	}
	
	private static void wtf() {
		System.out.println("Unknown command.");
	}
	
	private static boolean isCorrectGCode(String input) {
		return input.equals("oblivion") || input.equals("immunity")
				|| input.equals("vitusdance") || input.equals("paralysis");
	}
	
	private static String selectGCodeForLab() {
		String input="";
		boolean bCorrectInput = false;
		try {
			while(!bCorrectInput) 
			{
				System.out.println("What type of genetic code should be on this labor? (Oblivion/Immunity/VitusDance/Paralysis)");
			
				input=in.readLine().toLowerCase();
				bCorrectInput = isCorrectGCode(input);
				

			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	private static void createField(String[] c) {
		if(c.length == 3) {
			switch(c[2]) {
			
				case "field":
					fields.add(new Field());
					break;
					
				case "lab":
					fields.add(new Lab(selectGCodeForLab()));
					break;
					
				case "infectedlab":					
					fields.add(new InfectedLab(selectGCodeForLab()));
					break;
					
				case "storage":
					fields.add(new Storage());
					break;
				
				case "shelter":
					fields.add(new Shelter());
					break;
				default:
					wtf();
					break;
			}
			if (fields.size() > 0)
			{
				System.out.println("New Field "+fields.get(fields.size()-1).getID());
			}
			
		}
		else
			wtf();
	}
	
	private static boolean strToBool(String str) {
		if(str.equals("1") || str.toLowerCase().equals("true") || str.toLowerCase().equals("y") || str.toLowerCase().equals("yes")) {
			return true;
		}
		else
			return false;
	}
	
	public static Field getFieldFromID(String ID) {
		Field field = null;
		for(int i=0; i < fields.size();i++) {
			if(fields.get(i).getID().toLowerCase().equals(ID)){
				field=fields.get(i);
				break;
			}
		}
		return field;
	}
	
	private static void createVirologist(String[] c) {
		if(c.length >=3) {
			Field field = getFieldFromID(c[2]);
			if(field != null) {
				if(c.length > 3) {
					if(c.length >= 13) {
						boolean bError = false;
						ArrayList<GCode> gc = new ArrayList<GCode>();
						if(c.length > 13) {
							int eq=0, mat=0;
							try {
								mat+=Integer.valueOf(c[7]).intValue() +Integer.valueOf(c[8]);
								if(mat > 20) {
									System.out.println("Virologist not created, too many material.");
									return;
								}
								eq+=Integer.valueOf(c[9]).intValue()+Integer.valueOf(c[10]).intValue()+Integer.valueOf(c[11]).intValue()
										+Integer.valueOf(c[12]).intValue();
								if(eq > 5) {
									System.out.println("Virologist not created, too many equipment.");
									return;
								}
							}catch(NumberFormatException nfe) {
								nfe.printStackTrace();
								bError=true;
							}
							for(int i=13; i < c.length; i++ ) {
								bError=!isCorrectGCode(c[i]);
								if(bError)
									break;
								else {
									GCode gcode = null;
									switch(c[i]) {
										case "oblivion":
											gcode = new GCode("Oblivion");
											break;
										case "immunity":
											gcode = new GCode("Immunity");
											break;
										case "paralysis":
											gcode = new GCode("Paralysis");
											break;
										case "vitusdance":
											gcode = new GCode("VitusDance");
											break;
										}
										gc.add(gcode);
								}
							}
						}
						if(!bError) {
							try {
								boolean immunity = strToBool(c[3]);
								boolean paralyzed = strToBool(c[4]);
								boolean dancing = strToBool(c[5]);
								boolean beardancing = strToBool(c[6]);
								virologists.add(new Virologist(field,immunity,paralyzed,dancing,beardancing,Integer.valueOf(c[7]).intValue()
										,Integer.valueOf(c[8]).intValue(),Integer.valueOf(c[9]).intValue(),Integer.valueOf(c[10]).intValue(),Integer.valueOf(c[11]).intValue()
										,Integer.valueOf(c[12]).intValue(),gc));
								System.out.println("New Virologist " + virologists.get(virologists.size()-1).getID());
								Agent a = null;
								if(immunity)
								{
									a = new Immunity();
								}
								if(paralyzed)
								{
									a = new Paralysis();
								}
								if(dancing)
								{
									a = new VitusDance();
								}
								if(beardancing)
								{
									a = new BearDance();
								}
								if(a != null)
								{
									a.setVirologistUnderEffect(virologists.get(virologists.size()-1));
								}
							}
							catch(NumberFormatException nfe) {
								nfe.printStackTrace();
								wtf();
							}
						}
						else
							wtf();
							
					}
					else
						wtf();
				}
				else
				{
					virologists.add(new Virologist(field));
					System.out.println("New Virologist " + virologists.get(virologists.size()-1).getID());
				}
			}
			else {
				System.out.println("Virologist not created, invalid Field Id");
			}
		}
		else
			wtf();
	}

	
	private static void setNeighbor(String[] c) {
		if(c.length ==3) {
			Field field1 = getFieldFromID(c[1]);
			Field field2 = getFieldFromID(c[2]);
			
			if(field1 != null && field2 != null) {
				Field.setNeighbours(field1, field2);
				printNeighbors(field1);
				printNeighbors(field2);
			}
			else
				System.out.println("Invalid Field Id");
		}
		else
			wtf();
	}

	public static void printNeighbors(Virologist v) {
		System.out.print("Select neighbor: ");
		ArrayList<Field> neighbors = v.getStandingField().getNeighbours();
		for(int i=0; i < neighbors.size();i++) {
			System.out.print(neighbors.get(i).getID()+" ");
		}
		System.out.println();
	}
	
	public static void printNeighbors(Field f)
	{
		System.out.print(f.getID() + " neighbors: ");
		for(Field nf : f.getNeighbours()) {
			System.out.print(nf.getID()+" ");
		}
		System.out.println();
	}

	private static void move(String[] c) {
		if(activeVirologist > 0 || activeVirologist >= virologists.size()) {
			System.out.println("Invalid active virologist, you can initialize active virologist with calling the \"next\" command");
			return;
		}
		else if(moved)
		{
			System.out.println("Moving is allowed only one time in a round.");
		}else {
			if(c.length ==1) {
				if(activeVirologist >= 0) {
					Virologist v= virologists.get(activeVirologist);
					if(v.getParalyzed())
						System.out.println("Virologist paralyzed. Cannot perform action.");
					else if(v.getDancing())
						System.out.println("Virologist vitus dancing. Cannot perform action.");
					else if(v.getBearDancing())
						System.out.println("Virologist bear dancing. Cannot perform action.");
					else {
						Field field =null;
						try {
							while (Timer.getRemainingTime() > 0)
							{
								printNeighbors(v);
	
								while(Timer.getRemainingTime() > 0 && !in.ready());
								
								if (in.ready()) {
									field = getFieldFromID(in.readLine().toLowerCase());
									if(field!=null) {
										v.move(field, true);
										moved = true;
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
				}
				else
					System.out.println("No active Virologist. Please type 'next' to start first round.");
				
				
		}
		else
			wtf();
		}
		
	}

	private static void equip(String[] c) {
		if(activeVirologist == -1) {
			System.out.println("The game is not started yet.\nYou can start it with the \"next\" command");
			return;
		}
		if(c.length ==2) {
			if(virologists.get(activeVirologist).canEquipEq()) {	
				Inventory inv = virologists.get(activeVirologist).getInventory();
				int oldStoredCount = 0;
				int newStoredCount = 0;
				if(c[1].toLowerCase().equals("a") && inv.getStoredAxesCount() > 0) {
					oldStoredCount = inv.getStoredAxesCount(); 
					virologists.get(activeVirologist).equipAxe();
					inv = virologists.get(activeVirologist).getInventory();
					newStoredCount = inv.getStoredAxesCount(); 
				}
				else if(c[1].toLowerCase().equals("g") && inv.getStoredGlovesCount() > 0) {
					oldStoredCount = inv.getStoredGlovesCount();
					virologists.get(activeVirologist).equipGloves();
					inv = virologists.get(activeVirologist).getInventory();
					newStoredCount = inv.getStoredAxesCount(); 
				}
				else if(c[1].toLowerCase().equals("c") && inv.getStoredCloaksCount() > 0) {
					oldStoredCount = inv.getStoredCloaksCount();
					virologists.get(activeVirologist).equipCloak();
					inv = virologists.get(activeVirologist).getInventory();
					newStoredCount = inv.getStoredAxesCount(); 
				}
				else if(c[1].toLowerCase().equals("s") && inv.getStoredSacksCount() > 0) {
					oldStoredCount = inv.getStoredSacksCount();
					virologists.get(activeVirologist).equipSack();
					inv = virologists.get(activeVirologist).getInventory();
					newStoredCount = inv.getStoredAxesCount(); 
				}
				else
					System.out.println("Equipment not found in inventory.");
				if (oldStoredCount > newStoredCount)
				{
					System.out.println("Equip successful.");
				}
			}	
		}
		else
			wtf();
		
	}


	private static void takeOff(String[] c) {
		if(activeVirologist == -1) {
			System.out.println("The game is not started yet.\nYou can start it with the \"next\" command");
			return;
		}
		if(c.length ==2) {
			switch(c[1].toLowerCase()) {
				
				case "a":
					virologists.get(activeVirologist).takeOffAxe();
					break;
					
				case "g":
					virologists.get(activeVirologist).takeOffGloves();
					break;
					
				case "c":
					virologists.get(activeVirologist).takeOffCloak();
					break;
					
				case "s":
					virologists.get(activeVirologist).takeOffSack();
					break;
			}
		}
		else
			wtf();
	}

	private static GCode getGCodeFromName(String name) {
		Inventory inv = virologists.get(activeVirologist).getInventory();
		ArrayList<GCode> gcs = inv.getLearntGCode();
		GCode gc=null;
		for(int i=0; i < inv.getLearntGCodeCount();i++) {
			if(gcs.get(i).getGCName().toLowerCase().equals(name)){
				gc=gcs.get(i);
				break;
			}
		}
		return gc;
	}
	

	private static void createAgent(String[] c) {
		if(activeVirologist == -1) {
			System.out.println("The game is not started yet.\nYou can start it with the \"next\" command");
			return;
		}
		if(c.length ==1) {
			Inventory inv = virologists.get(activeVirologist).getInventory();
			if(inv.getLearntGCodeCount() > 0) {
				ArrayList<GCode> gcs = inv.getLearntGCode();
				GCode gc =null;
				try {
					System.out.print("GCode known for: ");
					while (Timer.getRemainingTime() > 0)
					{
						for(int i=0; i < inv.getLearntGCodeCount();i++) {
							System.out.print(gcs.get(i).getGCName()+" ");
						}
						System.out.println();

						while(Timer.getRemainingTime() > 0 && !in.ready());
						
						if (in.ready()) {
							gc = getGCodeFromName(in.readLine().toLowerCase());
							if(gc!=null) {
								virologists.get(activeVirologist).createAgent(new GCode(gc.getGCName()));
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
				if(gc==null) {
					wtf();
				}
			}
			else
				System.out.println("No GCode known, can’t create agent.");
		}
		else
			wtf();
	}


	private static void printAttackableVirologists(Virologist v) {
		System.out.print("Attackable: ");
		ArrayList<Virologist> viros = v.getStandingField().getVirologists();
		for(int i=0; i < viros.size();i++) {
			System.out.print(viros.get(i).getID()+" ");
		}
		System.out.println();
	}
	
	private static Virologist getVirologistFromID(String ID) {
		Virologist viro=null;
		for(int i=0; i < virologists.size();i++) {
			if(virologists.get(i).getID().toLowerCase().equals(ID.toLowerCase())){
				viro=virologists.get(i);
				break;
			}
		}
		
		return viro;
	}
	
	private static void printVirologistsAgents(Virologist v) {
		ArrayList<Agent> agents = v.getAgents();
		for(int i=0; i < agents.size();i++) {
			System.out.println(agents.get(i).getName());
		}
	}
	
	private static Agent getAgentsFromVirologistByName(String name) {
		ArrayList<Agent> agents = virologists.get(activeVirologist).getAgents();
		Agent a=null;
		for(int i=0; i < agents.size();i++) {
			if(agents.get(i).getName().toLowerCase().equals(name)){
				a=agents.get(i);
				break;
			}
		}
		return a;
	}
	
	private static void attack(String[] c) {
		if(activeVirologist == -1) {
			System.out.println("The game is not started yet.\nYou can start it with the \"next\" command");
			return;
		}
		if(c.length ==1) {
			if(virologists.get(activeVirologist).getStandingField().getVirologists().size() > 0) {
				Virologist victim = null;
				Agent ag = null;
				try {
					while (Timer.getRemainingTime() > 0)
					{
						if(victim==null)	
							printAttackableVirologists(virologists.get(activeVirologist));
	
						while(Timer.getRemainingTime() > 0 && !in.ready());
						
						if (in.ready()) {
							victim = getVirologistFromID(in.readLine().toLowerCase());
							if(victim!=null) {
								if(virologists.get(activeVirologist).getAgents().size()>0) {
											printVirologistsAgents(virologists.get(activeVirologist));
											
											while(Timer.getRemainingTime() > 0 && !in.ready());
											
											if (in.ready()) {
												ag = getAgentsFromVirologistByName(in.readLine().toLowerCase());
												if(ag!=null) {
													virologists.get(activeVirologist).attack(victim,ag);
													return;
												}
											} 	
											else {
												return;
											}
										
								}
								else {
									System.out.println("No Agent available.");
									break;
								}
							}
							else
								wtf();
						} 	
						else {
							return;
						}
					}
						
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else
			wtf();
	}
}
