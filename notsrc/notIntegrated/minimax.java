bstract class Minimax{
 
	MinMax(board){
	  return MaxMove(board);
	}
	 
	MaxMove(board){
	  if(GameEnded(board)){
	    return EvalGameState(board);
	  }
	  else{
	    best_move = "";
	    moves = getAvaiableMoves("colour");
	    for(String move : moves){
	       move = MinMove(makeMove(game));
	       if (Value(move) > Value(best_move)) {
	          best_move = move;
	       }
	    }
	    return best_move;
	  }
	}
	 
	MinMove(board){
	  best_move = "";
	  moves = getAvaiableMoves("colour");
	  for(String move : moves){
	     move = MaxMove(makeMove(game));
	     if (Value(move) > Value(best_move)) {
	        best_move = move;
	     }
	  }
	return best_move;
	}
}
	
