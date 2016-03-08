package ponggame.pongmvc;

/**
 * Created by Sindre on 29.01.2016.
 */
public class Counter {
    private int pointsP1;
    private int pointsP2;
    private static Counter instance=null;

    public static Counter getInstance(){
        if(instance==null){
            instance=new Counter();
        }
        return instance;
    }

    private Counter(){
        pointsP1=0;
        pointsP2=0;
    }
    public void givePointP1(){
        pointsP1++;
    }
    public void givePointP2(){
        pointsP2++;
    }
    public int getPointsP1(){
        return pointsP1;
    }
    public int getPointsP2(){
        return pointsP2;
    }
    public boolean isWin(){
        return  pointsP1>=21 || pointsP2>=21;
    }
    public void reset(){
        pointsP1=0;
        pointsP2=0;
    }
}
