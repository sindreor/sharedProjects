package ponggame.pongmvc;

import android.view.MotionEvent;

import java.util.Iterator;

import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

/**
 * Created by Sindre on 16.02.2016.
 */
public class Paddle extends Sprite{
    public Paddle(){
        super(new Image(R.drawable.paddle));
    }
}
