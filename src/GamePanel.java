import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {

    // =====================================================
    // 遊戲畫面大小
    // =====================================================

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    // =====================================================
    // 遊戲物件
    // =====================================================

    private Player player;

    private List<Enemy> enemies;

    private List<Bullet> bullets;

    // =====================================================
    // 遊戲狀態
    // =====================================================

    private boolean gameOver;

    private int score;

    // =====================================================
    // 遊戲 Timer
    // =====================================================

    private Timer timer;

    // =====================================================
    // 鍵盤狀態
    // =====================================================

    private boolean leftPressed = false;

    private boolean rightPressed = false;

    private boolean spacePressed = false;

    // =====================================================
    // 建構子
    // =====================================================

    public GamePanel() {

        setPreferredSize(
                new Dimension(
                        SCREEN_WIDTH,
                        SCREEN_HEIGHT
                )
        );

        setBackground(Color.BLACK);

        setFocusable(true);

        // 初始化遊戲
        initGame();

        // 設定鍵盤
        setupKeyboard();

        // 啟動遊戲
        startGame();
    }

    // =====================================================
    // 初始化遊戲
    // =====================================================

    private void initGame() {

        gameOver = false;

        score = 0;

        leftPressed = false;

        rightPressed = false;

        spacePressed = false;

        bullets = new ArrayList<>();

        enemies = new ArrayList<>();

        // =================================================
        // 建立玩家
        // =================================================

        int playerWidth = 50;

        int playerHeight = 40;

        int playerX =
                SCREEN_WIDTH / 2
                - playerWidth / 2;

        int playerY =
                SCREEN_HEIGHT - 70;

        player = new Player(
                playerX,
                playerY,
                playerWidth,
                playerHeight
        );

        // =================================================
        // 建立敵人
        // =================================================

        int enemyWidth = 40;

        int enemyHeight = 30;

        int rows = 3;

        int columns = 8;

        int startX = 100;

        int startY = 60;

        int gapX = 70;

        int gapY = 50;

        for (int row = 0; row < rows; row++) {

            for (int column = 0;
                 column < columns;
                 column++) {

                int enemyX =
                        startX
                        + column * gapX;

                int enemyY =
                        startY
                        + row * gapY;

                enemies.add(
                        new Enemy(
                                enemyX,
                                enemyY,
                                enemyWidth,
                                enemyHeight
                        )
                );
            }
        }
    }

    // =====================================================
    // 鍵盤設定
    // =====================================================

    private void setupKeyboard() {

        KeyboardFocusManager manager =
                KeyboardFocusManager
                        .getCurrentKeyboardFocusManager();

        manager.addKeyEventDispatcher(
                event -> {

                    int key =
                            event.getKeyCode();

                    // =================================================
                    // KEY PRESSED
                    // =================================================

                    if (event.getID()
                            == KeyEvent.KEY_PRESSED) {

                        // ---------------------------------------------
                        // 左
                        // ---------------------------------------------

                        if (key == KeyEvent.VK_LEFT) {

                            leftPressed = true;

                            player.setMovingLeft(true);

                            System.out.println(
                                    "LEFT"
                            );
                        }

                        // ---------------------------------------------
                        // 右
                        // ---------------------------------------------

                        if (key == KeyEvent.VK_RIGHT) {

                            rightPressed = true;

                            player.setMovingRight(true);

                            System.out.println(
                                    "RIGHT"
                            );
                        }

                        // ---------------------------------------------
                        // SPACE
                        // ---------------------------------------------

                        if (key == KeyEvent.VK_UP) {

                            System.out.println(
                                    "UP KEY DETECTED"
                            );

                                shoot();
                            }

                        // ---------------------------------------------
                        // R
                        // ---------------------------------------------

                        if (key == KeyEvent.VK_R) {

                            if (gameOver) {

                                System.out.println(
                                        "RESTART"
                                );

                                initGame();
                            }
                        }
                    }

                    // =================================================
                    // KEY RELEASED
                    // =================================================

                    if (event.getID()
                            == KeyEvent.KEY_RELEASED) {

                        // ---------------------------------------------
                        // 左
                        // ---------------------------------------------

                        if (key == KeyEvent.VK_LEFT) {

                            leftPressed = false;

                            player.setMovingLeft(false);
                        }

                        // ---------------------------------------------
                        // 右
                        // ---------------------------------------------

                        if (key == KeyEvent.VK_RIGHT) {

                            rightPressed = false;

                            player.setMovingRight(false);
                        }

                        // ---------------------------------------------
                        // SPACE
                        // ---------------------------------------------

                        
                    }

                    // =================================================
                    // 不攔截鍵盤事件
                    // =================================================

                    return false;
                }
        );
    }

    // =====================================================
    // 啟動遊戲
    // =====================================================

    private void startGame() {

        timer = new Timer(
                16,
                e -> gameLoop()
        );

        timer.start();
    }

    // =====================================================
    // 遊戲主迴圈
    // =====================================================

    private void gameLoop() {

        if (!gameOver) {

            updateGame();

            checkCollisions();

            checkGameOver();
        }

        repaint();
    }

    // =====================================================
    // 更新遊戲
    // =====================================================

    private void updateGame() {

        // =================================================
        // 玩家
        // =================================================

        player.update(
                SCREEN_WIDTH
        );

        // =================================================
        // 敵人
        // =================================================

        for (Enemy enemy : enemies) {

            enemy.update(
                    SCREEN_WIDTH
            );
        }

        // =================================================
        // 子彈
        // =================================================

        Iterator<Bullet> bulletIterator =
                bullets.iterator();

        while (bulletIterator.hasNext()) {

            Bullet bullet =
                    bulletIterator.next();

            bullet.update();

            // 子彈飛出畫面
            if (bullet.getY()
                    + bullet.getHeight()
                    < 0) {

                bulletIterator.remove();
            }
        }
    }

    // =====================================================
    // 碰撞偵測
    // =====================================================

    private void checkCollisions() {

        Iterator<Bullet> bulletIterator =
                bullets.iterator();

        while (bulletIterator.hasNext()) {

            Bullet bullet =
                    bulletIterator.next();

            Iterator<Enemy> enemyIterator =
                    enemies.iterator();

            while (enemyIterator.hasNext()) {

                Enemy enemy =
                        enemyIterator.next();

                if (bullet.getBounds()
                        .intersects(
                                enemy.getBounds()
                        )) {

                    // 移除敵人
                    enemyIterator.remove();

                    // 移除子彈
                    bulletIterator.remove();

                    // 加分
                    score += 100;

                    System.out.println(
                            "ENEMY HIT!"
                    );

                    System.out.println(
                            "SCORE = "
                            + score
                    );

                    break;
                }
            }
        }
    }

    // =====================================================
    // Game Over 判斷
    // =====================================================

    private void checkGameOver() {

        // =================================================
        // 所有敵人死亡
        // =================================================

        if (enemies.isEmpty()) {

            gameOver = true;

            return;
        }

        // =================================================
        // 敵人碰到玩家
        // =================================================

        for (Enemy enemy : enemies) {

            if (enemy.getBounds()
                    .intersects(
                            player.getBounds()
                    )) {

                gameOver = true;

                return;
            }

            // =================================================
            // 敵人下降到玩家附近
            // =================================================

            if (enemy.getY()
                    + enemy.getHeight()
                    >= SCREEN_HEIGHT - 50) {

                gameOver = true;

                return;
            }
        }
    }

    // =====================================================
    // 射擊
    // =====================================================

    private void shoot() {

        // Game Over 不射擊
        if (gameOver) {

            System.out.println(
                    "Cannot shoot - GAME OVER"
            );

            return;
        }

        // =================================================
        // 建立子彈
        // =================================================

        int bulletX =
                player.getX()
                + player.getWidth() / 2
                - 3;

        int bulletY =
                player.getY()
                - 15;

        Bullet bullet =
                new Bullet(
                        bulletX,
                        bulletY
                );

        bullets.add(bullet);

        // =================================================
        // Debug
        // =================================================

        System.out.println(
                "================================"
        );

        System.out.println(
                "BULLET CREATED"
        );

        System.out.println(
                "Bullet X = "
                + bulletX
        );

        System.out.println(
                "Bullet Y = "
                + bulletY
        );

        System.out.println(
                "Bullet Count = "
                + bullets.size()
        );

        System.out.println(
                "================================"
        );
    }

    // =====================================================
    // 繪製遊戲
    // =====================================================

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // =================================================
        // 玩家
        // =================================================

        if (player != null) {

            player.draw(g);
        }

        // =================================================
        // 敵人
        // =================================================

        if (enemies != null) {

            for (Enemy enemy : enemies) {

                enemy.draw(g);
            }
        }

        // =================================================
        // 子彈
        // =================================================

        if (bullets != null) {

            for (Bullet bullet : bullets) {

                bullet.draw(g);
            }
        }

        // =================================================
        // 分數
        // =================================================

        g.setColor(Color.WHITE);

        g.drawString(
                "Score: " + score,
                20,
                25
        );

        // =================================================
        // Game Over / Win
        // =================================================

        if (gameOver) {

            String message;

            if (enemies.isEmpty()) {

                message = "YOU WIN!";

            } else {

                message = "GAME OVER";
            }

            g.setColor(Color.WHITE);

            g.drawString(
                    message,
                    SCREEN_WIDTH / 2 - 40,
                    SCREEN_HEIGHT / 2
            );

            g.drawString(
                    "Press R to restart",
                    SCREEN_WIDTH / 2 - 65,
                    SCREEN_HEIGHT / 2 + 30
            );
        }
    }
}