package com.example.visualeditor.models;
public class ViewPort extends SMStateNode{
    /**
     * Constructor for ViewPort
     *
     * @param newX      initial x location
     * @param newY      initial y location
     * @param newWidth  width of view port
     * @param newHeight height of view port
     */
    public ViewPort(double newX, double newY, double newWidth, double newHeight) {
        super(newX, newY, newWidth, newHeight);
    }
    

    /**
     * Move the viewport based on main view panning
     * @param newX newest X location
     * @param newY newest Y location
     * @param prevX X location on mouse press
     * @param prevY Y location on mouse press
     */
    public void pan(double newX, double newY, double prevX, double prevY) {
        x = x + (prevX - newX);
        y = y + (prevY - newY);
        if (x <= 0) {
            x = 0;
        }
        if (x + width >= 1) {
            x = 1 - width;
        }
        if (y <= 0) {
            y = 0;
        }
        if (y + height >= 1) {
            y = 1 - height;
        }
        initialX = x;
        initialY = y;
    }
}
