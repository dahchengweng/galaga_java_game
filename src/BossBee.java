import java.awt.Color;
import java.awt.Graphics;

public class BossBee extends Alien {
    public BossBee(int x, int y, int screenWidth) {
        // 寬40, 高30, 速度1, 血量2, 分數400分
        super(x, y, 40, 30, 1, 2, 400, screenWidth);
    }

    @Override
    public void draw(Graphics g) {
        // 根據剩餘血量改變顏色（經典 Galaga 機制：受傷變色）
        if (health == 2) {
            g.setColor(Color.GREEN); // 滿血綠色
        } else {
            g.setColor(Color.BLUE); // 受傷變藍色
        }
        g.fillRect(x + 8, y, 24, height);
        g.fillRect(x, y + 8, width, 12); // 更寬的魔王翅膀
    }
}
