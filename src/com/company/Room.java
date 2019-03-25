package com.company;
//Created by Shae Cloud 2/27/19

// This is my room "factory"
public class Room {
    /**
     * Whether this room is visitable.
     */
    boolean isVisitable;
    /**
     * The x coordinate of this room
     */
    int x;
    /**
     * The y coordinate of this room
     */
    int y;
    /**
     * The variable to keep track of traveled path
     */
    boolean isPartOfPath;
    /**
     * The character variables used to represent rooms and walls
     */
    char ch;

    /**
     * Room constructor to create an instance of the 'Room' class
     *
     * @param x  location coordinate for each maze room
     * @param y  location coordinate for each maze room
     * @param ch variable to indicate weather a room is visitable
     */
    public Room(int x, int y, char ch) {
        this.y = y;
        this.x = x;
        // Set this room to be visitable if it is a blank space
        if (ch == ' ') {
            this.isVisitable = true;
        } else { // If the cell is a wall, not visitable is set
            this.isVisitable = false;
        }
        // Default: unvisited rooms have yet to become part of path
        this.isPartOfPath = false;
        this.ch = ch;
    }

    @Override
    public String toString() {
        return "" + this.ch;
    }
}



v
