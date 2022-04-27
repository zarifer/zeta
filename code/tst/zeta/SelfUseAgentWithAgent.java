package zeta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class SelfUseAgentWithAgent {

	@Test
	public void test() 
	{
		try {
			PrintStream fileOut = new PrintStream("./test13output.txt");
			InputStream fileIn = new FileInputStream("test13input.txt");
			System.setIn(fileIn);
			System.setOut(fileOut);
			
			
			Game.startGame(true);
	
			File outputfile = new File("test13output.txt");
			Scanner outputchecker = new Scanner(outputfile);
			String currentoutput;
			
			for(int i=0; i<11; i++)
				outputchecker.nextLine();
			
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Field Field1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("New Virologist V1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("V1 turn."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("GCode known for: Immunity"));
			for(int i=0; i<2; i++)
			{
				currentoutput=outputchecker.nextLine().trim();
				Assert.assertTrue(currentoutput.equals("Aminoacid found."));
			}
			for(int i=0; i<4; i++)
			{
				currentoutput=outputchecker.nextLine().trim();
				Assert.assertTrue(currentoutput.equals("Nukleotid found."));
			}
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Create agent successful."));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Attackable: V1"));
			currentoutput=outputchecker.nextLine().trim();
			Assert.assertTrue(currentoutput.equals("Immunity"));
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