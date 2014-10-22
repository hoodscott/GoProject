import java.io.*;
import java.util.ArrayList;
        
public class FileIO 
{   
	private static final String DEFINPUT = "";
	private static final String DEFOUTPUT = "";
	private static final String DEFLOGOUTPUT = "";

	public static void writeBoard(Board board)
	{

	}

	public static void writeBoard(Board board, String path)
	{

	}

	public static Board readBoard(Board board)
	{

	}

	public static Board readBoard(Board board, String path)
	{

	}

	public static void writeToLog(String text)
	{

	}

	public static void writeToLog(String text, String path)
	{

	}

    //General read method - returns ArrayList of lines
    public ArrayList<String> readFile(String path)
    {
        BufferedReader reader = null;
        ArrayList<String> rawInput = new ArrayList<>();
        
        try
        {
            reader = new BufferedReader(new FileReader(path));
            String currentLine;
            
            System.out.println("--Reading in file from "+path+" --");
                          
            while((currentLine = reader.readLine()) != null)
                    rawInput.add(currentLine);
        }
        
        catch(IOException ex)
        {
            System.err.println("The program could not read the file at "+path);                   
        }
        
        finally
        {
            try{reader.close();}

            catch(IOException ex)
            {
                System.err.println("There was an error closing the reading stream.")
            }
            System.out.println("--Reading Complete--");      
        }
        return rawInput;
    }
    
    //General write method
    public void writeFile(String path, String output)
    {
        BufferedWriter writer = null;
        String p = path;
        
        try
        {
            File file = new File(path);
            
            if(!file.exists())
            {
                file.createNewFile();
                System.out.println("--Writing file to "+p+" --");
            }
            else
            {
                System.err.println("WARNING: file already exists.");
                p = adjustPath(path);
                System.out.println("Writing to "+p+" instead.");
            }
                      
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(output);
            writer.close();                        
        }
        catch(IOException ex)
        {
            System.err.println("The program could not write the file to "+p)
        }
        finally
        {
            try{writer.close();}
            catch(IOException ex)
            {
                System.err.println("There was an error closing the writing stream.")
            }
        }
    }

    //Fancy method that adds a number to a path.
    public String adjustPath(String original)
    {
        String numbers = "";
        boolean isDigit = true;
        for(int i = original.length() - 1; i >= 0 && isDigit; i--)
        {
            if(Character.isDigit(original.charAt(i)))
                numbers += original.charAt(i);
            else
                isDigit = false;
        }

        if(numbers.length() == 0)
            return original + "1";
        else
        {
            int count = Integer.parseInt(numbers) + 1;
            String sub = original.substring(0,sub.length() - numbers.length() + 1);
            return sub + Integer.toString(count);
        }
    }
}