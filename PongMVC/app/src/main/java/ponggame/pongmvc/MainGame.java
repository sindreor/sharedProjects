package ponggame.pongmvc;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import sheep.game.Game;


public class MainGame extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Game game = new Game(this, null);
        // Push the main state.
        game.pushState(new GameController(new View(getWidth(), getHeight()),new GameModel()));
        // View the game.
        setContentView(game);
    }

    private float getWidth(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size =new Point();
        display.getSize(size);
        return (float)size.x;
    }
    private float getHeight(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size =new Point();
        display.getSize(size);
        return (float)size.y;
    }


}
