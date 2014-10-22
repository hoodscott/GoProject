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
    
    public void writeFile(String path, ArrayList <String> output)
    {
        BufferedWriter writer = null;
        
        try
        {
            File file = new File(path);
            
            if(!file.exists())
                file.createNewFile();
            else
            {
                System.err.println("WARNING: file already exists.");
            }
            
            System.out.println("--Writing file to "+file+" --");
            
            writer = new BufferedWriter(new FileWriter(file));
            
            for(int i = 0; i < output.length; i++)
            {   
                String temp = "Case #"+Integer.toString(i+1)+": "+output[i];
                writer.write(temp);
                writer.newLine();
            }
            writer.close();                        
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try{writer.close();}
            catch(IOException ex){ex.printStackTrace();}
        }
    }
}