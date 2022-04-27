package zeta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;


public class MoveToInfectedLab {
	@Rule
	public ErrorCollector errorCollector= new ErrorCollector();
	
	@Test
    public void test() 
    {
		String inputPath = "./MoveToInfectedLab_Input.txt";
		String outputPath = "./MoveToInfectedLab_Output.txt";
		String comparePath = "./MoveToInfectedLab_Compare.txt";
		try {
        	
            PrintStream fileOut = new PrintStream(outputPath);
            InputStream fileIn = new FileInputStream(inputPath);
            System.setIn(fileIn);
            System.setOut(fileOut);
            Game.startGame(true);
            Scanner scOutPut = new Scanner(new File(outputPath));
            Scanner scCompare = new Scanner(new File(comparePath));
            int n = 0;
            while (scOutPut.hasNextLine() && scCompare.hasNextLine())
            {
            	n+=1;
            	String outputLine = scOutPut.nextLine();
            	String compareLine = scCompare.nextLine();
            	if (!outputLine.contentEquals(compareLine))
            	{
            		errorCollector.addError(new Throwable("\n" + String.valueOf(n) + ". line comparsion failed.\r\nExpected line: "+compareLine+"\r\nReal line: "+outputLine));
            	}
            }
            if (scCompare.hasNextLine() && !scOutPut.hasNextLine()) {
            	errorCollector.addError(new Throwable("More output line, as expected."));
            }
            if (scOutPut.hasNextLine() && !scCompare.hasNextLine()) {
            	errorCollector.addError(new Throwable("Less output line, as expected."));
            }
            
            scOutPut.close();
            scCompare.close();
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
    }

}