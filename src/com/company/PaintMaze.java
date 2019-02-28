package com.company;
import javax.swing.*;
import java.awt.*;
//Created by Shae Cloud 2/27/19

public class PaintMaze {
    // need to extend the JPanel functionality for my graphics
    public static class MPanel extends JPanel {

        Room rooms [][];
        public MPanel(Room[][] rooms) {
            this.rooms=rooms;
        }
        // This method is to draw the maze with solution
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // The following lines size the JPanel to scale and
            int height = this.getHeight()/rooms.length;
            int width = this.getWidth()/rooms[0].length;
            int cellSize = Math.min(height,width);
            // Search rooms[][] for character information be utilized by the paintComponent method
            for (int i = 0; i < rooms.length; i++)
                for (int j = 0; j < rooms[0].length; j++) {
                    if (rooms[i][j].character == '*')g.setColor(Color.white);             // walls are white
                    else if (rooms[i][j].character == ' ')g.setColor(Color.black);        // path is black
                    if(rooms[i][j].isPartOfPath)    g.setColor(Color.blue);               // solution is blue
                    if (i == 1 && j == 1) g.setColor(Color.cyan);                         // starting point is cyan
                    // Draw each part of the maze using the fillRect method with the proper parameters
                    g.fillRect(j * cellSize+(this.getWidth()-cellSize*rooms[0].length)/2,
                            i * cellSize+(this.getHeight()-cellSize*rooms.length)/2, cellSize, cellSize);
                }
        }
    }
}
