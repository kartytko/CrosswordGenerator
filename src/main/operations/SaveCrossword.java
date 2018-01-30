package main.operations;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import main.board.CwEntry;
import main.crossword.Crossword;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

//Klasa pozwalająca na zapisanie krzyżówki do pliku
//Serializuje dane z krzyżówki - do pliku dodaje informacje o każdym haśle (położenie na planszy, hasło, podpowiedź, informację o tym, czy hasło jest pionowe/poziome)

public class SaveCrossword implements Writer{

    private String path = "";

    public SaveCrossword(String path){
        this.path = path;
    }

    @Override
    public boolean write(Crossword crossword) {
        Iterator <CwEntry> it = crossword.getROEntryIter();

        String new_path = path +"/"+ getUniqueId() +".txt";
        try {
            FileWriter fileWriter = new FileWriter(new_path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(new Integer(crossword.getSize()).toString());
            bufferedWriter.newLine();

            while(it.hasNext()){
                bufferedWriter.write(" ");
                bufferedWriter.newLine();
                CwEntry cwentry= it.next();
                String word = cwentry.getWord();
                bufferedWriter.write(word);
                bufferedWriter.newLine();

                String clue = cwentry.getClue();
                bufferedWriter.write(clue);
                bufferedWriter.newLine();

                int x = cwentry.getX();
                String x_ = new Integer(x).toString();
                bufferedWriter.write(x_);
                bufferedWriter.newLine();

                int y = cwentry.getY();
                String y_ = new Integer(y).toString();
                bufferedWriter.write(y_);
                bufferedWriter.newLine();

                CwEntry.Direction d = cwentry.getDir();
                if(d.equals(CwEntry.Direction.HORIZ)){
                    bufferedWriter.write("HORIZ");
                    bufferedWriter.newLine();
                }else{
                    bufferedWriter.write("VERT");
                    bufferedWriter.newLine();
                }

            }

            bufferedWriter.close();
            return true;

        }catch (IOException e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public long getUniqueId() {
        return System.currentTimeMillis();
    }
}
