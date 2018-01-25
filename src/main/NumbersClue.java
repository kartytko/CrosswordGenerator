package main;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import main.board.CwEntry;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class NumbersClue {

    public LinkedList<Text> draw(CwEntry cw, int it){
        LinkedList<Text> list = new LinkedList<>();
        int x = cw.getX();
        int y = cw.getY();
        String clue = cw.getClue();

        String it_string = new Integer(it+1).toString();

        Text number_text = new Text(it_string);
        number_text.setY((x+1)*40+15);
        number_text.setX((y+1)*40+5);
        number_text.setFill(Color.SEAGREEN);

        Text clue_text = new Text(it_string+ ". "+clue);
        clue_text.setY((it+1)*40);
        clue_text.setX(750);
        clue_text.setWrappingWidth(400);
        list.add(clue_text);
        list.add(number_text);
        return list;
    }

    public LinkedList<Text> drawBasic(CwEntry cw, int it){
        LinkedList<Text> list = new LinkedList<>();
        int x = it;
        int y = it;
        String clue = cw.getClue();

        String it_string = new Integer(it+1).toString();

        javafx.scene.text.Text number_text = new javafx.scene.text.Text(it_string);
        number_text.setY((it+1)*40+15);
        number_text.setX(40+5);

        javafx.scene.text.Text clue_text = new javafx.scene.text.Text(it_string+ ". "+clue);
        clue_text.setY((it+1)*40);
        clue_text.setX(750);
        clue_text.setWrappingWidth(400);
        list.add(clue_text);
        list.add(number_text);
        return list;
    }

}
