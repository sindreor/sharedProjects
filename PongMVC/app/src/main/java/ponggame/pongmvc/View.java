package ponggame.pongmvc;

import android.graphics.Canvas;
import android.graphics.Typeface;

import java.util.ArrayList;

import sheep.game.Sprite;
import sheep.graphics.Font;
import sheep.graphics.Image;

/**
 * Created by Sindre on 16.02.2016.
 */
public class View {
    private Paddle player1;
    private Paddle player2;
    private Ball ball;
    private float width;
    private float height;
    private Sprite background;


    public View(float width, float height){
        player1=new Paddle();
        player2=new Paddle();
        ball=new Ball();
        background=new Sprite(new Image(R.drawable.background));
        this.width=width;
        this.height=height;

    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPlayer1() {
        return player1;
    }

    public Paddle getPlayer2() {
        return player2;
    }

    public void updateView(float dt){
        player1.update(dt);
        player2.update(dt);
        ball.update(dt);
    }
    public void drawView(Canvas canvas, int p1, int p2){
        background.draw(canvas);
        player1.draw(canvas);
        player2.draw(canvas);
        ball.draw(canvas);

        canvas.drawText(p1 + "", 30, 30, new Font(0, 0, 0, 30, Typeface.SANS_SERIF, 1));
        canvas.drawText(p2 + "", width - 30, 30, new Font(0, 0, 0, 30, Typeface.SANS_SERIF, 1));


    }

    public void drawText(Canvas canvas, String text, float width, float height){
        canvas.drawText(text,width,height, new Font(0,0,0,30, Typeface.SANS_SERIF, 1));
    }


}
