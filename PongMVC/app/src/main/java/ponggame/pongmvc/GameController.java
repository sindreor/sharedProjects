package ponggame.pongmvc;

import android.graphics.Canvas;
import android.view.MotionEvent;

import sheep.game.State;


/**
 * Created by Sindre on 17.02.2016.
 */
public class GameController extends State implements GameModelListener{
    private View view;
    private GameModel model;
    private float timer;
    private int countdown;


    public GameController(View view, GameModel model){
        this.view=view;
        this.model=model;
        timer=0;
        countdown=5;
        model.addGameModelListener(this);
        model.setHeight(view.getHeight());
        model.setWidth(view.getWidth());
        model.newGame();
        updateView();

    }


    public void draw(Canvas canvas){
        view.drawView(canvas, Counter.getInstance().getPointsP1(), Counter.getInstance().getPointsP2());
        if(model.isP1Win()){
            view.drawText(canvas, "Player 1 wins", view.getWidth() / 3, view.getHeight() / 2);
            startNewGame(canvas);
        }
        if(model.isP2Win()){
            view.drawText(canvas, "Player 2 wins", view.getWidth()/3, view.getHeight() / 2);
            startNewGame(canvas);
        }

        }
    private void startNewGame(Canvas canvas){
        if(timer>=1000) {
            countdown-=1;
            timer=0;
        }
        view.drawText(canvas, "New game starts in... "+countdown, view.getWidth()/3, (view.getHeight() / 2)+60);
        if(countdown==0) {
            countdown=5;
            model.newGame();
            updateView();
        }
    }

    public void updateView(){
        GameObject p1=model.getPlayer1();
        GameObject p2=model.getPlayer2();
        GameObject b=model.getBall();
        Paddle p1v=view.getPlayer1();
        Paddle p2v=view.getPlayer2();
        Ball bv=view.getBall();

        p1v.setPosition(p1.getX(), p1.getY());
        p2v.setPosition(p2.getX(), p2.getY());
        bv.setPosition(b.getX(), b.getY());
        bv.setSpeed(b.getSpeedX(), b.getSpeedY());
    }

    private void updateModel(){
        GameObject p1=model.getPlayer1();
        GameObject p2=model.getPlayer2();
        GameObject b=model.getBall();
        Paddle p1v=view.getPlayer1();
        Paddle p2v=view.getPlayer2();
        Ball bv=view.getBall();

        p1.setX(p1v.getX());
        p1.setY(p1v.getY());
        p2.setX(p2v.getX());
        p2.setY(p2v.getY());
        b.setX(bv.getX());
        b.setY(bv.getY());
        p1.setSpeedX(p1v.getSpeed().getX());
        p2.setSpeedX(p2v.getSpeed().getX());
        b.setSpeedX(bv.getSpeed().getX());
        p1.setSpeedY(p1v.getSpeed().getY());
        p2.setSpeedY(p2v.getSpeed().getY());
        b.setSpeedY(bv.getSpeed().getY());

    }


    public void update(float dt){
        updateModel();
        model.edgeCollisionTest();
        timer+=1000*dt;
        if(view.getPlayer1().collides(view.getBall())){
            model.notifyCollision(true);

        }
        else if(view.getPlayer2().collides(view.getBall())){
            model.notifyCollision(false);
        }

        view.getPlayer1().setPosition(view.getPlayer1().getX(), view.getBall().getY());//Spiller automatisk
        //view.getPlayer2().setPosition(view.getPlayer1().getX(),view.getBall().getY());//Spiller automatisk

        view.updateView(dt);


    }
    public boolean onTouchMove(MotionEvent event) {
        if(event.getX()>view.getWidth()-60 && event.getY()>view.getPlayer2().getY()-40 && event.getY()<view.getPlayer2().getY()+40){
            view.getPlayer2().setPosition(view.getPlayer2().getX(), event.getY());
            model.getPlayer2().setY(event.getY());
        }
        return super.onTouchMove(event);
    }

    public boolean onTouchDown(MotionEvent event) {
        if(event.getX()>view.getWidth()-60 && event.getY()>view.getPlayer2().getY()-40 && event.getY()<view.getPlayer2().getY()+40){
            view.getPlayer2().setPosition(view.getPlayer2().getX(), event.getY());
            model.getPlayer2().setY(event.getY());
        }
        return super.onTouchDown(event);
    }

}
