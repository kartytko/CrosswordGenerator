package main.board;

import java.util.LinkedList;

//Jedna z głównych klas projektu.
//Przechowuje informacje o zawartości poszczególnych komórek krzyżówki

public class Board implements Cloneable{
    private BoardCell[][] board;

    public Board(int x, int y){             //public przed konstruktorem, bo nie mogłam wywołać go w Crossword
        board = new BoardCell[y][x];
    }

    public Board cloneMyself()throws CloneNotSupportedException{
        return (Board)this.clone();
    }

    public int getWidth(){ //amount of columns
        return board[0].length;
    }

    public int getHeight(){ //amount of rows
        return board.length;
    }

    public BoardCell getCell(int x, int y){
        BoardCell tmp = board[x][y];
        return tmp;
    }

    public void setCell (int x, int y, BoardCell c){
        board[x][y] = c;
    }



    //Funkcja pobierająca wszystkie komórki, których "stan dostępności" (BoardCellAvailibity) pozwala na rozpoczęcie hasła od tego miejsca
    //Funkcja nieużywana - może pomóc w optymalizacji w trakcie szukania kolejnych haseł (klasa Advanced oraz klasa Basic)
    public LinkedList<BoardCell> getStartCells(){
        LinkedList<BoardCell> start_cells = new LinkedList<>();

        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[row].length; col++){
                if (board[row][col].getHoriz().getStart() || board[row][col].getVert().getStart()){
                    start_cells.add(board[row][col]);
                }
            }
        }
        return start_cells;
    }



    //Funkcja pozwala na stworzenie patternu do Regexu na podstawie zawartośc komórek od A=(fromx, fromy) do B=(tox, toy)
    public String createPattern(int fromx, int fromy, int tox, int toy){
        String pattern = "";

        if(fromx == tox) { // vertical
            for(int i = fromy; i <= toy; i++){
                String single_cell_content = board[i][fromx].getContent();
                if(single_cell_content.equals("")){
                    pattern = pattern + ".";
                }else {
                    pattern = pattern + single_cell_content;
                }
            }
        }

        if(fromy == toy) { // horizontal
            for(int i = fromx; i <= tox; i++){
                String single_cell_content = board[fromy][i].getContent();
                if(single_cell_content.equals("")){
                    pattern = pattern + ".";
                }else {
                    pattern = pattern + single_cell_content;
                }
            }

        }
        return pattern;
    }
}
