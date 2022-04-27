package zeta;

import static org.junit.Assert.*;

import org.junit.Test;

import zeta.Game;

import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;

public class UseAgentOnVirologistButVirologistUnderEffect
{

	@Test
	public void test() 
	{
		 try {
	            PrintStream fileOut = new PrintStream("./UseAgentOnVirologistButVirologistUnderEffect_Out.txt");
	            InputStream fileIn = new FileInputStream("UseAgentOnVirologistButVirologistUnderEffect_In.txt");
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