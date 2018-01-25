package main;

import javafx.scene.text.Text;
import main.board.Board;
import main.board.CwEntry;
import main.crossword.Advanced;
import main.crossword.Basic;
import main.crossword.Crossword;

import javafx.scene.shape.Rectangle;

import java.util.Iterator;
import java.util.LinkedList;

public class Model {

    int size=10;
    Crossword crossword;
    LinkedList<Rectangle> list = new LinkedList<>();
    LinkedList<Text> list_of_texts = new LinkedList<>();
    LinkedList<Text> resolve = new LinkedList<>();

    LinkedList<Rectangle> list_basic = new LinkedList<>();
    LinkedList<Text> list_of_texts_basic = new LinkedList<>();
    LinkedList<Text> resolve_basic = new LinkedList<>();
    boolean crossword_isset = false;

    String path_to_selected_dictionary = "";

    public Model(int size){
        this.size = size;
    }

    public void setCrossword(Crossword crossword){
        this.crossword = crossword;
        this.crossword_isset = true;
    }

    public LinkedList<Rectangle> GenerateAdvance(){
        if(!crossword_isset){
            if(path_to_selected_dictionary.equals("")){
                crossword =  new Crossword(size);
            }else{
                crossword = new Crossword(size, path_to_selected_dictionary);
            }
        }
        if(!this.crossword.getROEntryIter().hasNext()){
            Advanced advanced = new Advanced();
            crossword.generate(advanced);
        }

        Board board = new Board(1, 1);

        try{
            board = crossword.getBoardCopy();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }


        for(int i = 0; i<crossword.getSize(); i++){
            for(int j=0; j<crossword.getSize(); j++){
                if(board.getCell(i, j) != null){
                    if(board.getCell(i, j).getContent().equals("")){
                    }else{
                        Cell cell = new Cell();
                        list.add(cell.draw(i, j));
                        resolve.add(cell.resolve(board.getCell(i, j), i, j));
                    }
                }
            }
        }

        Iterator<CwEntry> iterator = crossword.getROEntryIter();

        int it = 0;
        while(iterator.hasNext()){
            CwEntry entry = iterator.next();
            NumbersClue numbersClue = new NumbersClue();
            list_of_texts.add(numbersClue.draw(entry, it).get(0));
            list_of_texts.add(numbersClue.draw(entry, it).get(1));
            it++;
        }
        return list;
    }





    public LinkedList<Rectangle> GenerateBasic(){

        if(!crossword_isset){
            if(path_to_selected_dictionary.equals("")){
                crossword =  new Crossword(size);
            }else{
                crossword = new Crossword(size, path_to_selected_dictionary);
            }
        }


        if(!crossword.getROEntryIter().hasNext()) {
            Basic basic = new Basic(size);
            crossword.generate(basic);
        }


        Board board = new Board(1, 1);

        try{
            board = crossword.getBoardCopy();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }


        for(int i = 0; i<crossword.getSize(); i++){
            for(int j=0; j<crossword.getSize(); j++){
                if(board.getCell(i, j) != null){
                    if(board.getCell(i, j).getContent().equals("")){
                    }else{
                        Cell cell = new Cell();
                        list_basic.add(cell.drawBasic(i, j));
                        resolve_basic.add(cell.resolve(board.getCell(i, j), i, j));
                    }
                }
            }
        }

        Iterator<CwEntry> iterator = crossword.getROEntryIter();

        int it = 0;
        CwEntry entry = iterator.next();
        while(iterator.hasNext()){
            entry = iterator.next();
            NumbersClue numbersClue = new NumbersClue();
            list_of_texts_basic.add(numbersClue.drawBasic(entry, it).get(0));
            list_of_texts_basic.add(numbersClue.drawBasic(entry, it).get(1));
            it++;

        }


        return list_basic;
    }
}
