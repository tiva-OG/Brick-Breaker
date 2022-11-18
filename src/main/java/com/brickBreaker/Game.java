package com.brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class Game extends JFrame implements ActionListener, KeyListener {

    Home home;
    GamePlay gamePlay;
    Object [] pauseOptions;
    Map<String, Object> state;

    Game () {
        pauseOptions = new Object[]{"Resume", "Restart", "Quit"};
        state = new HashMap<>();

        home = new Home();
        home.continueButton.addActionListener(this);
        home.newGameButton.addActionListener(this);
        home.highScoresButton.addActionListener(this);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        setTitle("Brick Breaker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 420);
        setBounds(10, 10, 700, 600);
        setResizable(false);
        add(home);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == home.continueButton) {

        } else if (e.getSource() == home.newGameButton) {
            gamePlay = new GamePlay();
            add(gamePlay);
            setVisible(true);
        } else if (e.getSource() == home.highScoresButton) {

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE && gamePlay.play) {
            int ballXDir = gamePlay.ballXDir;
            int ballYDir = gamePlay.ballYDir;

            gamePlay.play = false;
            gamePlay.ballXDir = 0;
            gamePlay.ballYDir = 0;

            // save current state of gamePlay
            state.clear();
            state.put("play", true);
            state.put("score", gamePlay.score);
            state.put("totalBricks", gamePlay.totalBricks);
            state.put("playerX", gamePlay.playerX);
            state.put("ballPosX", gamePlay.ballPosX);
            state.put("ballPosY", gamePlay.ballPosY);
            state.put("ballXDir", ballXDir);
            state.put("ballYDir", ballYDir);
            state.put("map", gamePlay.map);

            JLabel pauseLabel = new JLabel("PAUSE");
            gamePlay.add(pauseLabel);
            int result = JOptionPane.showOptionDialog(this, gamePlay, "Brick Breaker", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, pauseOptions, null);

            if (result == 0) {
                gamePlay = new GamePlay(state);
                add(gamePlay);
                setVisible(true);
            } else if (result == 1) {
                gamePlay = new GamePlay();
                add(gamePlay);
                setVisible(true);
            } else if (result == 2) {
                home.repaint();
                setVisible(true);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (gamePlay.playerX >= 575) {
                gamePlay.playerX = 575;
            } else {
                gamePlay.moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (gamePlay.playerX <= 10) {
                gamePlay.playerX = 10;
            } else {
                gamePlay.moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER && !gamePlay.play) {
            gamePlay.play = true;
            gamePlay.playerX = 285;
            gamePlay.ballPosX = gamePlay.playerX + 40;
            gamePlay.ballPosY = 500;
            gamePlay.ballXDir = -1;
            gamePlay.ballYDir = -2;
            gamePlay.score = 0;
            gamePlay.totalBricks = 48;

            gamePlay.map = new MapGenerator(4, 12);

            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
