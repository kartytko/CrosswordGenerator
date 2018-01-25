package main.operations;

import main.crossword.Crossword;

interface Writer {
    public boolean write(Crossword crossword);
    public long getUniqueId();
}
