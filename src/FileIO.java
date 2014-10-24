import java.io.*;
import java.util.ArrayList;
        
public class FileIO {   

    //Default paths
    public static final String relativePath = System.getProperty("user.dir");
    public static final String DEFLOGOUTPUT = relativePath+"\\saveData\\boards\\board";
    public static final String DEFOUTPUT = relativePath+"\\saveData\\boards\\board";
    public static final String DEFINPUT = relativePath+"\\saveData\\logs\\log";


    //Default Board writing method
	public static void writeBoard(Board board){writeBoard(board,DEFOUTPUT);}

    //Board writing method with given path
	public static void writeBoard(Board board, String path){

        String content = "";
        try{
            int w = board.getWidth(); int h = board.getHeight();
            char[][] cBoard = new char[h][w];
            content += w+" "+h+ '\n';

            for(int i = 0; i < w; i++)
                for(int j = 0; j < h;j++)
                    cBoard[j][i] = translateToChar(board.get(i,j));

            for(int i = 0; i < w; i++)
                content += new String(cBoard[i]) + '\n';

            writeFile(content, path);
        }
        catch(BoardFormatException badBoard){System.err.println(badBoard.getMsg());}
	}

    //Default Board reading method
	public static Board readBoard(){return readBoard(DEFINPUT);}

    //Board reading method for a given path
	public static Board readBoard(String path){

        ArrayList<String> lines = readFile(path);

        //catch(BoardFormatException badBoard){System.err.println("ERROR: The given file does not adhere to the Board format");}

        return null;

	}

	public static void writeToLog(String text){

	}

	public static void writeToLog(String text, String path){

	}

    //General read method - returns ArrayList of lines
    private static ArrayList<String> readFile(String path){

        BufferedReader reader = null;
        ArrayList<String> rawInput = new ArrayList<>();
        String currentLine;
        
        try{
            reader = new BufferedReader(new FileReader(path));
            //System.out.println("--Reading in file from "+path+" --");
                          
            while((currentLine = reader.readLine()) != null)
                    rawInput.add(currentLine);
        }
        
        catch(IOException ex){System.err.println("ERROR: The program could not read the file at "+path);}
        
        finally{
            try{if(reader != null)reader.close();}

            catch(IOException ex){System.err.println("ERROR: There was an error closing the reading stream.");}
            //System.out.println("--Reading Complete--");      
        }
        return rawInput;
    }
    
    //General write method
    private static void writeFile(String output, String path){

        BufferedWriter writer = null;
        String p = path; //This adds the relative path of where Main is located.
        
        try{
            File file = new File(p);
            
            if(file.exists()){

                System.err.println("WARNING: file already exists.");
                while(file.exists()){

                    p = adjustPath(p);
                    file = new File(p);
                }
                System.err.println("NOTE: Writing to "+p+" instead."); 
            }

            file.createNewFile();
                      
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(output);
            writer.close();                        
        }
        catch(IOException ex){System.err.println("ERROR: The program could not write the file to "+p);}
        finally{

            try{if(writer != null)writer.close();}
            catch(IOException ex){System.err.println("ERROR: here was an error closing the writing stream.");}
        }
    }

    //Fancy method that adds a number to a path.
    private static String adjustPath(String original){

        String numbers = "";
        boolean isDigit = true;

        for(int i = original.length() - 1; i >= 0 && isDigit; i--){

            if(Character.isDigit(original.charAt(i)))
                numbers = original.charAt(i) + numbers;
            else
                isDigit = false;
        }

        if(numbers.length() == 0)
            return original + "1";
        else{   

            int count = Integer.parseInt(numbers) + 1;
            //System.out.println(count);
            String sub = original.substring(0,original.length() - numbers.length());
            return sub + Integer.toString(count);
        }
    }   

    //Checks the integrity of an inputted board data and translates it to an int[][]
    private static int[][] translateBoard(ArrayList<String> raw){

        int w, h;
        int[][] transBoard;

        try{

            //Checks for existence of dimensions line and at least 1 board line
            if(raw.size() < 2)
                throw new BoardFormatException();

            String[] rawInts = raw.get(0).split(" ");

            //Checks whether first line contains 2 integers
            if(rawInts.length == 2 && isNumber(rawInts[0]) && isNumber(rawInts[1]))
            {
                w = Integer.parseInt(rawInts[0]);
                h = Integer.parseInt(rawInts[1]);
            }
            else
                throw new BoardFormatException();

            //Checks if file has specified number of rows.
            if(h != raw.size() - 1)
                throw new BoardFormatException();

            for(int i = 0; i < h; i++)
                if(raw.get(i+1).length() != w) //Checks if each row has the specified number of columns.
                    throw new BoardFormatException();

        }
        catch(BoardFormatException badBoard){
        }

        return new int[1][1];
    }
    
    //Translates Board int elements into text
    private static char translateToChar(int i) throws BoardFormatException{
        
        switch(i){

            case 0: return '.';
            case 1: return 'b';
            case 2: return 'w';
            default: throw new BoardFormatException("ERROR: The board to save contains an illegal integer "+i+". Board not saved.");
        }
    }

    // Method for translation of char positions to int values
    private static int translateToInt(char value) throws BoardFormatException{

        switch(value){

            case '.': return 0;
            case 'b': return 1;
            case 'w': return 2;
            default: throw new BoardFormatException();
        }
    }

    //Checks if a string is an int
    private static boolean isNumber(String s){

        try{Integer.parseInt(s);}
        catch(NumberFormatException e){return false;}
        return true;
    }

    private static String[] checkOS(){
        String[] pathVars
        if(System.getProperty("os.name") == "win"){
            DEFOUTPUT 
            DEFINPUT = 
            DEFLOGOUTPUT = 
        }
        else if(System.getProperty("os.name") == "Linux")
        {

        }
        else
        {

        }

    }
}