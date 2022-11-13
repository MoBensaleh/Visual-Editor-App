package com.example.assignment3.models;

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

    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }

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

    @Override
    public boolean isTransition(){
        return this.isTransitionNode;
    }

    @Override
    public void move(double normX, double normY) {
        transitionNodeX = normX;
        transitionNodeY = normY;
        initialX = x;
        initialY = y;
    }

    @Override
    public boolean contains(double mouseX, double mouseY) {
        return mouseX >= transitionNodeX-(60) && mouseX <= transitionNodeX+(60) && mouseY >= transitionNodeY-(60) && mouseY <= transitionNodeY+(60);
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

    public String getContext() {
        return context;
    }

    public String getEvent() {
        return event;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setEvent(String newEvent) {
        event = newEvent;
    }

    public void setContext(String newContext) {
        context = newContext;
    }

    public void setSideEffects(String newSideEffects) {
        sideEffects = newSideEffects;
    }
}
