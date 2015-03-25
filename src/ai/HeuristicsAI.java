package ai;

/**
 * This is the abstract class for all AI heuristic types
 */
import ai.heuristics.specific.Hane;
import ai.heuristics.general.EyeCreator;
import ai.heuristics.specific.EightStonesInARow;
import ai.heuristics.general.LibertyCounter;
import ai.heuristics.general.LivingSpace;
import ai.heuristics.general.TwoPointEye;
import ai.heuristics.general.ThreeLiberties;
import ai.heuristics.specific.SixStonesInARow;
import ai.heuristics.specific.UnsettledThree;
import ai.heuristics.*;
import java.util.ArrayList;
import main.Board;
import main.LegalMoveChecker;

public abstract class HeuristicsAI extends AI{
    
    protected ArrayList<Heuristic> heuristics = new ArrayList();
    protected boolean usingHeuristics = false;
    protected static boolean heuristicsFirst = false;
    
    //Method for setting heuristics
    public void setHeuristics(String[] names){
        heuristics = new ArrayList();
        for(String name : names)
            addHeuristic(name);
    }
    
    public void addHeuristic(String heuristicName){
        switch(heuristicName){
            case "Hane": heuristics.add(new Hane()); break;
            // case "HasAnEye": heuristics.add(new HasAnEye()); break;
            case "EightStonesInARow": heuristics.add(new EightStonesInARow()); break;
            case "TwoPointEye": heuristics.add(new TwoPointEye()); break;
            case "UnsettledThree": heuristics.add(new UnsettledThree()); break;
            case "ThreeLiberties": heuristics.add(new ThreeLiberties()); break;
            case "LibertyCounter": heuristics.add(new LibertyCounter()); break;
            case "LivingSpace": heuristics.add(new LivingSpace()); break;
            case "EyeCreator": heuristics.add(new EyeCreator()); break;
            case "SixStonesInARow": heuristics.add(new SixStonesInARow()); break;
            default: System.err.println("WARNING: heuristic \'"+heuristicName+"\' could not be found."); return;
        }
        System.out.println("Added heuristic: "+heuristicName);
    }
    
    //calculates score from heuristic
    protected int getHeuristicScores(Board initialBoard, Board currentBoard, LegalMoveChecker lmc, Objective evaluator){
        int sum = 0;
        for(Heuristic h : heuristics)
            sum += h.assess(initialBoard, currentBoard, lmc, evaluator, colour);
        
        return sum;
    }
    
    public static boolean getHeuristicsFirst(){return heuristicsFirst;}
    public static void setHeuristicsFirst(boolean setting){ heuristicsFirst = setting;}
}
