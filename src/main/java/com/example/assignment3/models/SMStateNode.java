package com.example.assignment3.models;

import java.util.ArrayList;
import java.util.Objects;

public class SMStateNode extends SMItem {
    private Boolean isTransitionNode;
    private String state;
    private ArrayList<SMTransitionLink> transitionLinks;

    /**
     * Constructor for SMStateNode
     * @param newX initial x location
     * @param newY initial y location
     * @param newWidth width of node
     * @param newHeight height of node
     */
    public SMStateNode(double newX, double newY, double newWidth, double newHeight) {
        super(newX, newY, newWidth, newHeight);
        isTransitionNode = false;
        transitionLinks = new ArrayList<>();
    }


    /**
     * @return All transition links attached to this state node
     */
    public ArrayList<SMTransitionLink> getTransitionLinks() {
        return transitionLinks;
    }

    /**
     * Add transition link to list of transition links attached to this state node
     * @param link transition link
     */
    public void addLink(SMTransitionLink link){
        transitionLinks.add(link);

    }

    /**
     * @return state property
     */
    public String getState() {
        if(!Objects.equals(state, "") && state != null){
            return state;
        }
        else{
            return "Default";
        }
    }



    public void setState(String newState) {
        state = newState;
    }

    @Override
    public boolean isTransition() {
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
