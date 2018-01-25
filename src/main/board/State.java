package main.board;

public class State{

    private boolean start = true;
    private boolean inner = true;
    private boolean end = true;

    public boolean getStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean getInner() {
        return inner;
    }

    public void setInner(boolean inner) {
        this.inner = inner;
    }

    public boolean getEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }


}
