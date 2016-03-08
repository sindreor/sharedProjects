package ponggame.pongmvc;

/**
 * Created by Sindre on 17.02.2016.
 */
public class GameObject {
    private float x;
    private float y;
    private float speedX;
    private float speedY;

    public GameObject(float x, float y, float speedX, float speedY){
        this.x=x;
        this.y=y;
        this.speedX=speedX;
        this.speedY=speedY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}
