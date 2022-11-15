package com.example.assignment3.controllers;

import com.example.assignment3.models.*;
import javafx.scene.input.MouseEvent;

/**
 * The controller to handle events from the view classes.
 */
public class AppController {
    protected SMModel model;
    protected InteractionModel iModel;
    protected double prevX, prevY, noOffsetX, noOffsetY;
    private SMTransitionLink transitionLink;
    private SMStateNode startNode;
    private SMStateNode endNode;
    private Boolean linkCreated = false;

    protected enum State {
        READY, PREPARE_CREATE, SELECTED, DRAGGING
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
     * @param newCursor selected cursor
     */
    public void handleSelectedCursor(String newCursor) {
        currentState = State.READY;
        iModel.setSelectedCursor(newCursor);
    }

    /**
     * Set the width of the viewport
     * @param newWidth the new width
     */
    public void setViewPortWidth(double newWidth) {
        iModel.viewPort.width = newWidth;
        iModel.notifySubscribers();
    }

    /**
     * Set the height of the viewport
     * @param newHeight the new height
     */
    public void setViewPortHeight(double newHeight) {
        iModel.viewPort.height = newHeight;
        iModel.notifySubscribers();
    }

    /**
     * Delete the selected item if there is one
     */
    public void handleDeleteSelected() {
        if (iModel.getSelectedItem() != null) {
            if(!iModel.getSelectedItem().isTransition()){
                SMStateNode stateNode = (SMStateNode) iModel.getSelectedItem();
                stateNode.getTransitionLinks().forEach(link -> {
                    model.deleteSelectedItem(link);
                });
            }
            model.deleteSelectedItem(iModel.getSelectedItem());
            iModel.setSelectedItem(null);
            currentState = State.READY;
        }
    }


    /**
     * Updates all properties associated with a transition link
     * @param link selected transition link
     * @param event event property
     * @param context context property
     * @param sideEffects side effects property
     */
    public void handleUpdateLinkProperties(SMTransitionLink link, String event, String context, String sideEffects){
        link.setEvent(event);
        link.setContext(context);
        link.setSideEffects(sideEffects);
        iModel.notifySubscribers();
    }

    /**
     * Updates all properties associated with a state node
     * @param node selected state node
     * @param state state property
     */
    public void handleUpdateNodeProperties(SMStateNode node, String state){
        node.setState(state);
        iModel.notifySubscribers();
    }

    /**
     * Handles properties view switch depending on selected item
     * @param normX normalized x coordinate of selected item
     * @param normY normalized y coordinate of selected item
     */
    public void handlePropertiesViewSwitch(double normX, double normY){
        boolean hit = model.checkHit(normX, normY);
        if(hit && model.whichItem(normX, normY).isTransition() || linkCreated){
            iModel.setPropertiesViewId("link");
        }
        else{
            iModel.setPropertiesViewId("node");
        }
    }


    /**
     * Designate what the controller should do
     * based on state when a mouse is pressed
     * @param event mouse event
     */
    public void handlePressed(double normX, double normY, MouseEvent event) {
        prevX = normX;
        prevY = normY;
        noOffsetX = event.getX()/1600;
        noOffsetY = event.getY()/1600;
        switch(iModel.getSelectedCursor()){
            case "pointer" ->{
                switch (currentState){
                    case READY -> {
                        linkCreated = false;
                        boolean hit = model.checkHit(normX, normY);
                        if (hit) {
                            SMItem item = model.whichItem(normX, normY);
                            iModel.setSelectedItem(item);
                            currentState = State.SELECTED;
                        }
                        else {
                            iModel.setSelectedItem(null);
                            prevX = normX;
                            prevY = normY;
                            currentState = State.PREPARE_CREATE;
                        }
                        handlePropertiesViewSwitch(normX, normY);
                    }

                    case SELECTED -> {
                        if (iModel.getSelectedItem() != null) {
                            boolean onSelectedStateNode = iModel.getSelectedItem().contains(normX, normY);
                            if (onSelectedStateNode) {
                                currentState = State.DRAGGING;
                            } else {
                                boolean onAnotherStateNode = model.checkHit(normX, normY);
                                if (onAnotherStateNode) {
                                    iModel.setSelectedItem(model.whichItem(normX, normY));
                                    model.setZOrdering(iModel.getSelectedItem());
                                }
                                else{
                                    iModel.setSelectedItem(null);
                                    prevX = normX;
                                    prevY = normY;
                                    currentState = State.PREPARE_CREATE;
                                }
                            }
                        } else {
                            currentState = State.READY;
                        }
                        handlePropertiesViewSwitch(normX, normY);
                    }
                }
            }

            case "link" ->{
                switch (currentState){
                    case READY -> {
                        if (model.checkHit(normX, normY) && !model.whichItem(normX, normY).isTransition()) {
                            startNode = (SMStateNode) model.whichItem(normX, normY);
                            currentState = State.PREPARE_CREATE;
                        }
                        else{
                            currentState = State.READY;
                        }
                    }

                    case SELECTED -> {
                        if (iModel.getSelectedItem() != null) {
                            boolean onSelectedStateNode = iModel.getSelectedItem().contains(normX, normY);
                            if (onSelectedStateNode && !model.whichItem(normX, normY).isTransition()) {
                                startNode = (SMStateNode) model.whichItem(normX, normY);
                                currentState = State.PREPARE_CREATE;
                            } else {
                                boolean onAnotherStateNode = model.checkHit(normX, normY);
                                if (onAnotherStateNode && !model.whichItem(normX, normY).isTransition()) {
                                    startNode = (SMStateNode) model.whichItem(normX, normY);
                                }
                            }
                        } else {
                            currentState = State.READY;
                        }
                    }
                }

            }

            case "move" ->{
                switch (currentState){
                    case READY -> {
                        currentState = State.DRAGGING;
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
        switch (iModel.getSelectedCursor()){
            case "pointer" -> {
                switch (currentState){
                    case PREPARE_CREATE -> {
                        currentState = State.READY;
                    }
                    case SELECTED -> {
                        if (iModel.getSelectedItem() != null) {
                            boolean onStateNodeXY = iModel.getSelectedItem().contains(normX, normY);
                            if (onStateNodeXY) {
                                boolean onPrevStateNodeXY = iModel.getSelectedItem().contains(prevX, prevY);
                                if (onPrevStateNodeXY) {
                                    // get ready to move state node
                                    currentState = State.DRAGGING;
                                }
                            }
                        } else {
                            currentState = State.READY;
                        }
                    }

                    case DRAGGING -> {
                        model.moveItem(iModel.getSelectedItem(), normX, normY);

                        model.getItems().forEach(item -> {
                            if(!item.isTransition()){
                                ((SMStateNode) item).getTransitionLinks().forEach(tLink -> {
                                    if(iModel.getSelectedItem() == tLink.getEndNode()){
                                        model.resizeTransitionLinkEnd(tLink, normX, normY);
                                    }  if (iModel.getSelectedItem() == tLink.getStartNode()) {
                                        model.resizeTransitionLinkStart(tLink, normX, normY);
                                    }
                                });
                            }
                        });
                    }
                }

            }
            case "link" -> {
                switch (currentState){
                    case PREPARE_CREATE -> {
                        // adjust the size of the transition link being drawn
                        model.deleteTransitionLink(transitionLink);
                        transitionLink = (SMTransitionLink) model.addItem(prevX, prevY, normX, normY, "Link");
                        model.notifySubscribers();
                        currentState = State.DRAGGING;
                    }

                    case SELECTED -> {
                        currentState = State.PREPARE_CREATE;
                    }

                    case DRAGGING -> {
                        // resize the transition link
                        if(transitionLink != null){
                            model.resizeTransitionLinkEnd(transitionLink, normX, normY);
                        }
                    }
                }
            }

            case "move" -> {
                switch (currentState){
                    case DRAGGING -> {
                        // handle view panning
                        double newX = event.getX()/1600;
                        double newY = event.getY()/1600;
                        model.pan(iModel.viewPort, newX, newY, noOffsetX, noOffsetY);
                    }
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

        switch (iModel.getSelectedCursor()){
            case "pointer" ->{
                switch (currentState){
                    case PREPARE_CREATE -> {
                        iModel.setSelectedItem(model.addItem(prevX, prevY, 0.09, 0.05, "Node"));
                        currentState = State.READY;
                    }
                    case DRAGGING -> {
                        currentState = State.SELECTED;
                    }
                    case SELECTED -> {
                        currentState = State.READY;
                    }
                }
            }

            case "link"->{
                switch (currentState){
                    case PREPARE_CREATE, SELECTED -> {
                        currentState = State.READY;
                    }
                    case DRAGGING -> {
                        // check if on a state node
                        boolean hit = model.checkHit(normX, normY);
                        if (hit && !model.whichItem(normX, normY).isTransition()) {

                            model.deleteTransitionLink(transitionLink);
                            endNode = (SMStateNode) model.whichItem(normX, normY);

                            transitionLink = (SMTransitionLink) model.addItem(prevX, prevY, normX, normY, "Link");
                            iModel.setSelectedItem(transitionLink);
                            linkCreated = true;
                            handlePropertiesViewSwitch(normX, normY);

                            //Set the start and end nodes of transition link
                            transitionLink.setEndNode(endNode);
                            transitionLink.setStartNode(startNode);

                            // Set coordinates of transition link to center
                            transitionLink.setX(transitionLink.getStartNode().getX());
                            transitionLink.setY(transitionLink.getStartNode().getY());
                            transitionLink.setX2(transitionLink.getEndNode().getX());
                            transitionLink.setY2(transitionLink.getEndNode().getY());

                            // Setting coordinates of transition node
                            if(transitionLink.getStartNode() == transitionLink.getEndNode()){
                                if(prevX < normX){
                                    transitionLink.transitionNodeX = startNode.getX() + .16;
                                }
                                else {
                                    transitionLink.transitionNodeX = startNode.getX() - .16;
                                }
                                transitionLink.transitionNodeY = startNode.getY();
                            }
                            else{
                                transitionLink.transitionNodeX = (startNode.getX() + endNode.getX())/2;
                                transitionLink.transitionNodeY = (startNode.getY() + endNode.getY())/2;
                            }

                            transitionLink.getStartNode().addLink(transitionLink);
                            transitionLink.getEndNode().addLink(transitionLink);

                            // notifies subscribers to make full transition link
                            model.addFinalLink(transitionLink);

                            transitionLink = null;
                            currentState = State.READY;

                        } else {
                            model.deleteTransitionLink(transitionLink);
                            currentState = State.READY;
                        }
                    }
                }
            }
        }
    }
}







