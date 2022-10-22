package com.example.assignment3.views;

import com.example.assignment3.controllers.AppController;
import com.example.assignment3.models.IModelSubscriber;
import com.example.assignment3.models.InteractionModel;
import com.example.assignment3.models.ModelSubscriber;
import com.example.assignment3.models.SMModel;
import javafx.scene.layout.BorderPane;

/**
 * A view that contains and lays out the tool palette,
 * the diagram view, node properties view, and the link properties view.
 */
public class MainUI extends BorderPane implements ModelSubscriber, IModelSubscriber {
    private ToolPalette toolPalette;
    private DiagramView diagramView;
    private SMModel model;
    private InteractionModel iModel;

    public MainUI() {
        // create the toolbar and canvas views to store
        toolPalette = new ToolPalette();
        this.setLeft(toolPalette);
        this.setPrefSize(800, 800);
    }

    /**
     * Associate an interaction model to the view
     * @param newIModel interaction model
     */
    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
        toolPalette.setInteractionModel(newIModel);
        iModel.addSub(toolPalette);
    }

    /**
     * Set a controller for the view
     * @param newController the controller
     */
    public void setController(AppController newController) {
        toolPalette.setController(newController);
    }


    public void modelChanged() {

    }

    @Override
    public void iModelUpdated() {

    }
}
