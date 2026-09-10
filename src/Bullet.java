import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends GameObject {
    private boolean active = true;
    private boolean isEnemy; // true: 敵人子彈往下飛, false: 玩家子彈往上飛

    public Bullet(int x, int y, boolean isEnemy) {
        // 敵人子彈速度慢一點(5)，玩家子彈快一點(10)
        super(x, y, 4, 12, isEnemy ? 5 : 10);
        this.isEnemy = isEnemy;
    }

    @Override
    public void update() {
        if (isEnemy) {
            y += speed; // 敵人子彈往下
            if (y > 600) active = false;
        } else {
            y -= speed; // 玩家子彈往上
            if (y < 0) active = false;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isEnemy) {
            g.setColor(Color.RED); // 敵人子彈為紅色
        } else {
            g.setColor(Color.YELLOW); // 玩家子彈為黃色
        }
        g.fillRect(x, y, width, height);
    }

    public boolean isActive() { return active; }
    public boolean isEnemy() { return isEnemy; }
}
