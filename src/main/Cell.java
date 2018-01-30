package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.board.BoardCell;
import main.board.CwEntry;

//Klasa reprezetująca graficznie jedną komórkę krzyżówki

public class Cell {

    public Rectangle rectangle;

    public Rectangle draw(int x, int y){
        Rectangle r = new Rectangle();
        r.setWidth(40);
        r.setHeight(40);

        r.setFill(Color.WHITE);
        r.setStroke(Color.BLACK);

        r.setX(40*(y+1));
        r.setY(40*(x+1));
        return  r;
    }
    public Text resolve(BoardCell boardCell, int x, int y){
        Text letter = new Text(boardCell.getContent().toUpperCase());
        letter.setX(40*(y+1)+17);
        letter.setY(40*(x+1)+23);
        return letter;
    }


    public Rectangle drawBasic(int x, int y){
        Rectangle r = new Rectangle();

        r.setWidth(40);
        r.setHeight(40);
        if(y==0){
            r.setFill(Color.YELLOW);
        }else{
            r.setFill(Color.WHITE);
        }
        r.setStroke(Color.BLACK);

        r.setX(40*(y+1));
        r.setY(40*(x+1));
        return  r;
    }


}
