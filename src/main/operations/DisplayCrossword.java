package main.operations;

import main.board.CwEntry;
import main.crossword.Advanced;
import main.crossword.Basic;
import main.crossword.Crossword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


//niedokończone!!!!!!
public class DisplayCrossword implements Reader {
    private String path_from;
    public enum strategy{ BASIC, ADVANCED;}
    public strategy strat;
    public DisplayCrossword(String path_from){
        this.path_from = path_from;
    }
    public int size = 10;


    @Override
    public Crossword read() {
        //Crossword crossword = new Crossword();
        try{
            FileReader fileReader = new FileReader(path_from);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String textLine = bufferedReader.readLine();
            size = Integer.parseInt(textLine);
            int iterator = 0;


            Crossword new_crossword = new Crossword(size);
            LinkedList<CwEntry> new_entries = new LinkedList<>();
            while((textLine = bufferedReader.readLine())!= null){
                String word = bufferedReader.readLine();
                String clue = bufferedReader.readLine();
                int x = Integer.parseInt(bufferedReader.readLine());
                int y = Integer.parseInt(bufferedReader.readLine());
                String direction = bufferedReader.readLine();


                CwEntry new_cwentry = new CwEntry(word, clue);
                new_cwentry.setX(x);
                new_cwentry.setY(y);
                if(direction.equals("HORIZ")){
                    new_cwentry.setDir(CwEntry.Direction.HORIZ);
                }else{
                    new_cwentry.setDir(CwEntry.Direction.VERT);
                }

                new_entries.add(new_cwentry);


                if(iterator==0){
                    if(x==0 && y==0){
                        strat = strategy.BASIC;
                    }else{
                        strat = strategy.ADVANCED;
                    }

                }
                iterator++;
            }
            if(strat.equals(strategy.BASIC)){
                for(int i=0; i<new_entries.size(); i++){
                    new_crossword.addCwEntry(new_entries.get(i), new Basic(size));          //BASIC SIZE
                }
            }
            if(strat.equals(strategy.ADVANCED)){
                new_crossword.setAvailability();
                for(int i=0; i<new_entries.size(); i++){
                    new_crossword.addCwEntry(new_entries.get(i), new Advanced());
                }
            }


            return new_crossword;



        }catch(IOException e){
            e.printStackTrace();
        }
        return new Crossword(size);
    }
}
