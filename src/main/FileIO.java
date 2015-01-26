package main;

import java.io.*;
import java.util.ArrayList;
import java.lang.StringBuilder;
        
public final class FileIO {   

    //Default paths
    public static final String RELATIVEPATH = System.getProperty("user.dir");
    public static final String DEFLOGOUTPUT = "\\src\\saveData\\logs\\";
    public static final String DEFOUTPUT = "\\src\\saveData\\boards\\";
    public static final String DEFINPUT = "\\src\\saveData\\boards\\";
    private static final String DEFLOGNAME = "log";
    private static final String DEFBOARDNAME = "board";

    //Default Board writing method
    public static void writeBoard(GameEngine gameEngine)throws BoardFormatException{
        writeBoard(gameEngine,(RELATIVEPATH+DEFOUTPUT+DEFBOARDNAME));
    }

    //Board writing method with given path
    public static void writeBoard(GameEngine gameEngine, String path) throws BoardFormatException{
        writeFile(Translator.translateToFile(gameEngine),path);
    }

    //Default Board reading method
    public static GameEngine readBoard() throws BoardFormatException, NumberFormatException 
    {return readBoard(RELATIVEPATH+DEFINPUT+DEFBOARDNAME);}

    //Board reading method for a given path
    public static GameEngine readBoard(String path) throws BoardFormatException, NumberFormatException{
        ArrayList<String> lines = readFile(path);
        System.out.println(lines);
        return Translator.translateGameInstructions(lines);
    }

    //Default Log writing method
    public static void writeLog(String text){writeLog(text,RELATIVEPATH+DEFLOGOUTPUT+DEFLOGNAME);}

    //Log writing method that takes in a custom path
    public static void writeLog(String text, String path){writeFile(text, path);}

    //Reads in help information on request
    public static String readHelp(String path){
        //System.out.println(path);
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
        
        System.out.println(output);
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