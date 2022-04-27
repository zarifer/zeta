package zeta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class MoveToShelter {

	@Test
	public void test() 
	{
		try {
			PrintStream fileOut = new PrintStream("./test17output.txt");
			InputStream fileIn = new FileInputStream("test17input.txt");
			System.setIn(fileIn);
			System.setOut(fileOut);
			
			
			Game.startGame(true);
			
			File outputfile = new File("test17output.txt");
			Scanner outputchecker = new Scanner(outputfile);
			
			String currentoutput;
			for(int i=0; i<11; i++)
			{
				outputchecker.nextLine();
			}
			
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Field Field1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Field Shelter1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Virologist V1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Field1 neighbors: Shelter1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Shelter1 neighbors: Field1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("V1 turn."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Select neighbor: Shelter1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Virologist moved to Shelter1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Wanna pick up? (Y/N)"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("What to pick up? (Axe/Gloves/Cloak/Sack)"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Axe added to inventory."));
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