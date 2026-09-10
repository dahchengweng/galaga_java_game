import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject {

    private boolean movingLeft;
    private boolean movingRight;

    private int speed = 6;

    public Player(
            int x,
            int y,
            int width,
            int height
    ) {

        super(x, y, width, height);
    }

    public void setMovingLeft(boolean value) {
        movingLeft = value;
    }

    public void setMovingRight(boolean value) {
        movingRight = value;
    }

    public void update(int screenWidth) {

        if (movingLeft) {
            x -= speed;
        }

        if (movingRight) {
            x += speed;
        }

        // 不可以跑出左邊螢幕
        if (x < 0) {
            x = 0;
        }

        // 不可以跑出右邊螢幕
        if (x + width > screenWidth) {
            x = screenWidth - width;
        }
    }

    @Override
    public void update() {
        // GameObject 要求的方法
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.CYAN);

        int[] xPoints = {
                x + width / 2,
                x,
                x + width
        };

        int[] yPoints = {
                y,
                y + height,
                y + height
        };

        g.fillPolygon(
                xPoints,
                yPoints,
                3
        );
    }
}