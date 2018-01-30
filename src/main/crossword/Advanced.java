package main.crossword;

import main.board.BoardCell;
import main.board.BoardCellAvailability;
import main.dictionary.Entry;
import main.board.Board;
import main.board.CwEntry;

import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Klasa "generująca" skomplikowaną krzyżówkę
//Używana w klasie Crossword, w metodach generate oraz addCwEntry

public class Advanced extends Strategy {

    int size = 10;

    //Funkcja dodająca znalezione hasło CwEntry cw, do Board b
    @Override
    public void updateBoard(Board b, CwEntry cw){
        if(cw.getDir().equals(CwEntry.Direction.VERT)){
            int length = cw.getWord().length();
            int x_tmp = cw.getX();
            int y_tmp = cw.getY();

            for(int i = 0; i<length; i++){
                BoardCell boardCell_tmp = new BoardCell();
                boardCell_tmp.setContent(cw.getWord().substring(i, i+1));
                b.setCell(x_tmp, y_tmp, boardCell_tmp);

                x_tmp = x_tmp+1;
            }
            updateAvailability(cw.getX(), cw.getY(), length, CwEntry.Direction.VERT, b);
        }else{
            int length = cw.getWord().length();
            int x_tmp = cw.getX();
            int y_tmp = cw.getY();

            for(int i = 0; i<length; i++){
                BoardCell boardCell_tmp = new BoardCell();
                boardCell_tmp.setContent(cw.getWord().substring(i, i+1));
                b.setCell(x_tmp, y_tmp, boardCell_tmp);

                y_tmp = y_tmp+1;
            }
            updateAvailability(cw.getX(), cw.getY(), length, CwEntry.Direction.HORIZ, b);
        }
    }


    //funkcja szukająca kolejnego hasła do krzyżówki (kolejne hasła są dodawane na przemian pionowo i poziomo)
    //generuje przypadkową długość hasła równą random_length, a następnie próbuje znaleźć dopasowanie w zadanym Board na podstawie regexu
    @Override
    public CwEntry findEntry(Crossword cw){
        size = cw.getSize();
        Board board_copy = new Board(size, size);
        try{
            board_copy= cw.getBoardCopy();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }

        //first entry
        if(cw.hori_or_vert_it==0){
            Entry first_entry = cw.getCwDB().getRandom(size-1);
            CwEntry first_cwentry= new CwEntry(first_entry.getWord(), first_entry.getClue());
            first_cwentry.setDir(CwEntry.Direction.VERT);
            first_cwentry.setX(1);
            first_cwentry.setY(2);
            return first_cwentry;
        }



        Random generator = new Random();
        int random_length = generator.nextInt(size);       // random length of next entry
        if(random_length<=3){
            random_length=random_length+3;
        }
        String pattern_containing_only_dots = "";
        for(int i=0; i<random_length; i++){
            pattern_containing_only_dots=pattern_containing_only_dots+".";
        }

        int iterator =0;
        while(true) {
            iterator++;
            if (cw.hori_or_vert_it%2 == 1) {        //horizontal
                Entry entry = cw.getCwDB().getRandom(random_length);
                for(int i=0; i<size; i++){
                    for(int j=0; j<size-random_length; j++){
                        String pattern = board_copy.createPattern(j, i, j+random_length-1, i);

                        Pattern patt = Pattern.compile(pattern);
                        Matcher matcher = patt.matcher(entry.getWord());
                        if(matcher.matches() && !pattern.equals(pattern_containing_only_dots) && !cw.contains(entry.getWord())){
                            CwEntry cwEntry = new CwEntry(entry.getWord(), entry.getClue());
                            cwEntry.setY(j);
                            cwEntry.setX(i);
                            cwEntry.setDir(CwEntry.Direction.HORIZ);
                            if(board_copy.getCell(i, j).getHoriz().getStart() && board_copy.getCell(i, j+random_length-1).getHoriz().getEnd()){
                                boolean isOK = true;
                                for(int k=j; k<j+random_length-1; k++){
                                    if(!board_copy.getCell(i, k).getHoriz().getInner()){
                                        isOK =false;
                                        break;
                                    }
                                }
                                if(isOK){
                                    return cwEntry;
                                }
                            }
                        }
                    }
                }

            } else {                               //vertical

                Entry entry = cw.getCwDB().getRandom(random_length);
                for(int i=0; i<size-random_length; i++){
                    for(int j=0; j<size; j++){
                        String pattern = board_copy.createPattern(j, i, j, i+random_length-1);

                        Pattern patt = Pattern.compile(pattern);
                        Matcher matcher = patt.matcher(entry.getWord());
                        if(matcher.matches() && !pattern.equals(pattern_containing_only_dots) && !cw.contains(entry.getWord())){
                            CwEntry cwEntry = new CwEntry(entry.getWord(), entry.getClue());
                            cwEntry.setY(j);
                            cwEntry.setX(i);
                            cwEntry.setDir(CwEntry.Direction.VERT);
                            if(board_copy.getCell(i, j).getVert().getStart() && board_copy.getCell(i+random_length-1, j).getVert().getEnd()){
                                boolean isOK = true;
                                for(int k=i; k<i+random_length-1; k++){
                                    if(!board_copy.getCell(k, j).getVert().getInner()){
                                        isOK =false;
                                        break;
                                    }
                                }
                                if(isOK){
                                    return cwEntry;
                                }
                            }
                        }
                    }
                }
            }

            random_length = generator.nextInt(size);       // random length of next entry
            if(random_length<=3){
                random_length=random_length+3;
            }
            pattern_containing_only_dots = "";
            for(int i=0; i<random_length; i++){
                pattern_containing_only_dots=pattern_containing_only_dots+".";
            }

            if(iterator == 10){
                cw.hori_or_vert_it --;
            }
            if(iterator>20){
                return null;
            }
        }
    }




    //funkcja pozwalająca uaktualnić dostępność pól, bo dodaniu hasła do krzyżówki
    public void updateAvailability(int from_x, int from_y, int length, CwEntry.Direction d, Board b){
        int fromy = from_y;
        int fromx = from_x;
        if(d.equals(CwEntry.Direction.VERT)){
            for(int i=0; i<length; i++){                        //vertical
                b.getCell(fromx+i, fromy).disableVert(BoardCellAvailability.START);
                b.getCell(fromx+i, fromy).disableVert(BoardCellAvailability.INNER);
                b.getCell(fromx+i, fromy).disableVert(BoardCellAvailability.END);

                if(fromy-1>=0){
                    b.getCell(fromx+i, fromy-1).disableVert(BoardCellAvailability.START);
                    b.getCell(fromx+i, fromy-1).disableVert(BoardCellAvailability.INNER);
                    b.getCell(fromx+i, fromy-1).disableVert(BoardCellAvailability.END);

                    b.getCell(fromx+i, fromy-1).disableHoriz(BoardCellAvailability.END);

                }

                if((fromy+1)<size){
                    b.getCell(fromx+i, fromy+1).disableVert(BoardCellAvailability.START);
                    b.getCell(fromx+i, fromy+1).disableVert(BoardCellAvailability.INNER);
                    b.getCell(fromx+i, fromy+1).disableVert(BoardCellAvailability.END);

                    b.getCell(fromx+i, fromy+1).disableHoriz(BoardCellAvailability.START);
                }
            }

            if(fromx-1>=0){
                b.getCell(fromx-1, fromy).disableVert(BoardCellAvailability.START);
                b.getCell(fromx-1, fromy).disableVert(BoardCellAvailability.INNER);
                b.getCell(fromx-1, fromy).disableVert(BoardCellAvailability.END);

                b.getCell(fromx-1, fromy).disableHoriz(BoardCellAvailability.START);
                b.getCell(fromx-1, fromy).disableHoriz(BoardCellAvailability.INNER);
                b.getCell(fromx-1, fromy).disableHoriz(BoardCellAvailability.END);
            }

            if(fromx+length<size){
                b.getCell(fromx+length, fromy).disableVert(BoardCellAvailability.START);
                b.getCell(fromx+length, fromy).disableVert(BoardCellAvailability.INNER);
                b.getCell(fromx+length, fromy).disableVert(BoardCellAvailability.END);

                b.getCell(fromx+length, fromy).disableHoriz(BoardCellAvailability.START);
                b.getCell(fromx+length, fromy).disableHoriz(BoardCellAvailability.INNER);
                b.getCell(fromx+length, fromy).disableHoriz(BoardCellAvailability.END);
            }
        }


        if(d.equals(CwEntry.Direction.HORIZ)){
            for(int i=0; i<length; i++) {
                b.getCell(fromx, fromy+i).disableHoriz(BoardCellAvailability.START);
                b.getCell(fromx, fromy+i).disableHoriz(BoardCellAvailability.INNER);
                b.getCell(fromx, fromy+i).disableHoriz(BoardCellAvailability.END);

                if(fromx-1>=0){
                    b.getCell(fromx-1, fromy+i).disableHoriz(BoardCellAvailability.START);
                    b.getCell(fromx-1, fromy+i).disableHoriz(BoardCellAvailability.INNER);
                    b.getCell(fromx-1, fromy+i).disableHoriz(BoardCellAvailability.END);

                    b.getCell(fromx-1, fromy+i).disableVert(BoardCellAvailability.END);
                }

                if(fromx+1<size){
                    b.getCell(fromx+1, fromy+i).disableHoriz(BoardCellAvailability.START);
                    b.getCell(fromx+1, fromy+i).disableHoriz(BoardCellAvailability.INNER);
                    b.getCell(fromx+1, fromy+i).disableHoriz(BoardCellAvailability.END);

                    b.getCell(fromx+1, fromy+i).disableVert(BoardCellAvailability.START);
                }
            }

            if(fromy-1>=0){
                b.getCell(fromx, fromy-1).disableVert(BoardCellAvailability.START);
                b.getCell(fromx, fromy-1).disableVert(BoardCellAvailability.INNER);
                b.getCell(fromx, fromy-1).disableVert(BoardCellAvailability.END);

                b.getCell(fromx, fromy-1).disableHoriz(BoardCellAvailability.START);
                b.getCell(fromx, fromy-1).disableHoriz(BoardCellAvailability.INNER);
                b.getCell(fromx, fromy-1).disableHoriz(BoardCellAvailability.END);
            }

            if(fromy+length<size){
                b.getCell(fromx, fromy+length).disableVert(BoardCellAvailability.START);
                b.getCell(fromx, fromy+length).disableVert(BoardCellAvailability.INNER);
                b.getCell(fromx, fromy+length).disableVert(BoardCellAvailability.END);

                b.getCell(fromx, fromy+length).disableHoriz(BoardCellAvailability.START);
                b.getCell(fromx, fromy+length).disableHoriz(BoardCellAvailability.INNER);
                b.getCell(fromx, fromy+length).disableHoriz(BoardCellAvailability.END);
            }
        }

    }

}
