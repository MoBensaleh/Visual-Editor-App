package com.example.assignment3.models;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The model that stores all elements of the drawing.
 */
public class SMModel {
    private ArrayList<ModelSubscriber> subs;
    private ArrayList<SMStateNode> stateNodes;
    private ArrayList<SMTransitionLink> transitionLinks;
    private int nextZ;

    /**
     * Constructor for SMModel
     */
    public SMModel() {
        subs = new ArrayList<>();
        stateNodes = new ArrayList<>();
        transitionLinks = new ArrayList<>();
        nextZ = 0;
    }

    /**
     * Add a new view subscriber to the model
     *
     * @param newSub a view
     */
    public void addSub(ModelSubscriber newSub) {
        subs.add(newSub);
    }

    /**
     * Notify all subscribers that the model has changed
     */
    public void notifySubscribers() {
        subs.forEach(ModelSubscriber::modelChanged);
    }

    /**
     * Get the list of state nodes to be drawn
     *
     * @return list of state nodes
     */
    public ArrayList<SMStateNode> getStateNodes() {
        return stateNodes;
    }

    /**
     * Get the list of transition links to be drawn
     *
     * @return list of transition links
     */
    public ArrayList<SMTransitionLink> getTransitionLinks() {
        return transitionLinks;
    }


    /**
     * Create a state node given position coordinates
     *
     * @param normX            normalized x coordinate
     * @param normY            normalized y coordinate
     */
    public SMStateNode createStateNode(double normX, double normY) {
        SMStateNode stateNode;
        stateNode = new SMStateNode(normX, normY, 110.0, 60.0);

        stateNode.setNodeText("Default");
        setZOrdering(stateNode);
        stateNodes.add(stateNode);
        notifySubscribers();
        return stateNode;
    }

    /**
     * Create a transition link given position coordinates and a start and end state node.
     *
     * @param normX1 starting x coordinate
     * @param normY1 starting y coordinate
     * @param startNode start state node
     *
     */
    public SMTransitionLink createTransitionLink(double normX1, double normY1, double normX2, double normY2, SMStateNode startNode) {
        SMTransitionLink smTransitionLink;
        smTransitionLink = new SMTransitionLink(normX1, normY1, normX2, normY2, startNode, startNode);
        setLinkZOrdering(smTransitionLink);
        startNode.addTransitionLink(smTransitionLink);
        transitionLinks.add(smTransitionLink);
        notifySubscribers();
        return smTransitionLink;
    }

    /**
     * Resize transition link end coordinates (dragging action)
     * @param transitionLink transition link
     * @param normX new location for x2 (end)
     * @param normY new location for y2 (end)
     */
    public void resizeTransitionLinkEnd(SMTransitionLink transitionLink, double normX, double normY) {
        transitionLink.resizeEnd(normX, normY);
        notifySubscribers();
    }

    /**
     * Resize transition link start coordinates (dragging action)
     * @param transitionLink transition link
     * @param normX new location for x2 (end)
     * @param normY new location for y2 (end)
     */
    public void resizeTransitionLinkStart(SMTransitionLink transitionLink, double normX, double normY) {
        transitionLink.resizeStart(normX, normY);
        notifySubscribers();
    }

    /**
     * Check if any state node was hit
     *
     * @param normX mouse X coordinate
     * @param normY mouse Y coordinate
     * @return true if clicked a state node, false otherwise
     */
    public boolean checkHit(double normX, double normY) {
        return stateNodes.stream().anyMatch(s -> s.contains(normX, normY));
    }

    /**
     * Determines which state node was selected
     *
     * @param normX mouse X coordinate
     * @param normY mouse Y coordinate
     * @return the hit state node
     */
    public SMStateNode whichStateNode(double normX, double normY) {
        SMStateNode found = null;
        for (SMStateNode stateNode : stateNodes) {
            if (stateNode.contains(normX, normY)) {
                found = stateNode;
            }
        }
        return found;
    }

    /**
     * Move the selected state node
     *
     * @param selectedStateNode selected state node
     * @param normX             mouse X location
     * @param normY             mouse Y location
     */
    public void moveStateNode(SMStateNode selectedStateNode, double normX, double normY) {
        selectedStateNode.move(normX, normY);
        notifySubscribers();
    }

    /**
     * Delete the selected state node
     *
     * @param selectedStateNode state node to be deleted
     */
    public void deleteSelectedStateNode(SMStateNode selectedStateNode) {
        stateNodes.remove(selectedStateNode);
        notifySubscribers();
    }

    /**
     * Delete the transition link
     *
     * @param transitionLink transition link to be deleted
     */
    public void deleteTransitionLink(SMTransitionLink transitionLink) {
        transitionLinks.remove(transitionLink);
        notifySubscribers();
    }

    /**
     * Set the Z value of a state node
     *
     * @param stateNode the state node who's Z value needs to be set
     */
    public void setZOrdering(SMStateNode stateNode) {
        stateNode.setZ(nextZ);
        nextZ++;
        stateNodes.sort(Comparator.comparingInt(SMStateNode::getZ));
        notifySubscribers();
    }

    /**
     * Set the Z value of a transition link
     *
     * @param transitionLink the transition link who's Z value needs to be set
     */
    public void setLinkZOrdering(SMTransitionLink transitionLink) {
        transitionLink.setZ(nextZ);
        nextZ++;
        transitionLinks.sort(Comparator.comparingInt(SMTransitionLink::getZ));
        notifySubscribers();
    }

    /**
     * Move the viewport on the mini view
     * @param viewPort the viewport
     * @param normX x location of viewport
     * @param normY y location of viewport
     */
//    public void moveViewPort(ViewPort viewPort, double normX, double normY) {
//        viewPort.move(normX, normY);
//        notifySubscribers();
//    }

    /**
     * Move the viewport based on panning in the main view
     * @param viewPort the viewport
     * @param newX the newest X location
     * @param newY the newest Y location
     * @param prevX the original mouse press X location
     * @param prevY the original mouse press Y location
     */
//    public void pan(ViewPort viewPort, double newX, double newY, double prevX, double prevY) {
//        viewPort.pan(newX, newY, prevX, prevY);
//        notifySubscribers();
//    }
}
