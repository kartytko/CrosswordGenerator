package main.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class CwDB {
    protected LinkedList<Entry> dict = new LinkedList<>();

    public CwDB (String filename){
        createDB(filename);
    };

    protected void createDB(String filename){
        String word_tmp;
        String clue_tmp;

        try{
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String textLine = "";

            while(textLine!=null){
                textLine = bufferedReader.readLine();
                word_tmp = textLine;
                textLine = bufferedReader.readLine();
                clue_tmp = textLine;

                if(clue_tmp==null || word_tmp==null){
                    break;
                }
                add(word_tmp, clue_tmp);
            }

            bufferedReader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void add(String word, String clue){
        Entry tmp = new Entry(word, clue);
        dict.add(tmp);
    }

    public Entry get(String word){
        for(int i=0; i<dict.size(); i++){
            if(word.equals(dict.get(i).getWord())){
                String word_tmp = dict.get(i).getWord();
                String clue_tmp = dict.get(i).getClue();
                Entry tmp = new Entry(word_tmp, clue_tmp);
                return tmp;
            }
        }
        return new Entry("", ""); //returns empty string when given word is not found in dict
    }

    public void remove (String word){
        for(int i=0; i<dict.size(); i++){
            if(word.equals(dict.get(i).getWord())){
                dict.remove(i);
            }
        }
    }

    //poniższe klasy???
    public void saveDB(String filename){}
    public int getSize(){ return dict.size();}


}
