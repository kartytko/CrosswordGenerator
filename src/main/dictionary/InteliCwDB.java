package main.dictionary;

import main.dictionary.CwDB;
import main.dictionary.Entry;

import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InteliCwDB extends CwDB {
    public InteliCwDB(String filename){
        super(filename);
    }

    public LinkedList<Entry> findAll(String pattern){
        LinkedList<Entry> answer = new LinkedList<>();
        Pattern patt = Pattern.compile(pattern);
        Matcher matcher;

        for(int i=0; i<getSize(); i++){
            String word_tmp = dict.get(i).getWord();
            String clue_tmp = dict.get(i).getClue();
            matcher = patt.matcher(word_tmp);
            if(matcher.matches()){
                answer.add(new Entry(word_tmp, clue_tmp));
            }
        }
        return answer;
    }

    public LinkedList<Entry> findAll (int length){
        LinkedList<Entry> answer = new LinkedList<>();
        for(int i=0; i<getSize(); i++){
            if(dict.get(i).getWord().length() == length){
                String word_tmp = dict.get(i).getWord();
                String clue_tmp = dict.get(i).getClue();
                Entry tmp = new Entry(word_tmp, clue_tmp);
                answer.add(tmp);
            }
        }
        return answer;
    }


    public Entry getRandom(){
        Random generator = new Random();
        int index = generator.nextInt(getSize());
        return dict.get(index);
    }


    public Entry getRandom(int length){
        LinkedList<Entry> specific_length = findAll(length);

        Random generator = new Random();
        int index = generator.nextInt(specific_length.size());

        return specific_length.get(index);
    }


    public Entry getRandom (String pattern){
        LinkedList<Entry> matches = findAll(pattern);

        Random generator = new Random();
        int index = generator.nextInt(matches.size());

        return matches.get(index);
    }

    @Override
    public void add(String word, String clue){   //adds Entries in alphabetical order into dict
        Entry entry = new Entry(word, clue);
        boolean added = false;


        if(getSize()>1) {
            for (int i = 0; i < getSize(); i++) {
                String word_tmp = dict.get(i).getWord();
                if (compare(word, word_tmp)) {
                    dict.add(i, entry);
                    added = true;
                    break;
                }
            }
        }
        if(!added){
            dict.add(entry);
        }

    }

    public boolean compare(String word1, String word2){ //retruns true if word1 should appear first
        String w1 = word1.toLowerCase();
        String w2 = word2.toLowerCase();
        int w1_length = word1.length();
        int w2_length = word2.length();
        int shorter = w1_length;

        if(w1_length > w2_length){
            shorter = w2_length;
        }

        for(int i=0; i<shorter; i++){
            if(w1.charAt(i) < w2.charAt(i)){
                return true;
            }
            if(w1.charAt(i) > w2.charAt(i)){
                return false;
            }
        }

        //if one of the words includes the other eg. "deal" + "dealer"
        if(shorter == w1_length){
            return true;
        }else{
            return false;
        }
    }

}
