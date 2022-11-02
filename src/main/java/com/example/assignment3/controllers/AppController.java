package com.example.assignment3.controllers;


import com.example.assignment3.models.InteractionModel;
import com.example.assignment3.models.SMModel;
import com.example.assignment3.models.SMStateNode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * The controller to handle events from the view classes.
 */
public class AppController {
    protected SMModel model;
    protected InteractionModel iModel;
    protected double prevX, prevY;

    protected enum State {
        READY, PREPARE_CREATE, SELECTED, MOVING
    }

    protected State currentState;

    /**
     * Constructor for AppController
     */
    public AppController() {
        currentState = State.READY;
    }

    /**
     * Associate a model to the controller
     * @param newModel the drawing model information
     */
    public void setModel(SMModel newModel) {
        model = newModel;
    }

    /**
     * Associate an interaction model to the controller
     * @param newIModel interaction model
     */
    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
    }

    /**
     * Set the selected cursor
     */
    public void handleSelectedCursor(String newCursor) {
        iModel.setSelectedCursor(newCursor);
    }


    /**
     * Designate what the controller should do
     * based on state when a mouse is pressed
     * @param event mouse event
     */
    public void handlePressed(double normX, double normY, MouseEvent event) {
        prevX = normX;
        prevY = normY;
        switch (currentState) {
            case READY -> {
                if (model.checkHit(normX, normY)) {
                    SMStateNode node = model.whichStateNode(normX, normY);
                    iModel.setSelectedStateNode(node);
                    currentState = State.SELECTED;
                } else {
                    iModel.setSelectedStateNode(null);
                    prevX = normX;
                    prevY = normY;
                    currentState = State.PREPARE_CREATE;
                }
            }

            case SELECTED -> {
                if (iModel.getSelectedStateNode() != null) {
                    boolean onSelectedStateNode = iModel.getSelectedStateNode().contains(normX, normY);
                    if (onSelectedStateNode) {
                        currentState = State.MOVING;
                    } else {
                        boolean onAnotherStateNode = model.checkHit(normX, normY);
                        if (onAnotherStateNode) {
                            iModel.setSelectedStateNode(model.whichStateNode(normX, normY));
                            model.setZOrdering(iModel.getSelectedStateNode());
                        }
                    }
                } else {
                    currentState = State.READY;
                }
            }
        }
    }


    /**
     * Designate what the controller should do
     * based on state when a mouse is released
     * @param event mouse event
     */


    public void handleReleased(double normX, double normY, MouseEvent event) {
        switch (currentState) {
            case PREPARE_CREATE -> {
                SMStateNode newStateNode = model.createStateNode(prevX, prevY);
                iModel.setSelectedStateNode(newStateNode);
                currentState = State.READY;
            }
            case MOVING -> {
                currentState = State.SELECTED;
            }

            case SELECTED -> {
                // check if on a state node
                if (prevX == normX && prevY == normY) {
                    boolean hit = model.checkHit(normX, normY);
                    if (hit) {
                        iModel.setSelectedStateNode(model.whichStateNode(normX, normY));
                        model.setZOrdering(iModel.getSelectedStateNode());
                    } else {
                        if (prevX == normX && prevY == normY) {
                            iModel.setSelectedStateNode(null);
                            currentState = State.READY;
                        }
                    }
                }
            }
        }
    }

    /**
     * Designate what the controller should do
     * based on state when a mouse is dragged
     * @param normX normalized x coordinate
     * @param normY normalized y coordinate
     * @param event mouse event
     */
    public void handleDragged(double normX, double normY, MouseEvent event) {
        // handle view panning
//        if (event.isSecondaryButtonDown()) {
//            double newX = event.getX()/1600;
//            double newY = event.getY()/1600;
//            model.pan(iModel.viewPort, newX, newY, noOffsetX, noOffsetY);
//        }

        switch (currentState) {
            case PREPARE_CREATE -> {
                currentState = State.READY;
            }

            case SELECTED -> {
                if (iModel.getSelectedStateNode() != null) {
                    boolean onStateNodeXY = iModel.getSelectedStateNode().contains(normX, normY);
                    if (onStateNodeXY) {
                        boolean onPrevStateNodeXY = iModel.getSelectedStateNode().contains(prevX, prevY);
                        if (onPrevStateNodeXY) {
                            // get ready to move state node
                            currentState = State.MOVING;
                        }
                    }
                } else {
                    currentState = State.READY;
                }
            }
            case MOVING -> {
                // move the state node
                model.moveStateNode(iModel.getSelectedStateNode(), normX, normY);

            }
        }
    }
}







