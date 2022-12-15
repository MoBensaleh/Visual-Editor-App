package com.example.visualeditor.models;

import java.util.ArrayList;

/**
 * The interaction model that stores all elements related to view state.
 */
public class InteractionModel {
    private final ArrayList<IModelSubscriber> subs;
    private String selectedCursor;
    private SMItem selectedItem;
    private String currentPropertiesView;
    public ViewPort viewPort;

    /**
     * Constructor for InteractionModel
     */
    public InteractionModel() {
        subs = new ArrayList<>();
        viewPort = new ViewPort(0, 0, 0.25, 0.25);
    }

    /**
     * Add a new view subscriber to the model
     * @param newSub a view
     */
    public void addSub(IModelSubscriber newSub) {
        subs.add(newSub);
    }

    /**
     * Notify all subscribers that the model has changed
     */
    public void notifySubscribers() {
        subs.forEach(IModelSubscriber::iModelUpdated);
    }

    /**
     * Set the selected cursor
     * @param newCursor selected cursor
     */
    public void setSelectedCursor(String newCursor) {
        selectedCursor = newCursor;
        notifySubscribers();
    }
    /**
     * Set the currently selected item
     * @param newItem selected item
     */
    public void setSelectedItem(SMItem newItem) {
        selectedItem = newItem;
        notifySubscribers();
    }

    /**
     * Set the item properties view id
     * @param propertyViewId right properties view id based on if a state node or transition link is selected
     */
    public void setPropertiesViewId(String propertyViewId) {
        currentPropertiesView = propertyViewId;
        notifySubscribers();
    }

    /**
     * Get the current properties view id
     *  @return the current properties view id
     */
    public String getCurrentPropertiesViewId() {
        return currentPropertiesView;
    }
    /**
     * Get the currently selected item
     * @return selected item
     */
    public SMItem getSelectedItem() {
        return selectedItem;
    }
    /**
     * Get the selected cursor
     * @return the selected cursor
     */
    public String getSelectedCursor() {
        return selectedCursor;
    }


}