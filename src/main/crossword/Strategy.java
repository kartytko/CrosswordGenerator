package main.crossword;

import main.board.Board;
import main.board.CwEntry;
import main.crossword.Crossword;

//Po tej klasie dziedzicy Advanced oraz Basic
abstract public class Strategy {
    abstract public CwEntry findEntry(Crossword cw);
    abstract public void updateBoard (Board b, CwEntry e);
}
