package zeta;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;


public class UseAgentOnVirologistWithoutAgent {

	@Test
	public void test() 
	{
		try {
			PrintStream fileOut = new PrintStream("./test1output.txt");
			InputStream fileIn = new FileInputStream("test1input.txt");
			System.setIn(fileIn);
			System.setOut(fileOut);
			
			
			Game.startGame(true);
			
			File outputfile = new File("test1output.txt");
			Scanner outputchecker = new Scanner(outputfile);
			
			
			
			
			String currentoutput;
			for(int i=0; i<11; i++)
				 outputchecker.nextLine();
			
			currentoutput=outputchecker.nextLine();
			Assert.assertTrue(currentoutput.equals("New Field Field1"));
			currentoutput = outputchecker.nextLine();
			Assert.assertEquals(currentoutput, "New Virologist V1");
			currentoutput = outputchecker.nextLine();
			Assert.assertEquals(currentoutput, "New Virologist V2");
			currentoutput = outputchecker.nextLine();
			Assert.assertEquals(currentoutput, "V1 turn.");
			currentoutput = outputchecker.nextLine();
			currentoutput=currentoutput.trim();
			Assert.assertEquals(currentoutput, "GCode known for: Paralysis");
			currentoutput = outputchecker.nextLine();
			Assert.assertEquals(currentoutput, "Aminoacid found.");
			currentoutput = outputchecker.nextLine();
			Assert.assertEquals(currentoutput, "Nukleotid found.");
			currentoutput = outputchecker.nextLine();
			Assert.assertEquals(currentoutput, "Create agent successful.");
			currentoutput = outputchecker.nextLine();
			currentoutput=currentoutput.trim();
			Assert.assertEquals(currentoutput, "Attackable: V1 V2");
			currentoutput = outputchecker.nextLine();
			Assert.assertEquals(currentoutput, "Paralysis");
			currentoutput = outputchecker.nextLine();
			Assert.assertEquals(currentoutput, "V2 Paralysis1 infection succesful.");
			
			outputchecker.close();
			

		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}