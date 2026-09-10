import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends GameObject {

    private int speed = 2;

    private int direction = 1;

    public Enemy(
            int x,
            int y,
            int width,
            int height
    ) {

        super(x, y, width, height);
    }

    @Override
    public void update() {

        x += speed * direction;
    }

    public void update(int screenWidth) {

        x += speed * direction;

        if (x <= 0 ||
            x + width >= screenWidth) {

            direction *= -1;

            y += 15;
        }
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.RED);

        g.fillRect(
                x,
                y,
                width,
                height
        );

        g.setColor(Color.WHITE);

        g.fillOval(
                x + 8,
                y + 8,
                8,
                8
        );

        g.fillOval(
                x + 24,
                y + 8,
                8,
                8
        );
    }
}