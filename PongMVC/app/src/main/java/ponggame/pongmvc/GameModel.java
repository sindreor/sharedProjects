package ponggame.pongmvc;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

import sheep.game.Game;
import sheep.game.State;

/**
 * Created by Sindre on 16.02.2016.
 */
public class GameModel{
    private Counter game;

    private GameObject player1;
    private GameObject player2;
    private GameObject ball;
    private float width;
    private float height;
    private boolean p1Win;
    private boolean p2Win;
    private ArrayList<GameModelListener> listeners;

    public GameModel(){
        game=Counter.getInstance();
        listeners=new ArrayList<>();
        p1Win=false;
        p2Win=false;
        newGame();

    }

    public void addGameModelListener(GameModelListener listener){
        listeners.add(listener);
    }

    private void firePropertyChanged(){
        for (GameModelListener l:listeners){
            l.updateView();
        }
    }
    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width){
        this.width=width;
    }

    public GameObject getBall() {
        return ball;
    }

    public GameObject getPlayer1() {
        return player1;
    }

    public GameObject getPlayer2() {
        return player2;
    }


    public boolean isP1Win() {
        return p1Win;
    }

    public boolean isP2Win() {
        return p2Win;
    }

    public void newGame(){
        player1=new GameObject(60, height/2,0,0);
        player2=new GameObject(width-60, height/2,0,0);
        p1Win=false;
        p2Win=false;
        Counter.getInstance().reset();
        restartBall();


    }

    public void restartBall(){
        Random rand=new Random();
        int[] choices={-1,1};
        ball=new GameObject(width / 2, height / 2, choices[rand.nextInt(2)] * (60+rand.nextInt(80)),choices[rand.nextInt(2)] *(60+rand.nextInt(80)));
        firePropertyChanged();
    }

    public void edgeCollisionTest(){
        if(ball.getX()<0){

            game.givePointP2();
            if(game.isWin()){
                ball.setX(width / 2);
                ball.setY(height / 2);
                ball.setSpeedX(0);
                ball.setSpeedY(0);
                p2Win=true;
                firePropertyChanged();
            }
            else{
                restartBall();
            }


        }
        else if(ball.getX()>width&&width>0){

            game.givePointP1();
            if(game.isWin()){
                ball.setX(width / 2);
                ball.setY(height / 2);
                ball.setSpeedX(0);
                ball.setSpeedY(0);
                p1Win=true;
                firePropertyChanged();
            }
            else{
                restartBall();
            }
        }

        if(ball.getY()<=15||ball.getY()>height-30){
            ball.setSpeedX(ball.getSpeedX());
            ball.setSpeedY(-ball.getSpeedY());
            firePropertyChanged();
        }
    }

    public void notifyCollision(boolean type) {
            ball.setSpeedX(-(ball.getSpeedX()*(float)1.1));
            ball.setSpeedY(ball.getSpeedY()*(float)1.1);
        if(type){
            ball.setX(ball.getX()+1);
        }
        else{
            ball.setX(ball.getX()-1);
        }
        firePropertyChanged();
    }






}
