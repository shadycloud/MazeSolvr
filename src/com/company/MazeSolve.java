package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;
//Created by Shae Cloud 2/27/19

/**
 * The MazeSolve class is designed to solve specifically formatted mazes.
 * A maze must be a txt file with the number of rows and the number of columns
 * being on the first line of the file. Subsequent lines will contain the rows
 * of the maze. The '*' character will represent a wall, a space character will
 * represent the absence of a wall.
 */
public class MazeSolve {
    /**
     * The 'moves' variable is used to count the number of moves
     * taken by explore()
     */
    static int moves = 0;

    public static void main(String[] args) {
        boolean mazeHasNotBeenSolved = true;

        while (mazeHasNotBeenSolved) {
            try {
                solveMaze();
                mazeHasNotBeenSolved = false;
            } catch (EmptyStackException e1) {
                System.out.println("Maze seems unsolvable!");
                System.exit(1);
            } catch (IOException e2) {
                System.out.println("IO exception! Try another file. \n" + e2);
                System.exit(1);
            } catch (Exception e3) {
                System.out.println("Unexpected exception... " + e3);
                System.out.println("Cannot recover, exiting...");
                System.exit(1);
            }
        }

    } //End main

    /**
     * The 'solveMaze()' method gets information needed to create a
     * multidimensional array by scanning the first line of the txt file for
     * integers that define the number of rows and columns of the maze.
     *
     * @throws EmptyStackException intended to identify unsolvable mazes
     * @throws IOException         indicates file chooser cancellation, or bad file
     */
    private static void solveMaze() throws EmptyStackException, IOException {
        Scanner scanner = new Scanner(getFileFromUser());
        //get M rows from top of txt file
        int rows = scanner.nextInt();
        //get N columns from top of txt file
        int columns = scanner.nextInt();
        //discard rest of line 1 so it doesn't look wrong
        scanner.nextLine();
        //create 2D array with null space for my 'room' objects
        Room[][] rooms = new Room[rows][columns];
        for (int i = 0; i < rows; i++) {
            String s = scanner.nextLine();
            //This is a solution for printing out the maze to the console
            //System.out.println(s + " " + s.length());
            for (int j = 0; j < columns; j++) {
                char nextChar = s.charAt(j);
                rooms[i][j] = new Room(i, j, nextChar);
            }
        }

        // Explore method can't begin until a
        // room such as 'room[1][1]' is pushed onto the stack
        Stack<Room> path = new Stack<>();
        path.push(rooms[1][1]);
        path = explore(path, rooms);
        JFrame frame = new JFrame();
        // This block proportions the window to fit well with the maze
        Dimension frameSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Get width returns a double so it needs to be casted as an integer
        int cellWidth = ((int) (frameSize.getWidth())) / rooms[0].length;
        int cellHeight = ((int) (frameSize.getHeight())) / rooms.length;
        // Set the initial size of the maze form with minimum values.\
        cellHeight = cellWidth = Math.min(cellHeight, cellWidth);
        frame.getContentPane().setPreferredSize(new Dimension(
                cellWidth * rooms[0].length,
                cellHeight * rooms.length));
        frame.pack();
        // Exiting out of the GUI form will end the program
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PaintMaze.MPanel mp = new PaintMaze.MPanel(rooms);
        frame.add(mp);
        mp.setBounds(frame.getBounds());
        frame.setVisible(true);
        printPath(path, rooms);
    }

    /**
     * The 'getFileFromUser' method uses JFileChooser to select maze files
     *
     * @return fc.getSelectedFile() returns selected file
     * @throws IOException
     */
    private static File getFileFromUser() throws IOException {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fc.showOpenDialog(null);
        if (fc.getSelectedFile() != null) {
            return fc.getSelectedFile();
        } else {
            // End execution of the method
            throw new IOException("No file selected.");
        }
    }

    /**
     * The 'explore()' method navigates the 2D array of rooms by moving in
     * N, S, E, and W directions. If possible, the method is called recursively
     * with each move until a successful solution is found
     */
    private static Stack<Room> explore(Stack<Room> path, Room[][] rooms)
            throws EmptyStackException {
        //printPath(path, rooms);
        int x = path.peek().x;
        int y = path.peek().y;
        // Limit exploring method to rooms inside the extreme edges of the maze
        if (x == 0 || y == 0 || x == rooms.length - 1
                || y == rooms[0].length - 1) {
            return path;
        }
        // The following five blocks of code navigate a maze by setting every
        // room explored to not visitable
        if (rooms[x + 1][y].isVisitable) {  // Go East if visitable
            moves++;
            path.push(rooms[x + 1][y]);
            rooms[x + 1][y].isVisitable = false;
            return explore(path, rooms);
        }
        if (rooms[x][y + 1].isVisitable) {  // Go South if visitable
            moves++;
            path.push(rooms[x][y + 1]);
            rooms[x][y + 1].isVisitable = false;
            return explore(path, rooms);
        }
        if (rooms[x][y - 1].isVisitable) {  // Go North if visitable
            moves++;
            path.push(rooms[x][y - 1]);
            rooms[x][y - 1].isVisitable = false;
            return explore(path, rooms);
        }
        if (rooms[x - 1][y].isVisitable) {  // Go West if visitable
            moves++;
            path.push(rooms[x - 1][y]);
            rooms[x - 1][y].isVisitable = false;
            return explore(path, rooms);
        } else {
            // Take the last room visited off of the stack and recall explore()
            path.pop();
            return explore(path, rooms);
        }
    }

    /**
     * The 'printPath' method is used to print a representation of a txt maze
     * file with a successful path being denoted by a '+' sign to the console.
     *
     * @param path  Stack name used to define a successful path for a maze
     * @param rooms 2D array of type Room
     */
    public static void printPath(Stack<Room> path, Room[][] rooms) {
        for (Room room : path) {
            int x = room.x;
            int y = room.y;
            rooms[x][y].isPartOfPath = true;
        }
        for (int i = 0; i < rooms.length; ++i) {
            for (int j = 0; j < rooms[0].length; ++j) {
                if (rooms[i][j].isPartOfPath) {
                    // The '+' sign for visited rooms on a successful path
                    System.out.print('+');
                } else { // Print all other rooms and walls of the maze
                    System.out.print(rooms[i][j].ch);
                }
            }
            System.out.println();
        }
    }
}





