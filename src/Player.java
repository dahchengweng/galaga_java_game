import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject {
    private int screenWidth;

    public Player(int x, int y, int screenWidth) {
        super(x, y, 40, 30, 7);
        this.screenWidth = screenWidth;
    }

    @Override
    public void update() {}

    public void moveLeft() {
        if (x > 10) x -= speed;
    }

    public void moveRight() {
        if (x < screenWidth - width - 10) x += speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE); // Galaga 經典白戰機
        // 船身
        g.fillRect(x, y + 10, width, 20);
        // 船頭
        g.fillRect(x + 15, y, 10, 10);
        // 兩側機翼紅點裝飾
        g.setColor(Color.RED);
        g.fillRect(x, y + 20, 5, 10);
        g.fillRect(x + 35, y + 20, 5, 10);
    }
}
