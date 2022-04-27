package zeta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;


public class NextIfParalyzed {

	@Test
	public void test() 
	{
		try {
			PrintStream fileOut = new PrintStream("./test25output.txt");
			InputStream fileIn = new FileInputStream("test25input.txt");
			System.setIn(fileIn);
			System.setOut(fileOut);
			
			
			Game.startGame(true);
			
			File outputfile = new File("test25output.txt");
			Scanner outputchecker = new Scanner(outputfile);
			String currentoutput;
			
			for(int i=0; i<11; i++)
			{
				outputchecker.nextLine();
			}
			
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Field Field1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Virologist V1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Virologist V2"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("V1 under Paralysis, skip step."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("V2 turn."));
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