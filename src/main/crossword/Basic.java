package main.crossword;

import main.dictionary.Entry;
import main.dictionary.InteliCwDB;
import main.board.Board;
import main.board.BoardCell;
import main.board.CwEntry;

import java.util.LinkedList;
import java.util.Random;

public class Basic extends Strategy {

    private CwEntry main_entry;
    private String path = "/home/kartytko/Pulpit/projekt/src/main/dictionary/dic.txt/";
    private boolean is_main_entry_added = false;

    int size = 10;

    public Basic(int size){
        super();                                        //generates random entry which becomes the main entry
        this. size = size;
        InteliCwDB dict = new InteliCwDB(path);
        Entry main_entry_ = dict.getRandom(size);
        while(true){
            if(main_entry_.getWord().contains("ą") || main_entry_.getWord().contains("ę") || main_entry_.getWord().contains("ó") || main_entry_.getWord().contains("v") ){
                main_entry_ = dict.getRandom(size);
            }else{
                break;
            }
        }

        main_entry = new CwEntry(main_entry_.getWord(), main_entry_.getClue());
        main_entry.setX(0);
        main_entry.setY(0);
        main_entry.setDir(CwEntry.Direction.VERT);
    }

    @Override
    public void updateBoard(Board b, CwEntry e) {

        boolean isHorizontal;
        if(e.getDir().equals(CwEntry.Direction.HORIZ)){
            isHorizontal = true;
        }else{
            isHorizontal = false;
        }


        if(!isHorizontal){
            int x_tmp = e.getX();
            int y_tmp = e.getY();
            int length = e.getWord().length();
            for(int i = 0; i<length; i++){
                BoardCell boardCell_tmp = new BoardCell();
                boardCell_tmp.setContent(e.getWord().substring(i, i+1));
                b.setCell(x_tmp, y_tmp, boardCell_tmp);

                x_tmp = x_tmp+1;
            }

        }else{
            int x_tmp = -1;
            int y_tmp = 1;
            for(int i=0; i<b.getHeight(); i++){
                if(b.getCell(i, 1) == null){
                    x_tmp = i;
                    break;
                }
            }

            for(int i=1; i<e.getWord().length(); i++){
                BoardCell boardCell_tmp = new BoardCell();
                boardCell_tmp.setContent(e.getWord().substring(i, i+1));
                b.setCell(x_tmp, y_tmp, boardCell_tmp);

                y_tmp = y_tmp+1;
            }

        }
    }

    @Override
    public CwEntry findEntry(Crossword cw) {
        size  = cw.getSize();
        if(!is_main_entry_added){
            is_main_entry_added = true;
            return main_entry;
        }


        Board board_copy = new Board(size, size);
        try{
            board_copy = cw.getBoardCopy();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }

        int height = board_copy.getHeight();

        for(int i = 0; i<height; i++){
            if(board_copy.getCell(i, 1) == null && board_copy.getCell(i, 0) != null){
                String pattern = board_copy.getCell(i, 0).getContent();
                pattern = pattern+".+";
                LinkedList<Entry> entries_that_matches_pattern = cw.getCwDB().findAll(pattern);

                while(true){
                    LinkedList<Entry> entries_tmp = (LinkedList<Entry>) entries_that_matches_pattern.clone();
                    int it = 0;
                    while(true){
                        if(it>=entries_tmp.size()){
                            break;
                        }
                        if(entries_tmp.get(it).getWord().length() > board_copy.getWidth()){
                            entries_tmp.remove(it);
                        }else{
                            it++;
                        }
                    }

                    Random generator = new Random();
                    int index = generator.nextInt(entries_tmp.size());

                    Entry entry_tmp = entries_tmp.get(index);
                    if(cw.contains(entry_tmp.getWord())){
                        continue;
                    }else{
                        CwEntry found_entry = new CwEntry(entry_tmp.getWord(), entry_tmp.getClue());
                        found_entry.setDir(CwEntry.Direction.HORIZ);
                        return found_entry;
                    }

                }
            }
        }

        return null;
    }
}
