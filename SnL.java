import java.util.*;
import java.io.*;
import javax.sound.sampled.*;
public class SnL {
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private ArrayList<Player> players;
    private int boardSize;
    private int gameStatus;
    private int nowPlaying;
    //0 = started
    //1 = on going
    //2 = ended
    public SnL(){
        this.snakes = new ArrayList<Snake>();
        this.ladders = new ArrayList<Ladder>();
        this.players = new ArrayList<Player>();
        this.gameStatus = 0;
    }
    public void setBoardSize(int initSize){
        this.boardSize = initSize;
    }
    public void setGameStatus(int setStatus){
        this.gameStatus = setStatus;
    }
    public int getGameStatus(){
        return  this.gameStatus;
    }
    public void addSnakes(Snake s){
        this.snakes.add(s);
    }
    public void addLadder(Ladder l){
        this.ladders.add(l);
    }

    public void addLadders(int l[][]){
        for(int r = 0; r < l.length; r++){
            Ladder ladder = new Ladder(l[r][1], l[r][0]);
            this.ladders.add(ladder);
        }
    }
    public void addSnakes(int s[][]){
        for(int r = 0; r < s.length; r++){
            Snake snake = new Snake(s[r][0], s[r][1]);
            this.snakes.add(snake);
        }
    }

    public void addPlayers(Player p){
        this.players.add(p);
    }
    public int getBoardSize(){
        return this.boardSize;
    }
    public ArrayList<Snake> getSnakes(){
        return this.snakes;
    }
    public ArrayList<Ladder> getLadders(){
        return this.ladders;
    }
    public ArrayList<Player> getPlayers(){
        return this.players;
    }
    public void initiateGame(String difficulty){
        if(difficulty.equals("large")){
            this.boardSize = 100;
            int l[][] = {
                    {2, 23},
                    {8, 34},
                    {20, 77},
                    {32, 68},
                    {41, 79},
                    {74, 88},
                    {82, 100},
                    {85, 95}
            };
            addLadders(l);
            int s[][] = {
                    {5, 47},
                    {9, 29},
                    {15, 38},
                    {25, 97},
                    {33, 53},
                    {37, 62},
                    {54, 86},
                    {70, 92}
            };
            addSnakes(s);
        }
        else if(difficulty.equals("medium")){
            this.boardSize = 60;
            int l[][] = {
                    {3, 13},
                    {7, 14},
                    {11, 28},
                    {30, 45},
                    {36, 56},
                    {42, 59}
            };
            addLadders(l);
            int s[][] = {
                    {1, 13},
                    {10, 25},
                    {26, 44},
                    {47, 58},
                    {38, 45},
                    {5, 27}
            };
            addSnakes(s);
        }
        else if(difficulty.equals("small")){
            this.boardSize = 30;
            int l[][] = {
                    {3, 22},
                    {5, 8},
                    {11, 26},
                    {20, 29},

            };
            addLadders(l);
            int s[][] = {
                    {1, 27},
                    {4, 17},
                    {7, 19},
                    {9, 21},
            };
            addSnakes(s);
        }
    }
    public void movePlayerAround(Player p, int dice){
        p.moveAround(dice, this.boardSize);
        for(Ladder l : this.ladders){
            if(p.getPosition() == l.getBottomPosition()){
                p.setPosition(l.getTopPosition());
                System.out.println(p.getName() + " you got ladder from " + l.getBottomPosition() + " to " + l.getTopPosition());
            }
        }
        for(Snake s : this.snakes){
            if(p.getPosition() == s.getHeadPosition()){
                p.setPosition(s.getTailPosition());
                System.out.println(p.getName() + " you got snake from " + s.getHeadPosition() + " to " + s.getTailPosition());
            }
        }
        // Check if player lands on the same position as another player
        for (Player otherPlayer : this.players){
            if(!otherPlayer.equals(p) && otherPlayer.getPosition() == p.getPosition()){
                p.setPosition(0);
                System.out.println(p.getName() + " landed on the same spot as " + otherPlayer.getName() + " and should back to start!");
            }

        }

        if(p.getPosition() == this.boardSize) this.gameStatus = 2;
    }
    public Player getWhoseTurn(){
        if(this.gameStatus == 0){
            this.gameStatus = 1;
            double r = Math.random();
            if(r<=0.5){
                this.nowPlaying = 0;
                return this.players.get(0);
            }
            else{
                this.nowPlaying = 1;
                return this.players.get(1);
            }
        }
        else{
            if(nowPlaying == 0){
                this.nowPlaying = 1;
                return this.players.get(1);
            }
            else{
                this.nowPlaying = 0;
                return this.players.get(0);
            }
        }
    }

    public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to Snake and Ladder game !!!");
        System.out.println("Please select the number of player : ");
        System.out.println("   (1) 1 Player");
        System.out.println("   (2) 2 Player");
        System.out.print("Enter number of player : ");
        int numPlayer = Integer.parseInt(scan.nextLine());
        if(numPlayer == 1){
            System.out.print("Enter player name : ");
            String playerName = scan.nextLine();
            System.out.print("Select board size (small/medium/large) : ");
            String gameSize = scan.nextLine();
            gameSize = gameSize.toLowerCase();
            System.out.print("Number of Dice? (1/2) : ");
            int numDice = Integer.parseInt(scan.nextLine());
            System.out.println();

            Player p1 = new Player(playerName);
            Player p2 = new Player("Computer");
            addPlayers(p1);
            addPlayers(p2);
            initiateGame(gameSize);

            if (gameSize.equals("small")) System.out.println("Whoever gets to number 30 first is the winner!");
            else if (gameSize.equals("medium")) System.out.println("Whoever gets to number 60 first is the winner!");
            else if (gameSize.equals("large")) System.out.println("Whoever gets to number 100 first is the winner!");
            System.out.println();

            Player playerInTurn;

            do {
                File file1 = new File("SuaraDadu.WAV");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file1);
                Clip clip = AudioSystem.getClip();
                playerInTurn = getWhoseTurn();
                System.out.println("----------------------------------------");
                System.out.println("Now Playing: " + playerInTurn.getName());
                if(playerInTurn.getName() == p1.getName()) {
                    System.out.print(playerInTurn.getName() + " please press enter to roll the dice");
                    String enter = scan.nextLine();
                    clip.open(audioStream);
                    clip.start();
                    Thread.sleep(1500);
                    if (enter.equals("")) {
                        int x = 0;
                        if (numDice == 1) x = playerInTurn.rollDice();
                        else if (numDice == 2) x = playerInTurn.roll2Dice();
                        System.out.println("Dice Number: " + x);
                        movePlayerAround(playerInTurn, x);
                        System.out.println("Current Position : " + playerInTurn.getPosition());
                        if ((numDice == 1 && x == 6) || (numDice == 2 && x == 12)) {
                            int y = playerInTurn.rollAgain(x, numDice);
                            System.out.println("Dice Number: " + y);
                            movePlayerAround(playerInTurn, y);
                            System.out.println("Current Position : " + playerInTurn.getPosition());
                        }
                    }
                }else if (playerInTurn.getName() == p2.getName()) {
                    clip.open(audioStream);
                    clip.start();
                    Thread.sleep(1500);
                    int x = 0;
                    if (numDice == 1) x = playerInTurn.rollDice();
                    else if (numDice == 2) x = playerInTurn.roll2Dice();
                    System.out.println("Dice Number: " + x);
                    movePlayerAround(playerInTurn, x);
                    System.out.println("Current Position : " + playerInTurn.getPosition());
                    if ((numDice == 1 && x == 6) || (numDice == 2 && x == 12)) {
                        int y = playerInTurn.computerRollAgain(x, numDice);
                        System.out.println("Dice Number: " + y);
                        movePlayerAround(playerInTurn, y);
                        System.out.println("Current Position : " + playerInTurn.getPosition());
                    }
                }
            } while (getGameStatus() != 2);

            System.out.println();
            System.out.println("================================");
            if (playerInTurn.getPosition() == 100) {
                System.out.println("The Winner is " + playerInTurn.getName());
            } else System.out.println("The Winner is " + playerInTurn.getName());
            System.out.println("================================");
            File file2 = new File("SuaraMenang.WAV");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file2);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            Thread.sleep(6000);
            clip.stop();
        }
        else if(numPlayer == 2) {
            System.out.print("Enter player 1: ");
            String player1 = scan.nextLine();
            System.out.print("Enter player 2: ");
            String player2 = scan.nextLine();
            System.out.print("Select board size (small/medium/large) : ");
            String gameSize = scan.nextLine();
            gameSize = gameSize.toLowerCase();
            System.out.print("Number of Dice? (1/2) : ");
            int numDice = Integer.parseInt(scan.nextLine());
            System.out.println();

            Player p1 = new Player(player1);
            Player p2 = new Player(player2);

            addPlayers(p1);
            addPlayers(p2);
            initiateGame(gameSize);

            if (gameSize.equals("small")) System.out.println("Whoever gets to number 30 first is the winner!");
            else if (gameSize.equals("medium")) System.out.println("Whoever gets to number 60 first is the winner!");
            else if (gameSize.equals("large")) System.out.println("Whoever gets to number 100 first is the winner!");
            System.out.println();

            Player playerInTurn;

            do {
                File file1 = new File("SuaraDadu.WAV");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file1);
                Clip clip = AudioSystem.getClip();
                playerInTurn = getWhoseTurn();
                System.out.println("----------------------------------------");
                System.out.println("Now Playing: " + playerInTurn.getName());
                System.out.print(playerInTurn.getName() + " please press enter to roll the dice");
                String enter = scan.nextLine();
                clip.open(audioStream);
                clip.start();
                Thread.sleep(1500);
                if (enter.equals("")) {
                    int x = 0;
                    if (numDice == 1) x = playerInTurn.rollDice();
                    else if (numDice == 2) x = playerInTurn.roll2Dice();
                    System.out.println("Dice Number: " + x);
                    movePlayerAround(playerInTurn, x);
                    System.out.println("Current Position : " + playerInTurn.getPosition());
                    if ((numDice == 1 && x == 6) || (numDice == 2 && x == 12)) {
                        int y = playerInTurn.rollAgain(x, numDice);
                        System.out.println("Dice Number: " + y);
                        movePlayerAround(playerInTurn, y);
                        System.out.println("Current Position : " + playerInTurn.getPosition());
                    }
                }
            } while (getGameStatus() != 2);

            System.out.println();
            System.out.println("================================");
            if (playerInTurn.getPosition() == 100) {
                System.out.println("The Winner is " + playerInTurn.getName());
            } else System.out.println("The Winner is " + playerInTurn.getName());
            System.out.println("================================");
            File file2 = new File("SuaraMenang.WAV");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file2);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            Thread.sleep(6000);
            clip.stop();
        }
    }
}