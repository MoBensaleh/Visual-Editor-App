package com.example.assignment3.views;

import com.example.assignment3.controllers.AppController;
import com.example.assignment3.models.*;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * A view that contains a canvas to show the
 * drawing and allow user interaction.
 */
public class DiagramView extends StackPane implements ModelSubscriber, IModelSubscriber {
    protected Canvas myCanvas;
    protected GraphicsContext gc;
    protected SMModel model;
    protected InteractionModel iModel;
    protected double docWidth, docHeight;

    /**
     * Constructor for DrawingView
     */
    public DiagramView(double newDocWidth, double newDocHeight) {
        myCanvas = new Canvas(800,800);
        gc = myCanvas.getGraphicsContext2D();
        this.setStyle("-fx-background-color: lightblue");
        this.getChildren().add(myCanvas);
        docWidth = newDocWidth;
        docHeight = newDocHeight;
    }

    /**
     * Draws the shapes onto the canvas in immediate-mode graphics
     */
    public void draw() {
        double width = docWidth;
        double height = docHeight;
        gc.clearRect(0, 0, width, height);
        gc.setLineWidth(2.0);

        for (SMStateNode node : model.getStateNodes()) {
            if (node.equals(iModel.getSelectedStateNode())) {
                gc.setStroke(Color.RED);
            }
            else{
                gc.setStroke(Color.BLACK);
            }
            gc.setFill(Color.YELLOW);
            gc.fillRect(node.x, node.y,
                    node.width, node.height);
            gc.strokeRect(node.x, node.y,
                    node.width, node.height);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            gc.setFill(Color.BLACK);
            gc.fillText(node.getNodeText(), node.x + (node.width / 2), node.y + (node.height / 2));

        }

        for (SMTransitionLink link : model.getTransitionLinks()) {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2.0);
            gc.strokeLine(link.x, link.y,
                    link.x2, link.y2);


        }
    }

    /**
     * Associate a model to the view
     * @param newModel the drawing model information
     */
    public void setModel(SMModel newModel) {
        model = newModel;
    }

    /**
     * Associate an interaction model to the view
     * @param newIModel interaction model
     */
    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
    }

    /**
     * Set a controller for the view
     * @param newController the controller
     */
    public void setController(AppController newController) {
        // re-draw canvas when application is resized
        this.widthProperty().addListener((observable, oldVal, newVal) -> {
            myCanvas.setWidth(newVal.doubleValue());
            draw();
        });
        this.heightProperty().addListener((observable, oldVal, newVal) -> {
            myCanvas.setHeight(newVal.doubleValue());
            draw();
        });
        // event handlers for interaction on canvas
        double width = docWidth;
        double height = docHeight;
        myCanvas.setOnMousePressed(e -> {
            newController.handlePressed((e.getX()), (e.getY()), e);
        });
        myCanvas.setOnMouseReleased(e -> {
            newController.handleReleased((e.getX()), (e.getY()), e);
        });
        myCanvas.setOnMouseDragged(e -> {
            newController.handleDragged((e.getX()), (e.getY()), e);
        });
    }

    /**
     * Update view based on model changes
     */
    @Override
    public void modelChanged() {
        draw();
    }

    /**
     * Update view based on iModel changes
     */
    @Override
    public void iModelUpdated() {
        draw();
    }
}