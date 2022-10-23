package com.example.assignment3.controllers;


import com.example.assignment3.models.InteractionModel;
import com.example.assignment3.models.SMModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * The controller to handle events from the view classes.
 */
public class AppController {
    protected SMModel model;
    protected InteractionModel iModel;
    protected double prevX, prevY, noOffsetX, noOffsetY;

    protected enum State {
        READY, PREPARE_CREATE, RESIZING, SELECTED, MOVING
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

}
