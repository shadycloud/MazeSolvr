package com.company;

import javax.swing.*;
import java.awt.*;
//Created by Shae Cloud 2/27/19

public class PaintMaze {
    // need to extend the JPanel functionality for my graphics
    public static class MPanel extends JPanel {
        /**
         * The 'rooms' array is a
         * 2D array of type room is used for
         * the subdivision of rows and columns
         */
        Room[][] rooms;

        public MPanel(Room[][] rooms) {
            this.rooms = rooms;
        }

        /**
         * This method is to draw a graphical
         * representation of a maze and it's
         * successful path
         *
         * @param g variable name for graphics class parameter
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // The following lines size the JPanel to scale and
            int height = this.getHeight() / rooms.length;
            int width = this.getWidth() / rooms[0].length;
            // This loop searches rooms[][] for ch information to be utilized
            // by the paintComponent method in order to paint the maze
            for (int i = 0; i < rooms.length; i++)
                for (int j = 0; j < rooms[0].length; j++) {
                    if (rooms[i][j].ch == '*') {
                        // Walls are gray
                        g.setColor(Color.gray);
                    } else if (rooms[i][j].ch == ' ') {
                        // Path is light gray
                        g.setColor(Color.lightGray);
                    }
                    if (rooms[i][j].isPartOfPath) {
                        // Solution is blue
                        g.setColor(Color.blue);
                    }
                    if (i == 1 && j == 1) {
                        // Starting point is cyan
                        g.setColor(Color.cyan);
                    }
                    if (i == 0 || j == 0 || i == rooms.length - 1
                            || j == rooms[0].length - 1) {
                        // Perimeter is green
                        g.setColor(Color.green);
                    }
                    if ((i == 0 || j == 0 || i == rooms.length - 1
                            || j == rooms[0].length - 1)
                            && rooms[i][j].isPartOfPath) {
                        // Exit is magenta
                        g.setColor(Color.magenta);
                    }
                    // This method is needed to paint all of the cells including
                    // the rooms, walls, starting/stopping points, and
                    // a successful path when available.
                    g.fillRect(j * width, i * height, width, height);
                }
        }
    }
}
