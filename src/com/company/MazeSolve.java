package com.company;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
//Created by Shae Cloud 2/27/19

// This class file contains my stack and recursion
public class MazeSolve {

    static int moves = 0;

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(getFileFromUser());
        int rows = scanner.nextInt(); //get M rows from top of txt file
        int columns = scanner.nextInt(); //get N columns from top of txt file
        scanner.nextLine(); //discard rest of line 1 so it doesn't look wrong

        Room[][] rooms = new Room[rows][columns]; //create 2D array with null space for my 'room' objects
        for (int i = 0; i < rows; i++) {
            String s = scanner.nextLine();
            //This is a solution for printing out the maze to the console
            //System.out.println(s + " " + s.length());
            for (int j = 0; j < columns; j++) {
                char nextChar = s.charAt(j);
                rooms[i][j] = new Room(i, j, nextChar);
            }
        }
        // Explore method can't begin until a room such as 'room[1][1]' is pushed onto the stack.
        Stack<Room> path = new Stack<>();
        path.push(rooms[1][1]);
        path = explore(path, rooms);

        while (path != null) {
            try {
                path.pop().isPartOfPath = true;
            } catch (Exception e) {
                break;
            }
        }
        JFrame frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.pack();
        //These values set the initial size of the maze form.
        frame.setBounds(0, 0, (int) screenSize.getWidth()/3, (int) screenSize.getHeight()/3);
        //Exiting out of the GUI form will end the program.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PaintMaze.MPanel mp = new PaintMaze.MPanel(rooms);
        frame.add(mp);
        mp.setBounds(frame.getBounds());
        frame.setVisible(true);
        printPath(path, rooms);
    }//End main

    //Loosely coupled GUI for choosing the maze file (Commented out functionality for speed testing)
    private static File getFileFromUser() {
        // JFileChooser fc = new JFileChooser();
        // fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        // fc.showOpenDialog(null);
        return new File("/Users/cloud/IdeaProjects/Room/src/maze.txt");
        //return fc.getSelectedFile();
    }
    //Passing explore method the signature it needs to operate correctly.
    private static Stack<Room> explore(Stack<Room> path, Room[][] rooms) {
        int x = path.peek().x;
        int y = path.peek().y;
        //This limits the exploring method to rooms inside the extreme edges of the maze.
        if (x == 0 || y == 0 || x == rooms.length - 1 || y == rooms[0].length - 1) {
            System.out.println("Found exit at: (" + x + ", " + y + ")");
            System.out.println("<" + moves + path.toString() + ">");

            return path;
        }
        // The following five blocks of code navigate my maze by setting every room explored to not visitable.
        if (rooms[x][y + 1].isVisitable) {  // Go forward if visitable
            moves++;
            path.push(rooms[x][y + 1]);
            rooms[x][y + 1].isVisitable = false;
            return explore(path, rooms);
        }
        if (rooms[x][y - 1].isVisitable) {  // Go backward if visitable
            moves++;
            path.push(rooms[x][y - 1]);
            rooms[x][y - 1].isVisitable = false;
            return explore(path, rooms);
        }
        if (rooms[x + 1][y].isVisitable) {  // Go right if visitable
            moves++;
            path.push(rooms[x + 1][y]);
            rooms[x + 1][y].isVisitable = false;
            return explore(path, rooms);
        }
        if (rooms[x - 1][y].isVisitable) {  // Go left if visitable
            moves++;
            path.push(rooms[x - 1][y]);
            rooms[x - 1][y].isVisitable = false;
            return explore(path, rooms);
        } else {
            path.pop();
            return explore(path, rooms);
        }
    }

    public static void printPath(Stack<Room> path, Room[][] rooms) {
        for (Room room : path) {
            int x = room.x;
            int y = room.y;
            rooms[x][y].isPartOfPath = true;
        }
        //This for loop was used to leave a trail of bread crumbs illustrated by the '+' sign.
        for (int i = 0; i < rooms.length; ++i) {
            for (int j = 0; j < rooms[0].length; ++j) {
                if (rooms[i][j].isPartOfPath) {
                    System.out.print('+');
                } else {
                    System.out.print(rooms[i][j].character);
                }
            }
            System.out.println();
                }
            }
        }





