package main.crossword;

import main.dictionary.InteliCwDB;
import main.board.Board;
import main.board.BoardCell;
import main.board.BoardCellAvailability;
import main.board.CwEntry;

import java.util.Iterator;
import java.util.LinkedList;


public class Crossword {
    private LinkedList<CwEntry> entries = new LinkedList<>();
    private Board b;
    private InteliCwDB cwdb;        //database with all available entries
    private final long ID = -1;

    private String cwdb_path="/home/kartytko/Pulpit/projekt/src/main/dictionary/dic.txt";
    private int size = 7;

    public int hori_or_vert_it = 0;     //needed if Strategy s is an istance of Advanced


    public Crossword(int size, String path){
        cwdb_path = path;
        cwdb = new InteliCwDB(cwdb_path);
        this.size = size;
        b = new Board(this.size, this.size);
    }

    public Crossword(int size){
        cwdb = new InteliCwDB(cwdb_path);
        this.size = size;
        b = new Board(this.size, this.size);
    }


    public int getSize(){
        return size;
    }


    public Iterator<CwEntry> getROEntryIter() {
        Iterator <CwEntry> iterator = entries.listIterator();
        return iterator;
    }

    public Board getBoardCopy() throws CloneNotSupportedException{
        Board board_copy = b.cloneMyself();
        return board_copy;
    }

    public InteliCwDB getCwDB(){
        return cwdb;
    }
    public void setCwDB(InteliCwDB cwdb){
        this.cwdb = cwdb;
    }

    public boolean contains(String word){           //czy lista entires zawiera word?
        return entries.contains(word);
    }

    public final void addCwEntry(CwEntry cwe, Strategy s){
        entries.add(cwe);
        s.updateBoard(b, cwe);
        if(s instanceof Advanced){hori_or_vert_it++;}
    }

    public final void generate (Strategy s){
        if(s instanceof Advanced){
           setAvailability();
            CwEntry e;
            while( (e=s.findEntry(this)) != null || (hori_or_vert_it<2)){
                addCwEntry(e, s);
            }
        }else{
            CwEntry e;
            while( (e=s.findEntry(this)) != null){
                addCwEntry(e, s);
            }
        }
    }

    public void setAvailability(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++) {
                BoardCell boardCell_tmp = new BoardCell();
                if(i==0){
                    boardCell_tmp.disableHoriz(BoardCellAvailability.END);
                }
                if(i==size-1){
                    boardCell_tmp.disableHoriz(BoardCellAvailability.START);
                }

                if(j==0){
                    boardCell_tmp.disableVert(BoardCellAvailability.END);
                }
                if(j==size-1){
                    boardCell_tmp.disableVert(BoardCellAvailability.START);
                }

                b.setCell(j, i, boardCell_tmp);

            }

        }

    }

}

