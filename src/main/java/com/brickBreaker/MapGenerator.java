package com.brickBreaker;

import java.awt.*;

public class MapGenerator {

    public int[][] map;
    private final int brickWidth;
    private final int brickHeight;

    MapGenerator (int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    public void draw (Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * brickWidth + 60, i * brickHeight + 45, brickWidth, brickHeight);

                    // show separate bricks, game can still run w/o;
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.WHITE);
                    g.drawRect(j * brickWidth + 60, i * brickHeight + 45, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue (int value, int row, int col) {
        map[row][col] = value;
    }

    public int[][] getMap() {
        return map;
    }

    public int getBrickWidth() {
        return brickWidth;
    }

    public int getBrickHeight() {
        return brickHeight;
    }
}
