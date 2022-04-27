package zeta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class UseAgentOnVirologistWhoHasCloak {

	@Test
	public void test() 
	{
		try {
			PrintStream fileOut = new PrintStream("./test5output.txt");
			InputStream fileIn = new FileInputStream("test5input.txt");
			System.setIn(fileIn);
			System.setOut(fileOut);
			
			
			Game.startGame(true);
			
			File outputfile = new File("test5output.txt");
			Scanner outputchecker = new Scanner(outputfile);
			String currentoutput;
			
			for(int i=0; i<11; i++)
				outputchecker.nextLine();
			
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Field Field1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Virologist V1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Virologist V2"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("V1 turn."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("GCode known for: Oblivion"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Aminoacid found."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Nukleotid found."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Create agent successful."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("V2 turn."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Cloak found."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Equip successful."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("V1 turn."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Attackable: V1 V2"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Oblivion"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Wanna defend with cloak? (Y/N)"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("V2 Oblivion1 infection defended, Cloak lost."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Game over."));
			
			
			outputchecker.close();

		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}