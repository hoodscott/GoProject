package ai.heuristics;
import ai.Objective;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import main.Board;
import main.BoardFormatException;
import main.Coordinate;
import main.Translator;

public class UnconditionalLife {
    
    static int defendingColour;
    static int attackingColour;
    static ArrayList<HashSet<Coordinate>> regions;
    static ArrayList<HashSet<Coordinate>> defendingStones;
    static ArrayList<HashSet<Coordinate>> defendingStoneLiberties; 
    static ArrayList<HashSet<Coordinate>> vitalRegions;
    static Boolean passed;
    
    //This is an algorithm to detect unconditional life. It bases off of
    //Benson's algorithm.
    public static boolean isItAlive(Board b, Coordinate defendingGroup){
        //long startTime = System.currentTimeMillis();
        regions = new ArrayList();
        defendingStones = new ArrayList();
        defendingStoneLiberties = new ArrayList();
        boolean groupRemoved;   
        defendingColour = b.get(defendingGroup.x, defendingGroup.y);
        attackingColour = Objective.getOtherColour(defendingColour);
        findGroups(b, defendingColour, regions, defendingStones);
        for(HashSet<Coordinate> group : defendingStones){
            defendingStoneLiberties.add(getGroupLiberties(b, group));
        }
        do{
            groupRemoved = false;
            for(int i = 0; i < defendingStones.size(); i++){
                int vitalRegionCount = 0;
                vitalRegions = new ArrayList();
                HashSet<Coordinate> liberties = defendingStoneLiberties.get(i);
                for(HashSet<Coordinate> region : regions)
                    if(liberties.containsAll(region)){
                        vitalRegionCount++;
                        vitalRegions.add(region);
                    }
                //System.out.println("Vital Region Count: "+vitalRegionCount);
                //printGroup(defendingStones.get(i));
                if(vitalRegionCount < 2){
                    //System.out.println("Removing group:");
                    //printGroup(defendingStones.get(i));
                    removeKillableGroup(regions,defendingStones,defendingStoneLiberties, i);
                    groupRemoved = true;
                }
            }
        }
        while(groupRemoved);
        
        //long finishTime = System.currentTimeMillis();
        /*
        System.out.println("Time taken: "+(finishTime-startTime));
        Translator.printGameBoard(b);
        System.out.println("==Stone Groups==");
        printGroups(defendingStones, defendingColour);
        System.out.println("==Liberties==");
        printGroups(defendingStoneLiberties, Board.EMPTY);
        System.out.println("==Regions==");
        printGroups(regions, Board.EMPTY);        
        System.out.println("Stone Group count: "+defendingStones.size());
        System.out.println("Region Group count: "+regions.size());
        
        System.out.println("Alive: "+alreadyInGroup(defendingGroup, defendingStones));
        */
        passed = alreadyInGroup(defendingGroup, defendingStones);
        return passed;
    }
    private static void removeKillableGroup(ArrayList<HashSet<Coordinate>> regions, ArrayList<HashSet<Coordinate>> defendingStones, ArrayList<HashSet<Coordinate>> defendingStoneLiberties, int index){
        defendingStones.remove(index);
        Iterator<HashSet<Coordinate>> it = regions.iterator();
        while(it.hasNext()){
            HashSet<Coordinate> current = it.next();
            if(hasAtLeastOneMatch(current, defendingStoneLiberties.get(index))){
                //System.out.println("Removing region:");
                //printGroup(current);
                it.remove();
            }
        }
    }
    
    private static boolean hasAtLeastOneMatch(HashSet<Coordinate> a, HashSet<Coordinate> b){
        for(Coordinate c : a)
            if(b.contains(c))
                return true;
        return false;
    }   
    
    private static void findGroups(Board b, int colour, ArrayList<HashSet<Coordinate>> regions, ArrayList<HashSet<Coordinate>> defendingStones){
        for(int x = 0; x < b.getWidth(); x++)
            for(int y = 0; y < b.getHeight(); y++){
                Coordinate c = new Coordinate(x,y);
                if(b.get(x, y) == colour && !alreadyInGroup(c, defendingStones)){
                    HashSet <Coordinate> group = new HashSet();
                    getGroup(b, colour, c, group);
                    defendingStones.add(group);
                }
                else if((b.get(x, y) == Board.EMPTY || b.get(x, y) == Board.EMPTY_AI) && !alreadyInGroup(c, regions)){
                    HashSet <Coordinate> region = new HashSet();
                    getRegion(b, c, region);
                    regions.add(region);
                }
                
            }
    }
    
    private static boolean alreadyInGroup(Coordinate c, ArrayList<HashSet<Coordinate>> groups){
        for(HashSet group : groups)
            if(group.contains(c))
                return true;
        return false;
    }
    
    private static void getGroup(Board board, int colour, Coordinate c, HashSet<Coordinate> points) {
        if(points.contains(c))
            return;
        points.add(c);
        //System.out.println("Coord: "+c.toString());
        if (c.x > 0 && board.get(c.x-1, c.y) == colour) {
            getGroup(board, colour, new Coordinate(c.x - 1, c.y), points);
        }
        if (c.x < board.getWidth() - 1 && board.get(c.x+1, c.y) == colour) {
            getGroup(board, colour, new Coordinate(c.x + 1, c.y), points);
        }
        if (c.y > 0 && board.get(c.x, c.y-1) == colour) {
            getGroup(board, colour, new Coordinate(c.x, c.y-1), points);
        }
        if (c.y < board.getHeight() - 1 && board.get(c.x, c.y+1) == colour) {
            getGroup(board, colour, new Coordinate(c.x, c.y+1), points);
        }
    }
    
    private static void getRegion(Board board, Coordinate c, HashSet<Coordinate> points) {
        if(points.contains(c))
            return;
        points.add(c);
        int current;
        //System.out.println("Coord: "+c.toString());   
        if (c.x > 0 && ((current = board.get(c.x-1,c.y)) ==  Board.EMPTY || current == Board.EMPTY_AI || current == attackingColour)) {
            getRegion(board, new Coordinate(c.x - 1, c.y), points);
        }
        if (c.x < board.getWidth() - 1 && ((current = board.get(c.x+1,c.y)) == Board.EMPTY || current == Board.EMPTY_AI || current == attackingColour)) {
            getRegion(board, new Coordinate(c.x + 1, c.y), points);
        }
        if (c.y > 0 && ((current = board.get(c.x,c.y-1)) == Board.EMPTY || current == Board.EMPTY_AI || current == attackingColour)) {
            getRegion(board, new Coordinate(c.x, c.y-1), points);
        }
        if (c.y < board.getHeight() - 1 && ((current = board.get(c.x,c.y+1)) == Board.EMPTY || current == Board.EMPTY_AI  || current == attackingColour)) {
            getRegion(board, new Coordinate(c.x, c.y+1), points);
        }
    }
    
    private static HashSet<Coordinate> getGroupLiberties(Board board, HashSet<Coordinate> group){
        HashSet<Coordinate> liberties = new HashSet();
        for(Coordinate c : group){
        if (c.x > 0 && (board.get(c.x-1, c.y) ==  Board.EMPTY || board.get(c.x-1, c.y) == Board.EMPTY_AI) && !liberties.contains(c)) {
            liberties.add(new Coordinate(c.x-1, c.y));
        }
        if (c.x < board.getWidth() - 1 && (board.get(c.x+1, c.y) == Board.EMPTY || board.get(c.x+1, c.y) == Board.EMPTY_AI) && !liberties.contains(c)) {
            liberties.add(new Coordinate(c.x+1, c.y));
        }
        if (c.y > 0 && (board.get(c.x, c.y-1) == Board.EMPTY || board.get(c.x, c.y-1) == Board.EMPTY_AI) && !liberties.contains(c)) {
            liberties.add(new Coordinate(c.x, c.y-1));
        }
        if (c.y < board.getHeight() - 1 && (board.get(c.x, c.y+1) == Board.EMPTY || board.get(c.x, c.y+1) == Board.EMPTY_AI) && !liberties.contains(c)) {
            liberties.add(new Coordinate(c.x, c.y+1));
        }
        }
        return liberties;
    }
    
    private static void printGroup(HashSet<Coordinate> group){
        for(Coordinate c : group)
            System.out.print("("+c.toString()+") ");
        System.out.println();
    }
    
    private static void printGroups(ArrayList<HashSet<Coordinate>> groups, int type){
        for(HashSet<Coordinate> group : groups){
            try{System.out.println(Translator.translateToString(type)+" group: ");}catch(BoardFormatException e){}
            printGroup(group);
        }
    }
    
    public static void printLastDetails(){
        System.out.println("==Stone Groups==");
        printGroups(defendingStones, defendingColour);
        System.out.println("==Liberties==");
        printGroups(defendingStoneLiberties, Board.EMPTY);
        System.out.println("==Vital Regions==");
        printGroups(vitalRegions, Board.EMPTY);
        System.out.println("==Regions==");
        printGroups(regions, Board.EMPTY);   
        System.out.println("Alive: "+passed.toString());
    }
}
