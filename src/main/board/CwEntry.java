package main.board;

import main.dictionary.Entry;

//Klasa reprezentująca pojedyncze hasło krzyżówki
//Przechowuje hasło i podpowiedź, współrzędne początkowe oraz informację o tym, czy hasło jest pionowe, czy poziome

public class CwEntry extends Entry {
    public enum Direction {
        HORIZ, VERT;
    }

    private int x;
    private int y;
    private Direction d;

    public CwEntry(String word, String clue){
        super(word, clue);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Direction getDir(){
        return d;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setDir(Direction d){
        this.d = d;
    }

}
