package com.example.assignment3.models;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The model that stores all items of the diagram.
 */
public class SMModel {
    private ArrayList<ModelSubscriber> subs;
    private ArrayList<SMItem> items;
    private int nextZ;

    /**
     * Constructor for SMModel
     */
    public SMModel() {
        subs = new ArrayList<>();
        items = new ArrayList<>();
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
     * Get the list of items to be drawn
     *
     * @return list of items
     */
    public ArrayList<SMItem> getItems() {
        return items;
    }


    /**
     * Create an item given position coordinates
     *
     * @param normX            normalized x coordinate
     * @param normY            normalized y coordinate
     * @param normHeight       normalized x2 (or width for state nodes) coordinate
     * @param normWidth        normalized y2 (or width for state nodes) coordinate
     */
    public SMItem addItem(double normX, double normY, double normWidth, double normHeight, String smId) {
        SMItem smItem;
        switch (smId) {
            case "Node" -> smItem = new SMStateNode(normX, normY, normWidth, normHeight);
            case "Link" -> smItem = new SMTransitionLink(normX, normY, normWidth, normHeight);
            default -> smItem = null;
        }

        smItem.setItemId(smId);
        setZOrdering(smItem);
        items.add(smItem);
        notifySubscribers();

        return smItem;
    }


    /**
     * Flag transition link as final and notify subscribers
     */
    public void addFinalLink(SMTransitionLink link){
        link.setFinal(true);
        notifySubscribers();
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
     * Check if any item was hit
     *
     * @param normX mouse X coordinate
     * @param normY mouse Y coordinate
     * @return true if clicked a state node, false otherwise
     */
    public boolean checkHit(double normX, double normY) {
        return items.stream().anyMatch(s -> s.contains(normX, normY));
    }

    /**
     * Determines which item was selected
     *
     * @param normX mouse X coordinate
     * @param normY mouse Y coordinate
     * @return the hit item
     */
    public SMItem whichItem(double normX, double normY) {
        SMItem found = null;
        for (SMItem item : items) {
            if (item.contains(normX, normY)) {
                found = item;
            }
        }
        return found;
    }

    /**
     * Move the selected item
     *
     * @param selectedItem selected item
     * @param normX             mouse X location
     * @param normY             mouse Y location
     */
    public void moveItem(SMItem selectedItem, double normX, double normY) {
        selectedItem.move(normX, normY);
        notifySubscribers();
    }

    /**
     * Delete the selected item
     *
     * @param selectedItem item to be deleted
     */
    public void deleteSelectedItem(SMItem selectedItem) {
        items.remove(selectedItem);
        notifySubscribers();
    }

    /**
     * Delete the transition link
     *
     * @param transitionLink transition link to be deleted
     */
    public void deleteTransitionLink(SMTransitionLink transitionLink) {
        items.remove(transitionLink);
        notifySubscribers();
    }

    /**
     * Set the Z value of an item
     *
     * @param item the item who's Z value needs to be set
     */
    public void setZOrdering(SMItem item) {
        item.setZ(nextZ);
        nextZ++;
        items.sort(Comparator.comparingInt(SMItem::getZ));
        notifySubscribers();
    }

    /**
     * Move the viewport based on panning in the main view
     * @param viewPort the viewport
     * @param newX the newest X location
     * @param newY the newest Y location
     * @param prevX the original mouse press X location
     * @param prevY the original mouse press Y location
     */
    public void pan(ViewPort viewPort, double newX, double newY, double prevX, double prevY) {
        viewPort.pan(newX, newY, prevX, prevY);
        notifySubscribers();
    }
}
