package zeta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class MoveToStorageWhereParalyzedVirologistFound {

	@Test
	public void test() 
	{
		try {
			PrintStream fileOut = new PrintStream("./test21output.txt");
			InputStream fileIn = new FileInputStream("test21input.txt");
			System.setIn(fileIn);
			System.setOut(fileOut);
			
			
			Game.startGame(true);
			
			File outputfile = new File("test21output.txt");
			Scanner outputchecker = new Scanner(outputfile);
			String currentoutput;
			
			for(int i=0; i<11; i++)
				outputchecker.nextLine();
			
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Field Field1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Field Storage1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Field1 neighbors: Storage1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Storage1 neighbors: Field1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Virologist V1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Virologist V2"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("V1 turn."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Select neighbor: Storage1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Virologist moved to Storage1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("V2 paralyzed. Wanna steal? (Y/N)"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("What to steal? (Aminoacid/Nukleotid/Axe/Gloves/Cloak/Sack)"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Axe not found."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Wanna pick up? (Y/N)"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("What to pick up? (Aminoacid/Nukleotid)"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Aminoacid added to inventory."));
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
