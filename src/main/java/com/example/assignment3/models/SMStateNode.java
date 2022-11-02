package com.example.assignment3.models;

public class SMStateNode {
    private String nodeText;
    public double x, y, width, height, initialX, initialY;
    private int z;

    /**
     * Constructor for XShape
     * @param newX initial x location
     * @param newY initial y location
     * @param newWidth width of shape
     * @param newHeight height of shape
     */
    public SMStateNode(double newX, double newY, double newWidth, double newHeight) {
        x = newX - newWidth / 2;
        y = newY - newHeight / 2;
        width = newWidth;
        height = newHeight;
        initialX = newX;
        initialY = newY;
        double handleX = x + width;
        double handleY = y + height;
    }


    /**
     * Determine if a point lies in the state node
     * @param mouseX x coordinate
     * @param mouseY y coordinate
     * @return true if point is in state node, false otherwise
     */
    public boolean contains(double mouseX, double mouseY){
        return mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY <= y+height;

    }

    /**
     * Set the text of the state node
     * @param text
     */
    public void setNodeText(String text) {
        nodeText = text;
    }

    /**
     * Get the text from the state node
     * @return colour of the shape
     */
    public String getNodeText() {
        return nodeText;
    }

    /**
     * Move the state node
     * @param normX new X location
     * @param normY new Y location
     */
    public void move(double normX, double normY) {
        x = normX-(width/2);
        y = normY-(height/2);
        initialX = x;
        initialY = y;
    }

    /**
     * Set the z value of the state node
     * @param newZ the z value
     */
    public void setZ(int newZ) {
        z = newZ;
    }

    /**
     * Get the z value of the state node
     * @return the z value
     */
    public int getZ() {
        return z;
    }
}
