package com.example.assignment3.views;

import com.example.assignment3.controller.AppController;
import com.example.assignment3.models.IModelSubscriber;
import com.example.assignment3.models.InteractionModel;
import com.example.assignment3.models.SMModel;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * A view that contains and lays out the tool palette,
 * the diagram view, node properties view, and the link properties view.
 */
public class MainUI extends BorderPane implements IModelSubscriber {
    private ToolPalette toolPalette;
    private DiagramView diagramView;
    private NodePropertiesView nodePropertiesView;
    private LinkPropertiesView linkPropertiesView;
    private StackPane propertiesView;
    private MiniDiagramView miniDiagramView;
    private SMModel model;
    private InteractionModel iModel;

    public MainUI() {
        // create the toolbar and canvas views to store
        toolPalette = new ToolPalette();
        diagramView = new DiagramView(1600, 1600);
        nodePropertiesView = new NodePropertiesView();
        linkPropertiesView = new LinkPropertiesView();
        miniDiagramView = new MiniDiagramView(1600, 1600);
        miniDiagramView.setMouseTransparent(true);

        StackPane diagramViews = new StackPane(diagramView, miniDiagramView);
        StackPane.setAlignment(miniDiagramView, Pos.TOP_LEFT);

        // Setting initial properties view
        propertiesView = nodePropertiesView;

        diagramView.setMinWidth(this.getWidth()-toolPalette.getWidth()-propertiesView.getWidth());
        miniDiagramView.setMinWidth(this.getWidth()-toolPalette.getWidth()-propertiesView.getWidth());

        this.setLeft(toolPalette);
        this.setCenter(diagramViews);
        this.setRight(propertiesView);

        // make the canvas view resize based on the main application
        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            diagramViews.setMaxWidth(newValue.doubleValue()-toolPalette.getWidth()-nodePropertiesView.getWidth());
            diagramViews.setPrefWidth(newValue.doubleValue()-toolPalette.getWidth()-nodePropertiesView.getWidth());

        });
        this.heightProperty().addListener((observable, oldValue, newValue) -> {
            diagramViews.setMaxHeight(newValue.doubleValue());
            diagramViews.setPrefHeight(newValue.doubleValue());
        });
        this.setPrefSize(1150, 800);
    }

    /**
     * Associate an interaction model to the view
     * @param newIModel interaction model
     */
    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
        toolPalette.setInteractionModel(newIModel);
        diagramView.setInteractionModel(newIModel);
        nodePropertiesView.setInteractionModel(newIModel);
        linkPropertiesView.setInteractionModel(newIModel);
        miniDiagramView.setInteractionModel(newIModel);
        iModel.addSub(toolPalette);
        iModel.addSub(diagramView);
        iModel.addSub(nodePropertiesView);
        iModel.addSub(linkPropertiesView);
        iModel.addSub(miniDiagramView);
    }

    /**
     * Associate a model to the view
     * @param newModel the drawing model information
     */
    public void setModel(SMModel newModel) {
        model = newModel;
        diagramView.setModel(newModel);
        miniDiagramView.setModel(newModel);
        model.addSub(diagramView);
        model.addSub(miniDiagramView);
    }
    /**
     * Set a controller for the view
     * @param newController the controller
     */
    public void setController(AppController newController) {
        toolPalette.setController(newController);
        diagramView.setController(newController);
        nodePropertiesView.setController(newController);
        linkPropertiesView.setController(newController);

    }

    /**
     * Update view based on iModel changes
     */
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
                Image moveCursor = new Image(this.getClass().getResourceAsStream("/assets/move.png"), 20, 20, false, false);
                this.getScene().setCursor(new ImageCursor(moveCursor));
            }
        }

        if(iModel.getCurrentPropertiesViewId() == "link"){
            propertiesView = linkPropertiesView;
            this.setRight(linkPropertiesView);
        }
        else{
            propertiesView = nodePropertiesView;
            this.setRight(nodePropertiesView);
        }
    }
}
