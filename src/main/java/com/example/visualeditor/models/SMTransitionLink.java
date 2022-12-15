package com.example.visualeditor.models;

import java.util.Objects;

public class SMTransitionLink extends SMItem{
    private SMStateNode startNode, endNode;
    private Boolean isFinal;
    private Boolean isTransitionNode;
    private String event, context, sideEffects;
    public double transitionNodeX, transitionNodeY;

    /**
     * Constructor for XLine
     * @param newX1 starting x coordinate
     * @param newY1 starting y coordinate
     * @param newX2 ending x coordinate
     * @param newY2 ending y coordinate
     */
    public SMTransitionLink(double newX1, double newY1, double newX2, double newY2) {
        super(newX1, newY1, newX2, newY2);
        isFinal = false;
        isTransitionNode = true;
        transitionNodeX = 0.0;
        transitionNodeY = 0.0;


    }

    /**
     * Sets flag for whether a transition link is final
     * @param aFinal flag for whether a transition link is final
     */
    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }

    /**
     * @return flag for whether a transition link is final
     */
    public Boolean getFinal() {
        return isFinal;
    }


    /**
     * Resize the line with new ending coordinates
     * @param newX new x ending location
     * @param newY new y ending location
     */

    public void resizeEnd(double newX, double newY) {
        width = newX;
        height = newY;
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
     * Get the start node of transition link
     * @return the start node
     */
    public SMStateNode getStartNode() {
        return startNode;
    }

    /**
     * Set the start node of transition link
     */
    public void setStartNode(SMStateNode startNode) {
        this.startNode = startNode;

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
        this.endNode = endNode;
    }


    /**
     * @return flag for whether this is a transition link
     */
    @Override
    public boolean isTransition(){
        return this.isTransitionNode;
    }


    /**
     * Moves the transition link
     * @param normX x coordinate
     * @param normY y coordinate
     */
    @Override
    public void move(double normX, double normY) {
        transitionNodeX = normX;
        transitionNodeY = normY;
        initialX = x;
        initialY = y;
    }

    /**
     * Determine if a point lies in transition link
     * @param mouseX x coordinate
     * @param mouseY y coordinate
     * @return true if point is in transition link, false otherwise
     */
    @Override
    public boolean contains(double mouseX, double mouseY) {
        return mouseX >= transitionNodeX-(0.05) && mouseX <= transitionNodeX+(0.05) && mouseY >= transitionNodeY-(0.05) && mouseY <= transitionNodeY+(0.05);
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
        width = newX2;
    }

    /**
     * Set the new end y value of the transition link
     * @param newY2  the new end y value
     */
    public void setY2(double newY2) {
        height = newY2;
    }

    /**
     * @return Eontext property
     */
    public String getContext() {
        if(!Objects.equals(context, "") && context != null){
            return context;
        }
        else{
            return "No Context";
        }
    }

    /**
     * @return Event property
     */
    public String getEvent() {
        if(!Objects.equals(event, "") && event != null){
            return event;
        }
        else{
            return "No Event";
        }
    }

    /**
     * @return Side effects property
     */
    public String getSideEffects() {
        if(!Objects.equals(sideEffects, "") && sideEffects != null){
            return sideEffects;
        }
        else{
            return "No Side Effects";
        }
    }

    /**
     * Sets event property
     * @param newEvent new event property
     */
    public void setEvent(String newEvent) {
        event = newEvent;
    }

    /**
     * Sets context property
     * @param newContext new context property
     */
    public void setContext(String newContext) {
        context = newContext;
    }

    /**
     * Sets side effects property
     * @param newSideEffects new side effects property
     */
    public void setSideEffects(String newSideEffects) {
        sideEffects = newSideEffects;
    }
}
