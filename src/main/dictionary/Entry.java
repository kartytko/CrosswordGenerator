package main.dictionary;

//Klasa reprezentująca pojedyncze hasło krzyżówki. Dziedziczy po niej klasa CwEntry

public class Entry {
    private String word;
    private String clue;

    public Entry(String word, String clue){
        this.word = word;
        this.clue = clue;
    }

    public String getWord(){
        return word;
    }

    public String getClue(){
        return clue;
    }
}
