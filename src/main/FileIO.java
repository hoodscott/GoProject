package main;

import java.io.*;
import java.util.ArrayList;
        
public class FileIO {   

    //Default paths
    public static final String RELATIVEPATH = System.getProperty("user.dir");
    public static final String DEFLOGOUTPUT = "\\saveData\\logs\\";
    public static final String DEFOUTPUT = "\\saveData\\boards\\";
    public static final String DEFINPUT = "\\saveData\\boards\\";
    private static final String DEFLOGNAME = "log";
    private static final String DEFBOARDNAME = "board";

    //Default Board writing method
	public static void writeBoard(Board board){writeBoard(board,(RELATIVEPATH+DEFOUTPUT+DEFBOARDNAME));}

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

            for(int i = 0; i < h; i++)
                content += new String(cBoard[i]) + '\n';

            writeFile(content, path);
        }
        catch(BoardFormatException badBoard){System.err.println(badBoard.getMsg());}
	}

    //Default Board reading method
	public static Board readBoard(){return readBoard(RELATIVEPATH+DEFINPUT+DEFBOARDNAME);}

    //Board reading method for a given path
	public static Board readBoard(String path){

        ArrayList<String> lines = readFile(path);
        int[][] contents;

        if((contents = translateBoard(lines)) != null)
            return new Board(contents);
        else{
            System.err.println("ERROR: Inputted board cannot be processed. Returning default board.");
            return new Board();
        }
	}

    //Default Log writing method
	public static void writeLog(String text){writeLog(text,RELATIVEPATH+DEFLOGOUTPUT+DEFLOGNAME);}

    //Log writing method that takes in a custom path
	public static void writeLog(String text, String path){writeFile(text, path);}

    //Reads in help information on request
    public static String readHelp(String path){

        if(!(new File(path).exists()))
            return " does not exist";

        ArrayList<String> lines = readFile(path);
        String info = "";

        if(lines != null){
            info = "\n";
            for(String s : lines)
            info += s+"\n";
        }  

        return info;
    }

    //General read method - returns ArrayList of lines
    public static ArrayList<String> readFile(String path){

        BufferedReader reader = null;
        ArrayList<String> rawInput = new ArrayList<>();
        String currentLine;
        
        try{
            reader = new BufferedReader(new FileReader(path));
                          
            while((currentLine = reader.readLine()) != null)
                    rawInput.add(currentLine);
        }
        
        catch(IOException ex){System.err.println("ERROR: The program could not read the file at: "+path);}
        
        finally{
            try{if(reader != null)reader.close();}

            catch(IOException ex){System.err.println("ERROR: There was an error closing the reading stream.");}
        }
        return rawInput;
    }
    
    //General write method
    public static void writeFile(String output, String path){

        BufferedWriter writer = null;
        String p = path; //This adds the relative path of where Main is located.
        p = pathOS(p);
        
        try{
            File file = new File(p);
            
            if(file.exists()){

                System.err.println("WARNING: file already exists.");
                while(file.exists()){

                    p = adjustPath(p);
                    file = new File(p);
                }
            }

            file.createNewFile();
            System.out.println("NOTE: Writing file to "+p); 
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(output);
            writer.close();                        
        }
        catch(IOException ex){System.err.println("ERROR: The program could not write the file to: "+p);}
        finally{

            try{if(writer != null)writer.close();}
            catch(IOException ex){System.err.println("ERROR: There was an error closing the writing stream.");}
        }
    }



    //Checks the integrity of an inputted board data and translates it to an int[][]. If a condition fails, returns null.
    public static int[][] translateBoard(ArrayList<String> raw){

        int w, h;
        int[][] transBoard;

        try{

            //Checks for existence of dimensions line and at least 1 board line
            if(raw.size() < 2)
                throw new BoardFormatException("ERROR: The given input does not contain enough lines for a Board.");

            String[] rawInts = raw.get(0).split(" ");

            //Checks whether first line contains 2 integers
            if(rawInts.length == 2 && isNumber(rawInts[0]) && isNumber(rawInts[1]))
            {
                w = Integer.parseInt(rawInts[0]);
                h = Integer.parseInt(rawInts[1]);
            }
            else
                throw new BoardFormatException("ERROR: The initial line does not contain two valid integers.");

            //Checks if file has specified number of rows.
            if(h != raw.size() - 1)
                throw new BoardFormatException("ERROR: There is a mismatch in the given number of rows.");

            for(int i = 0; i < h; i++)
                if(raw.get(i+1).length() != w) //Checks if each row has the specified number of columns.
                    throw new BoardFormatException("ERROR: There is a mismatch in the given number of columns.");

            //Translates chars into board
            transBoard = new int[w][h];
            for(int i = 0; i < h; i++)
                for(int j = 0; j < w; j++)
                    transBoard[j][i] = translateToInt(raw.get(i+1).charAt(j));

            return transBoard;

        }
        catch(BoardFormatException badBoard) {System.err.println(badBoard.getMsg());}

        return null;
    }

    //Fancy method that adds a number to a path.
    public static String adjustPath(String original){

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
            String sub = original.substring(0,original.length() - numbers.length());
            return sub + Integer.toString(count);
        }
    }   
    
    //Translates Board int elements into text
    public static char translateToChar(int i) throws BoardFormatException{
        
        switch(i){

            case 0: return '.';
            case 1: return 'b';
            case 2: return 'w';
            default: throw new BoardFormatException("ERROR: The board to translate contains an illegal integer "+i+". Board not translated.");
        }
    }

    // Method for translation of char positions to int values
    public static int translateToInt(char value) throws BoardFormatException{

        switch(value){

            case '.': return 0;
            case 'b': return 1;
            case 'w': return 2;
            default: throw new BoardFormatException("ERROR: The board to translate contains an illegal character "+value+". Board not translated.");
        }
    }

    //Checks if a string is an int
    public static boolean isNumber(String s){

        try{Integer.parseInt(s);}
        catch(NumberFormatException e){return false;}
        return true;
    }

    //Adjusts Paths for Unix/Linux or Windows
    public static String pathOS(String path){

        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win"))
            return path.replace('/','\\');

        else if(os.contains("linux") || os.contains("unix"))
            return path.replace('\\','/');
        else
            System.out.println("WARNING: Detected OS is neither Windows nor Unix/Linux. Path handling for read/write operations may fail.");

        return path;
    }
}