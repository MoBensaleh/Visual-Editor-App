package com.example.assignment3.views;

import com.example.assignment3.controllers.AppController;
import com.example.assignment3.models.IModelSubscriber;
import com.example.assignment3.models.InteractionModel;
import com.example.assignment3.models.ModelSubscriber;
import com.example.assignment3.models.SMModel;
import javafx.scene.Cursor;
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
        diagramView = new DiagramView(800, 800);

        this.setLeft(toolPalette);
        this.setRight(diagramView);

        // make the canvas view resize based on the main application
        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            diagramView.setMaxWidth(newValue.doubleValue()-toolPalette.getWidth());
            diagramView.setPrefWidth(newValue.doubleValue()-toolPalette.getWidth());
        });
        this.heightProperty().addListener((observable, oldValue, newValue) -> {
            diagramView.setMaxHeight(newValue.doubleValue());
            diagramView.setPrefHeight(newValue.doubleValue());
        });
        this.setPrefSize(800, 800);
    }

    /**
     * Associate an interaction model to the view
     * @param newIModel interaction model
     */
    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
        toolPalette.setInteractionModel(newIModel);
        diagramView.setInteractionModel(newIModel);
        iModel.addSub(toolPalette);
        iModel.addSub(diagramView);
    }

    /**
     * Associate a model to the view
     * @param newModel the drawing model information
     */
    public void setModel(SMModel newModel) {
        model = newModel;
        diagramView.setModel(newModel);
        model.addSub(diagramView);
    }
    /**
     * Set a controller for the view
     * @param newController the controller
     */
    public void setController(AppController newController) {
        toolPalette.setController(newController);
        diagramView.setController(newController);
    }


    public void modelChanged() {

    }

    @Override
    public void iModelUpdated() {
        switch (iModel.getSelectedCursor()){
            case "pointer" -> {
                this.getScene().setCursor(Cursor.DEFAULT);
            }
            case "link" -> {
                this.getScene().setCursor(Cursor.CROSSHAIR);
            }
            case "move" -> {
                this.getScene().setCursor(Cursor.MOVE);
            }
        }

    }
}
