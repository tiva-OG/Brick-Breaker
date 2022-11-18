package com.brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GamePlay extends JPanel implements ActionListener, KeyListener {

    protected boolean play;
    protected int score;
    protected int totalBricks;

    protected int playerX;
    protected int ballPosX ;
    protected int ballPosY;
    protected int ballXDir;
    protected int ballYDir;

    private final int brickWidth;
    private final int brickHeight;

    private final Timer timer;
    protected MapGenerator map;

    protected Map<String, Object> state = new HashMap<>();
    protected final String savePath = "./state.txt";

    GamePlay () {
        this.play = false;
        this.score = 0;
        this.totalBricks = 48;

        this.playerX = 285;
        this.ballPosX = playerX + 40;
        this.ballPosY = 500;
        this.ballXDir = -1;
        this.ballYDir = -2;

        map = new MapGenerator(4, 12);
        brickWidth = map.getBrickWidth();
        brickHeight = map.getBrickHeight();

        timer = new Timer(5, this);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer.start();
    }

    GamePlay (Map<String, Object> state) {
        this.play = (boolean) state.get("play");
        this.score = (int) state.get("score");
        this.totalBricks = (int) state.get("totalBricks");

        this.playerX = (int) state.get("playerX");
        this.ballPosX = (int) state.get("ballPosX");
        this.ballPosY = (int) state.get("ballPosY");
        this.ballXDir = (int) state.get("ballXDir");
        this.ballYDir = (int) state.get("ballYDir");

        this.map = (MapGenerator) state.get("map");
        brickWidth = map.getBrickWidth();
        brickHeight = map.getBrickHeight();

        timer = new Timer(5, this);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer.start();
    }

    public void paint (Graphics g) {

        // background;
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        // borders;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 10, 590); // left;
        g.fillRect(0, 0, 690, 10); // top;
        g.fillRect(675, 0, 10, 590); // right;

        // scores;
        g.setColor(Color.GRAY);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.drawString("" + score, 340, 30);

         // paddle;
        g.setColor(Color.MAGENTA);
        g.fillRect(playerX, 550, 100, 10);

        // ball
        g.setColor(Color.WHITE);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        // WIN!
        if (totalBricks <= 0) {
            win();

            g.setColor(Color.orange);
            g.setFont(new Font("Serif", Font.BOLD, 40));
            g.drawString("YOU WIN!", 190, 270);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press ENTER to Restart", 210, 350);
        }

        //lose :(
        if (ballPosY > 565) {
//            lose();

            g.setColor(Color.RED);
            g.setFont(new Font("Serif", Font.BOLD, 40));
            g.drawString("GAME OVER!", 190, 270);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Score: " + score, 205, 310);
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press ENTER to Restart", 210, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();

        if (play) {

            // wall||paddle collision with ball;
            Rectangle ball = new Rectangle(ballPosX, ballPosY, 20, 20);

            if (ball.intersects(new Rectangle(playerX, 550, 30, 10))) {
                ballXDir = -2;
                ballYDir = -ballYDir;
            } else if (ball.intersects(new Rectangle(playerX + 70, 550, 30, 10))) {
                ballXDir = ballXDir + 1;
                ballYDir = -ballYDir;
            } else if (ball.intersects(new Rectangle(playerX + 30, 550, 40, 10))) {
                ballYDir = -ballYDir;
            }
        }

        // map collision with ball;
        A:
        for (int i = 0; i < map.map.length; i++) {

            for (int j = 0; j < map.map[0].length; j++) {

                if (map.map[i][j] > 0) {

                    // scores++;
                    int brickX = j * brickWidth + 60;
                    int brickY = i * brickHeight + 45;

                    Rectangle brick = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ball = new Rectangle(ballPosX, ballPosY, 20, 20);

                    if (ball.intersects(brick)) {
                        map.setBrickValue(0, i, j);
                        score += 5;
                        totalBricks--;

                        // ball hit side of brick;

                        if (ballPosX == brick.x || ballPosX >= (brick.x + brick.width)) {
                            ballXDir = -ballXDir;
                        } else { // ball hit top||bottom of brick;
                            ballYDir = -ballYDir;
                        }

                        break A;
                    }
                }

            }

        }

        ballPosX += ballXDir;
        ballPosY += ballYDir;

        if (ballPosX < 10) {
            ballXDir = -ballXDir;
        }
        if (ballPosY < 10) {
            ballYDir = -ballYDir;
        }
        if (ballPosX > 650) {
            ballXDir = -ballXDir;
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 575) {
                playerX = 575;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER && !play) {
            play = true;
            playerX = 285;
            ballPosX = playerX + 40;
            ballPosY = 500;
            ballXDir = -1;
            ballYDir = -2;
            score = 0;
            totalBricks = 48;

            map = new MapGenerator(4, 12);

            repaint();
        }
    }

    void moveRight() {
        play = true;
        playerX += 20;
    }

    void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void win () {
        play = false;
        ballXDir = 0;
        ballYDir = 0;

        Object[] winOptions = new Object[]{"Restart", "Home"};
        JLabel pauseLabel = new JLabel("WIN");
        add(pauseLabel);
        JOptionPane.showOptionDialog(null, this, "Brick Breaker", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, winOptions, null);
    }

    public void lose () {

        play = false;
        ballXDir = 0;
        ballYDir = 0;

        Object[] loseOptions = new Object[]{"Restart", "Home"};
        JLabel pauseLabel = new JLabel("GAME OVER");
        add(pauseLabel);
        JOptionPane.showOptionDialog(null, this, "Brick Breaker", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, loseOptions, null);
    }

    public void saveGame () {
        state.clear();
        state.put("play", true);
        state.put("score", score);
        state.put("totalBricks", totalBricks);
        state.put("playerX", playerX);
        state.put("ballPosX", ballPosX);
        state.put("ballPosY", ballPosY);
        state.put("ballXDir", ballXDir);
        state.put("ballYDir", ballYDir);
        state.put("map", map);

        File file = new File(savePath);
        BufferedWriter gameState = null;

        try {
            gameState = new BufferedWriter(new FileWriter(file));

            for (Map.Entry<String, Object> entry: state.entrySet()) {
                gameState.write(entry.getKey() + ":" + entry.getValue());
                gameState.newLine();
            }
            gameState.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert gameState != null;
                gameState.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    public void loadGame () {

    }
}
