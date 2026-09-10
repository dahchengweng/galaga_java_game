import java.awt.Color;
import java.awt.Graphics;

public class RedBee extends Alien {
    public RedBee(int x, int y, int screenWidth) {
        // 寬30, 高25, 速度1, 血量1, 分數100分
        super(x, y, 30, 25, 1, 1, 100, screenWidth);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        // 畫出帶有一點翅膀形狀的紅色小蜜蜂
        g.fillRect(x + 5, y, 20, height); 
        g.fillRect(x, y + 5, width, 10); // 翅膀
    }
}
