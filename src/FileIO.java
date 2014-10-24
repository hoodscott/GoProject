import java.io.*;
import java.util.ArrayList;
        
public class FileIO 
{   
    //Default paths
    private static String relativePath = System.getProperty("user.dir");
	private static final String DEFINPUT = relativePath+"\\saveData\\boards\\board";
	private static final String DEFOUTPUT = relativePath+"\\saveData\\boards\\board";
	private static final String DEFLOGOUTPUT = relativePath+"\\saveData\\logs\\log";

    //Default Board writing method
	public static void writeBoard(Board board){writeBoard(board,DEFOUTPUT);}

    //Board writing method with given path
	public static void writeBoard(Board board, String path)
	{
        String content = "";
        try
        {
            int w = board.getWidth(); int h = board.getHeight();
            char[][] cBoard = new char[w][h];
            content += w+" "+h+ '\n';

            for(int i = 0; i < w; i++)
                for(int j = 0; j < h;j++)
                    cBoard[i][j] = translate(board.get(i,j));

            for(int i = 0; i < w; i++)
                content += new String(cBoard[i]) + '\n';

            writeFile(content, path);
        }
        catch(BoardFormatException badBoard){System.err.println("The board to save contains illegal data. Board not saved");}
	}

    //Default Board reading method
	public static Board readBoard(){return readBoard(DEFINPUT);}

    //Board reading method for a given path
	public static Board readBoard(String path)
	{
        ArrayList<String> lines = readFile(path);
        try{
            if(lines.size() < 1)
                throw new BoardFormatException();
        }
        catch(BoardFormatException badBoard){System.err.println("The given file does not adhere to the Board format");}

	}

	public static void writeToLog(String text)
	{

	}

	public static void writeToLog(String text, String path)
	{

	}

    //General read method - returns ArrayList of lines
    private ArrayList<String> readFile(String path)
    {
        BufferedReader reader = null;
        ArrayList<String> rawInput = new ArrayList<>();
        String currentLine;
        
        try
        {
            reader = new BufferedReader(new FileReader(path));
            System.out.println("--Reading in file from "+path+" --");
                          
            while((currentLine = reader.readLine()) != null)
                    rawInput.add(currentLine);
        }
        
        catch(IOException ex){System.err.println("The program could not read the file at "+path);}
        
        finally
        {
            try{if(reader != null)reader.close();}

            catch(IOException ex){System.err.println("There was an error closing the reading stream.");}
            System.out.println("--Reading Complete--");      
        }
        return rawInput;
    }
    
    //General write method
    private static void writeFile(String output, String path)
    {
        BufferedWriter writer = null;
        String p = path; //This adds the relative path of where Main is located.
        
        try
        {
            File file = new File(p);
            
            if(file.exists())
            {
                System.err.println("WARNING: file already exists.");
                while(file.exists())
                {
                    p = adjustPath(p);
                    file = new File(p);
                }
                System.err.println("Writing to "+p+" instead."); 
            }

            file.createNewFile();
                      
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(output);
            writer.close();                        
        }
        catch(IOException ex){System.err.println("The program could not write the file to "+p);}
        finally
        {
            try{if(writer != null)writer.close();}
            catch(IOException ex){System.err.println("There was an error closing the writing stream.");}
        }
    }

    //Fancy method that adds a number to a path.
    private static String adjustPath(String original)
    {
        String numbers = "";
        boolean isDigit = true;

        for(int i = original.length() - 1; i >= 0 && isDigit; i--)
        {
            if(Character.isDigit(original.charAt(i)))
                numbers = original.charAt(i) + numbers;
            else
                isDigit = false;
        }

        if(numbers.length() == 0)
            return original + "1";
        else
        {   
            int count = Integer.parseInt(numbers) + 1;
            //System.out.println(count);
            String sub = original.substring(0,original.length() - numbers.length());
            return sub + Integer.toString(count);
        }
    }

    //Translates Board int elements into text
    private static char translate(int i) throws BoardFormatException
    {
        switch(i)
        {
            case 0: return '.';
            case 1: return 'b';
            case 2: return 'c';
            default: throw new BoardFormatException();
        }
    }
}