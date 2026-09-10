import java.awt.Graphics;

public abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected int speed;

    public GameObject(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public abstract void update();
    public abstract void draw(Graphics g);

    // 碰撞偵測 (AABB)
    public boolean intersects(GameObject other) {
        return this.x < other.x + other.width &&
               this.x + this.width > other.x &&
               this.y < other.y + other.height &&
               this.y + this.height > other.y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
