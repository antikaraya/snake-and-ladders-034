import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class Player {
    private String name;
    private int position;
    private Scanner scan = new Scanner(System.in);

    public Player(String nama){
        this.name=nama;
        this.position=0;
    }

    public void setName(String nama){
        this.name = nama;
    }

    public void setPosition(int position){
        this.position = position;
    }
    public String getName(){
        return this.name;
    }
    public int getPosition(){
        return this.position;
    }
    public int rollDice() {
        return (int)(Math.random()*6+1);
    }
    public int roll2Dice(){
        return (int)(Math.random()*6+1) + (int)(Math.random()*6+1);
    }
    public int rollAgain(int x, int numDice) throws LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException {
        if(x == 6 || x == 12){
            File file1 = new File("SuaraDadu.WAV");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file1);
            Clip clip = AudioSystem.getClip();
            System.out.print("You can roll the dice again! please press enter to roll the dice");
            String enter = scan.nextLine();
            clip.open(audioStream);
            clip.start();
            Thread.sleep(1500);
            if(numDice == 1) x = rollDice();
            else if(numDice == 2) x = roll2Dice();
        }
        return x;
    }

    public int computerRollAgain(int x, int numDice) throws LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException{
        if(x == 6 || x == 12){
            File file1 = new File("SuaraDadu.WAV");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file1);
            Clip clip = AudioSystem.getClip();
            System.out.print("You can roll the dice again! please roll the dice");
            clip.open(audioStream);
            clip.start();
            Thread.sleep(1500);
            if(numDice == 1) x = rollDice();
            else if(numDice == 2) x = roll2Dice();
        }
        return x;
    }
    public void moveAround(int y, int boardSize) {
        if(this.position + y > boardSize) {
            this.position = boardSize-(this.position + y)%boardSize;
        }
        else {
            this.position = this.position + y;
        }
    }
}