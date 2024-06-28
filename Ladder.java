public class Ladder {
    private int topPosition;
    private int bottomPosition;

    public Ladder(int initTop, int initBot){
        this.topPosition = initTop;
        this.bottomPosition = initBot;
    }
    public void setTopPosition(int setTop){
        this.topPosition = setTop;
    }
    public void setBottomPosition(int setBot){
        this.bottomPosition = setBot;
    }
    public int getTopPosition(){
        return this.topPosition;
    }
    public int getBottomPosition(){
        return this.bottomPosition;
    }
}