import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GalagaPanel extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    private Timer timer;
    private Player player;
    private List<Alien> aliens;
    private List<Bullet> bullets;
    private Random rand = new Random();
    
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;

    public GalagaPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
        player = new Player(WIDTH / 2 - 20, HEIGHT - 60, WIDTH);
        aliens = new ArrayList<>();
        bullets = new ArrayList<>();

        // 排出經典的 Galaga 陣列隊形
        // 最上面一排是綠色大魔王 BossBee
        for (int i = 0; i < 6; i++) {
            aliens.add(new BossBee(150 + i * 80, 50, WIDTH));
        }
        // 下面兩排是普通的紅色小蜜蜂 RedBee
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 10; col++) {
                aliens.add(new RedBee(100 + col * 60, 100 + row * 40, WIDTH));
            }
        }

        addKeyListener(new KeyController());
        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 畫星空背景效果 (簡單的白色像素點)
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < 20; i++) {
            g.fillRect((i * 47) % WIDTH, (i * 73) % HEIGHT, 2, 2);
        }

        // 繪製物件
        if (!gameOver) player.draw(g);
        for (Bullet b : bullets) b.draw(g);
        for (Alien a : aliens) a.draw(g);

        // UI 顯示
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("SCORE: " + score, 20, 30);
        g.drawString("LIVES: " + lives, WIDTH - 130, 30);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", WIDTH / 2 - 140, HEIGHT / 2);
        } else if (aliens.isEmpty()) {
            g.setColor(Color.CYAN);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("STAGE CLEAR", WIDTH / 2 - 160, HEIGHT / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        // 1. 玩家移動
        if (movingLeft) player.moveLeft();
        if (movingRight) player.moveRight();

        // 2. 更新子彈位置
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.update();
            if (!b.isActive()) {
                bullets.remove(i);
                i--;
            }
        }

        // 3. 更新外星人位置與「隨機開火」機制
        for (Alien a : aliens) {
            a.update();
            // 每一影格有 0.3% 的機率讓外星人發射子彈
            if (rand.nextInt(1000) < 3) {
                bullets.add(new Bullet(a.getX() + a.width / 2, a.getY() + a.height, true));
            }
        }

        // 4. 碰撞偵測
        checkCollisions();

        repaint();
    }

    private void checkCollisions() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);

            if (!b.isEnemy()) {
                // 玩家子彈打中外星人
                for (int j = 0; j < aliens.size(); j++) {
                    Alien a = aliens.get(j);
                    if (b.intersects(a)) {
                        bullets.remove(i);
                        i--;
                        // 如果外星人血量扣完死亡
                        if (a.takeDamage()) {
                            score += a.getPoints(); // 依據多型取得各自的分數
                            aliens.remove(j);
                        }
                        break; 
                    }
                }
            } else {
                // 敵人子彈打中玩家
                if (b.intersects(player)) {
                    bullets.remove(i);
                    i--;
                    lives--;
                    if (lives <= 0) {
                        gameOver = true;
                        timer.stop();
                    }
                    break;
                }
            }
        }
    }

    private class KeyController extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) movingLeft = true;
        if (key == KeyEvent.VK_RIGHT) movingRight = true;
        
        // 修正這裡：確保按空白鍵時會執行
        if (key == KeyEvent.VK_UP) {
            // 檢查 bullets 是否正確呼叫 add 方法
            bullets.add(new Bullet(player.getX() + 18, player.getY(), false));
            // 💡 可以在這裡加一行測試行，按空白鍵時如果主控台有印出字，代表鍵盤觸發正常：
            // System.out.println("Bullet fired!"); 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) movingLeft = false;
        if (key == KeyEvent.VK_RIGHT) movingRight = false;
    }
    }
}
