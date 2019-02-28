package com.company;
//Created by Shae Cloud 2/27/19

// This is my room "factory"
public class Room {
    boolean isVisitable;
    int x;
    int y;
    boolean isPartOfPath;
    char character;

    public Room(int x, int y, char ch) { // Constructor to create an instance of the 'Room' class
        this.y = y;
        this.x = x;
        this.isVisitable = ch == ' '; // This defines a room being visitable and sets it equal to 'ch' below
        this.isPartOfPath = false; // This defines a room being part of successful path
        this.character = ch;
    }
    // Prints successful path and shows how each room is set to not visitable after the explore method is called
    public String toString() {
        return "(" + x + ", " + y + " " + isVisitable + ")";

    }
}



