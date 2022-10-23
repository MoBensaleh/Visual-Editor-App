package com.example.assignment3.models;

import java.util.ArrayList;

/**
 * The interaction model that stores all elements related to view state.
 */
public class InteractionModel {
    private final ArrayList<IModelSubscriber> subs;
    private String selectedCursor;

    /**
     * Constructor for InteractionModel
     */
    public InteractionModel() {
        subs = new ArrayList<>();
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
     * Get the selected cursor
     * @return the selected cursor
     */
    public String getSelectedCursor() {
        return selectedCursor;
    }


}