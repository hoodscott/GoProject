
import java.util.ArrayList;

abstract class minimax2 {

    public void main(String[] args) {
        MinMax(1);
    }

    ArrayList<Integer> moves2 = new ArrayList<Integer>();
    int position = 0;
    ArrayList<Integer> moves3 = new ArrayList<Integer>();

    public int MinMax(int position) {
        return MaxMove(position);
    }

    public int MaxMove(int position) {
        if (gameEnded(position)) {
            return evaluatedGameState();
        } else {
            int i = 0;
            int best_move = 0;
            moves2 = getLegalMoves(position);
            for (int move : moves2) {
                move = MinMove(position);
                if (value(moves2.get(i)) > value(best_move)) {
                    best_move = move;
                }
            }
            position = best_move;
            System.out.println("moved to positon" + position);
            return best_move;
        }

    }

    public int MinMove(int position) {
        int j = 0;
        int best_move = 0;
        moves3 = getLegalMoves(position);
        for (int move : moves3) {
            move = MaxMove(position);
            if (value(moves3.get(j)) > value(best_move)) {
                best_move = move;
            }
        }

        position = best_move;
        System.out.println("moved to positon" + position);
        return best_move;
    }

    public static ArrayList<Integer> getLegalMoves(int position) {
        ArrayList<Integer> moves = new ArrayList<Integer>();

        switch (position) {
            case 1:
                moves.add(2);
                moves.add(3);
                moves.add(4);
            case 2:
                moves.add(6);
                moves.add(7);
                moves.add(8);
            case 3:
                moves.add(9);
                moves.add(10);
                moves.add(11);
            case 4:
                moves.add(12);
                moves.add(13);
                moves.add(14);
            default:
                break;
        }
        return moves;
    }

    public static boolean gameEnded(int position) {

        switch (position) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return true;
            default:
                return false;
        }

    }

    public static int value(int position) {
        int value = position;
        return value;
    }

    public int evaluatedGameState() {
        System.exit(0);
        return 0;

    }

}
