package main.operations;

import main.crossword.Crossword;

//Klasa SaveCrossword implementuje ten interfejs

interface Writer {
    public boolean write(Crossword crossword);
    public long getUniqueId();
}
