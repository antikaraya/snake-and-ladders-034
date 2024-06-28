public class Snake {
    private int tailPosition;
    private int headPosition;

    public Snake(int initTail, int initHead){
        this.tailPosition = initTail;
        this.headPosition = initHead;
    }

    public void setTailPosition(int setTail){
        this.tailPosition = setTail;
    }

    public void setHeadPosition(int setHead){
        this.headPosition = setHead;
    }

    public int getTailPosition(){
        return this.tailPosition;
    }

    public int getHeadPosition(){
        return this.headPosition;
    }
}