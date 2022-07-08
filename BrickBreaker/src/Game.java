package src;

import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Game extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int Bricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX = -1;
    private int ballDirY = -2;

    private Map layout;

    public Game() {
        layout = new Map(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // borders
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
        // drawing map
        layout.draw((Graphics2D) g);

        // The paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.CYAN);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        if (Bricks <= 0) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won: " + score, 260, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 230, 350);
        }
        if (ballPosY > 570) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        timer.start();
        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballDirY = -ballDirY;
            }

            A: for (int i = 0; i < layout.map.length; i++) {
                for (int j = 0; j < layout.map[0].length; j++) {
                    if (layout.map[i][j] > 0) {
                        int brickX = j * layout.brickWidth + 80;
                        int brickY = i * layout.brickHeight + 50;
                        int brickWidth = layout.brickWidth;
                        int brickHeight = layout.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            layout.setBrickValue(0, i, j);
                            Bricks--;
                            score += 5;
                        }
                        if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                            ballDirX = -ballDirX;
                        } else {
                            ballDirY = -ballDirY;
                        }
                        break A;
                    }
                }
            }

            ballPosX += ballDirX;
            ballPosY += ballDirY;
            if (ballPosX < 0) {
                ballDirX = -ballDirX;
            }
            if (ballPosY < 0) {
                ballDirY = -ballDirY;
            }
            if (ballPosX > 670) {
                ballDirX = -ballDirX;
            }

        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballDirX = -1;
                ballDirY = -2;
                playerX = 310;
                score = 0;
                Bricks = 21;
                layout = new Map(3, 7);

                repaint();
            }
        }

    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

}
