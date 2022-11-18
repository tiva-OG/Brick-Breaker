package com.brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JPanel{
    JFrame frame;

    JButton continueButton;
    JButton newGameButton;
    JButton highScoresButton;

    GamePlay gamePlay;

    Home () {
//        frame = new JFrame("Brick Breaker");

        continueButton = new JButton("Continue");
        newGameButton = new JButton("New Game");
        highScoresButton = new JButton("High Scores");

        continueButton.setBounds(200, 100, 300, 70);
        continueButton.setFocusable(false);
//        continueButton.addActionListener(this);
        continueButton.setFont(new Font("Comic Sans", Font.BOLD, 25));
        continueButton.setBackground(Color.DARK_GRAY);
        continueButton.setForeground(Color.WHITE);
        continueButton.setBorder(BorderFactory.createEtchedBorder());


        newGameButton.setBounds(200, 200, 300, 70);
        newGameButton.setFocusable(false);
//        newGameButton.addActionListener(this);
        newGameButton.setFont(new Font("Comic Sans", Font.BOLD, 25));
        newGameButton.setBackground(Color.DARK_GRAY);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setBorder(BorderFactory.createEtchedBorder());

        highScoresButton.setBounds(200, 300, 300, 70);
        highScoresButton.setFocusable(false);
//        highScoresButton.addActionListener(this);
        highScoresButton.setFont(new Font("Comic Sans", Font.BOLD, 25));
        highScoresButton.setBackground(Color.DARK_GRAY);
        highScoresButton.setForeground(Color.WHITE);
        highScoresButton.setBorder(BorderFactory.createEtchedBorder());

        setLayout(null);
        setBackground(Color.BLUE);
        add(continueButton);
        add(newGameButton);
        add(highScoresButton);

//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setBounds(10, 10, 700, 600);
//        frame.setResizable(false);
//        frame.add(this);
//        frame.setVisible(true);
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//        if (e.getSource() == continueButton) {
//
//        } else if (e.getSource() == newGameButton) {
//            gamePlay = new GamePlay();
//            frame.add(gamePlay);
//        } else if (e.getSource() == highScoresButton) {
//
//        }
//    }
}
