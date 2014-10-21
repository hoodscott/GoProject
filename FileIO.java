import java.io.*;
import java.util.ArrayList;
        
public class FileIO 
{   
	private static final DEFINPUT = "";
	private static final DEFOUTPUT = "";
	private static final DEFLOGOUTPUT = "";

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

    public ArrayList<ArrayList<String>> readFile(String path)
    {
        BufferedReader reader = null;
        ArrayList<String> rawInput = new ArrayList<ArrayList<String>>();
        
        try
        {
            reader = new BufferedReader(new FileReader(path));
            String currentLine;
            
            //System.out.println("--Reading in file from "+path+" --");
            //System.out.println("--Reading in lines--");
                          
            while((currentLine = reader.readLine()) != null)
                    rawInput.add(currentLine);
        }
        
        catch(IOException ex)
        {
            ex.printStackTrace();                    
        }
        
        finally
        {
            try{reader.close();}
            catch(IOException ex){ex.printStackTrace();}
            System.out.println("--Reading Complete--");      
        }
        return rawInput;
    }
    
    public void writeFileText(String path, String[] output)
    {
        BufferedWriter writer = null;
        
        try
        {
            File file = new File(path);
            
            if(!file.exists())
            {
                file.createNewFile();
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
            System.out.println("--Writing Complete--");  
            System.out.println("Execution time: "+(System.currentTimeMillis()-startTimeMs)+" ms");
        }
    }
}