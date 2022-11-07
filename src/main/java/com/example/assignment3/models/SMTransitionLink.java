package com.example.assignment3.models;

public class SMTransitionLink {
    private String nodeText;
    public double x, y, x2, y2, initialX, initialY;
    private SMStateNode startNode;
    private SMStateNode endNode;
    private int z;

    /**
     * Constructor for XLine
     * @param newX1 starting x coordinate
     * @param newY1 starting y coordinate
     * @param newX2 ending x coordinate
     * @param newY2 ending y coordinate
     */
    public SMTransitionLink(double newX1, double newY1, double newX2, double newY2, SMStateNode start, SMStateNode end) {
        x = newX1;
        y = newY1;
        x2 = newX2;
        y2 = newY2;
        initialX = newX1;
        initialY = newY1;
        startNode = start;
        endNode = end;

    }


    /**
     * Resize the line with new ending coordinates
     * @param newX new x ending location
     * @param newY new y ending location
     */

    public void resizeEnd(double newX, double newY) {
        x2 = newX;
        y2 = newY;
    }

    /**
     * Resize the line with new starting coordinates
     * @param newX new x ending location
     * @param newY new y ending location
     */

    public void resizeStart(double newX, double newY) {
        x = newX;
        y = newY;
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

    /**
     * Get the start node of transition link
     * @return the start node
     */
    public SMStateNode getStartNode() {
        return startNode;
    }

    /**
     * Get the end node of transition link
     * @return the end node
     */
    public SMStateNode getEndNode() {
        return endNode;
    }

    /**
     * Set the end node of transition link
     */
    public void setEndNode(SMStateNode endNode) {
        endNode.addTransitionLink(this);
        this.endNode = endNode;

    }

    /**
     * Set the new start x value of the transition link
     * @param newX  the new end x value
     */
    public void setX(double newX) {
        x = newX;
    }

    /**
     * Set the new start y value of the transition link
     * @param newY  the new start y value
     */
    public void setY(double newY) {
        y = newY;
    }

    /**
     * Set the new end x value of the transition link
     * @param newX2  the new end x value
     */
    public void setX2(double newX2) {
        x2 = newX2;
    }

    /**
     * Set the new end y value of the transition link
     * @param newY2  the new end y value
     */
    public void setY2(double newY2) {
        y2 = newY2;
    }
}
