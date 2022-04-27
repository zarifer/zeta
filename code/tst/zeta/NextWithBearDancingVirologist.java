package zeta;

import static org.junit.Assert.*;

import org.junit.Test;

import zeta.Game;

import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;

public class NextWithBearDancingVirologist
{

	@Test
	public void test() 
	{
		 try {
	            PrintStream fileOut = new PrintStream("./NextWithBearDancingVirologist_Out.txt");
	            InputStream fileIn = new FileInputStream("NextWithBearDancingVirologist_In.txt");
	            System.setIn(fileIn);
	            System.setOut(fileOut);

	            Game.startGame(true);
	        } catch (FileNotFoundException e) 
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}

}