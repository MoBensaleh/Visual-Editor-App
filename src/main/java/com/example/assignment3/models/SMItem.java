package com.example.assignment3.models;

import java.util.ArrayList;

public abstract class SMItem {
    public double x, y, width, height, initialX, initialY;
    private String itemText;
    private String itemId;
    private int z;

    public SMItem(double newX, double newY, double newWidth, double newHeight){
        x = newX;
        y = newY;
        width = newWidth;
        height = newHeight;
        initialX = newX;
        initialY = newY;
//        double handleX = x + width;
//        double handleY = y + height;
    }

    public abstract boolean getIsTransition();

    /**
     * Determine if a point lies in the item
     * @param mouseX x coordinate
     * @param mouseY y coordinate
     * @return true if point is in item, false otherwise
     */
    public abstract boolean contains(double mouseX, double mouseY);



    /**
     * Move the item
     * @param normX new X location
     * @param normY new Y location
     */
    public abstract void move(double normX, double normY);


    /**
     * Set the id of the item
     * @param smId of item
     */
    public void setItemId(String smId) {
        itemId = smId;
    }

    /**
     * Set the text of the item
     * @param text of item
     */
    public void setItemText(String text) {
        itemText = text;
    }

    /**
     * Get the text from the item
     * @return text of the item
     */
    public String getItemText() {
        return itemText;
    }


    /**
     * Set the z value of the item
     * @param newZ the z value
     */
    public void setZ(int newZ) {
        z = newZ;
    }

    /**
     * Get the z value of the item
     * @return the z value
     */
    public int getZ() {
        return z;
    }

    /**
     * Get the x value of the item
     * @return the x value
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y value of the item
     * @return the y value
     */
    public double getY() {
        return y;
    }

    /**
     * Get the width of the item
     * @return the width value
     */
    public double getWidth() {
        return width;
    }

    /**
     * Get the height of the item
     * @return the height value
     */
    public double getHeight() {
        return height;
    }
}
