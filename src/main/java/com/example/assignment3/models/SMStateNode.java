package com.example.assignment3.models;

import java.util.ArrayList;

public class SMStateNode extends SMItem {
    private Boolean isTransitionNode;

    private ArrayList<SMTransitionLink> transitionLinks;

    /**
     * Constructor for SMStateNode
     * @param newX initial x location
     * @param newY initial y location
     * @param newWidth width of shape
     * @param newHeight height of shape
     */
    public SMStateNode(double newX, double newY, double newWidth, double newHeight) {
        super(newX, newY, newWidth, newHeight);
        isTransitionNode = false;
        transitionLinks = new ArrayList<>();
    }

    public ArrayList<SMTransitionLink> getTransitionLinks() {
        return transitionLinks;
    }

    public void addLink(SMTransitionLink link){
        transitionLinks.add(link);

    }

    @Override
    public boolean getIsTransition() {
        return isTransitionNode;
    }

    @Override
    public boolean contains(double mouseX, double mouseY) {
        return mouseX >= x-(width/2) && mouseX <= x+(width/2) && mouseY >= y-(height/2) && mouseY <= y+(height/2);
    }

    @Override
    public void move(double normX, double normY) {
        x = normX;
        y = normY;
        initialX = x;
        initialY = y;
    }
}
