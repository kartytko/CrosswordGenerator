package main.crossword;

import main.board.Board;
import main.board.CwEntry;
import main.crossword.Crossword;

abstract public class Strategy {
    abstract public CwEntry findEntry(Crossword cw);
    abstract public void updateBoard (Board b, CwEntry e);
}
