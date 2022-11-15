package com.example.assignment3.models;

public abstract class SMItem {
    public double x, y, width, height, initialX, initialY;
    private String itemId;
    private int z;

    public SMItem(double newX, double newY, double newWidth, double newHeight){
        x = newX;
        y = newY;
        width = newWidth;
        height = newHeight;
        initialX = newX;
        initialY = newY;
    }

    public abstract boolean isTransition();

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

}
