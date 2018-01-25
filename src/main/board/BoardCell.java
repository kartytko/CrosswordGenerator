package main.board;

public class BoardCell{

    private String content = "";
    private State Horiz = new State();
    private State Vert = new State();


    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return content;
    }
    public State getHoriz() {
        return Horiz;
    }
    public State getVert() {
        return Vert;
    }

    public void enableHoriz(BoardCellAvailability availability){
        if(availability.equals(BoardCellAvailability.START)){
            Horiz.setStart(true);
        }else if(availability.equals(BoardCellAvailability.INNER)){
            Horiz.setInner(true);
        }else{
            Horiz.setEnd(true);
        }
    }

    public void disableHoriz(BoardCellAvailability availability){
        if(availability.equals(BoardCellAvailability.START)){
            Horiz.setStart(false);
        }else if(availability.equals(BoardCellAvailability.INNER)){
            Horiz.setInner(false);
        }else{
            Horiz.setEnd(false);
        }
    }

    public void enableVert(BoardCellAvailability availability){
        if(availability.equals(BoardCellAvailability.START)){
            Vert.setStart(true);
        }else if(availability.equals(BoardCellAvailability.INNER)){
            Vert.setInner(true);
        }else{
            Vert.setEnd(true);
        }
    }

    public void disableVert(BoardCellAvailability availability){
        if(availability.equals(BoardCellAvailability.START)){
            Vert.setStart(false);
        }else if(availability.equals(BoardCellAvailability.INNER)){
            Vert.setInner(false);
        }else{
            Vert.setEnd(false);
        }
    }
}



