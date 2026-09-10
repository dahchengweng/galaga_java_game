import java.awt.Graphics;

public abstract class Alien extends GameObject {
    protected int direction = 1; // 1: 向右, -1: 向左
    protected int health;
    protected int points;
    private int screenWidth;

    public Alien(int x, int y, int width, int height, int speed, int health, int points, int screenWidth) {
        super(x, y, width, height, speed);
        this.health = health;
        this.points = points;
        this.screenWidth = screenWidth;
    }

    @Override
    public void update() {
        x += speed * direction;
        
        // 經典的 Galaga 橫向隊形移動
        if (x <= 10 || x >= screenWidth - width - 10) {
            direction *= -1;
            y += 10; // 碰壁時整體往下逼近
        }
    }

    // 傳回值代表外星人是否死亡 (health <= 0)
    public boolean takeDamage() {
        health--;
        return health <= 0;
    }

    public int getPoints() { return points; }
}
