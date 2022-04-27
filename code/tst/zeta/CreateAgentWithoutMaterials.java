package zeta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class CreateAgentWithoutMaterials {


	@Test
	public void test() 
	{
		try {
			PrintStream fileOut = new PrintStream("./test9output.txt");
			InputStream fileIn = new FileInputStream("test9input.txt");
			System.setIn(fileIn);
			System.setOut(fileOut);
			

			
			
			/*
			 * New Field Field1
New Virologist V1
V1 turn.
GCode known for: Paralysis 
Not enough material.
			 */
			
			
			Game.startGame(true);
			
			File outputfile = new File("test9output.txt");
			Scanner outputchecker = new Scanner(outputfile);
			
			String currentoutput;
			for(int i=0; i<11; i++)
				currentoutput = outputchecker.nextLine();
			
			currentoutput=outputchecker.nextLine();
			Assert.assertTrue(currentoutput.equals("New Field Field1"));
			currentoutput=outputchecker.nextLine();
			Assert.assertTrue(currentoutput.equals("New Virologist V1"));
			currentoutput=outputchecker.nextLine();
			Assert.assertTrue(currentoutput.equals("V1 turn."));
			currentoutput=outputchecker.nextLine();
			currentoutput=currentoutput.trim();
			Assert.assertTrue(currentoutput.equals("GCode known for: Paralysis"));
			currentoutput=outputchecker.nextLine();
			Assert.assertTrue(currentoutput.equals("Not enough material."));
			
			outputchecker.close();

		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}