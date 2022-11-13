package com.example.assignment3.views;

import com.example.assignment3.controllers.AppController;
import com.example.assignment3.models.*;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

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

        for (SMItem item : model.getItems()) {
//
            if(item instanceof SMStateNode){
                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.BLACK);
                if (item.equals(iModel.getSelectedItem())) {
                    gc.setStroke(Color.RED);
                }

                gc.fillRect(item.x-(item.width/2), item.y-(item.height/2),
                        item.width, item.height);
                gc.strokeRect(item.x-(item.width/2), item.y-(item.height/2),
                        item.width, item.height);
                gc.setTextAlign(TextAlignment.CENTER);
                gc.setTextBaseline(VPos.CENTER);
                gc.setFill(Color.BLACK);
                gc.fillText(item.getItemText(), item.x, item.y);


            }
            if(item instanceof SMTransitionLink){
                if(((SMTransitionLink) item).getFinal()){
                    //Check if the transition node is selected:
                    if(item.equals(iModel.getSelectedItem())) {
                        gc.setStroke(Color.BLACK);
                        drawTransition(gc, (SMTransitionLink) item, true);
                    }
                    else{
                        gc.setStroke(Color.BLACK);
                        drawTransition(gc, (SMTransitionLink) item, false);

                    }


                }
                //If it's a temporary link
                else {
                    gc.setStroke(Color.BLACK);
                    gc.strokeLine(item.x, item.y, item.width, item.height);
                }
            }

        }

    }

    private void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2, double width, double height){

        gc.setFill(Color.BLACK);

        double dx = x2 - x1, dy = y2 - y1;
        //Get the angle of the arrow (where it's pointing)
        double arrowAngle = Math.atan2(dy, dx);
        double c = Math.cos(arrowAngle);
        double s = Math.sin(arrowAngle);
        double x;
        double y;

        if(width * Math.abs(s) < height * Math.abs(c)){
            x = Math.signum(c) * (width/2);
            y = Math.tan(arrowAngle) * x;
        }
        else{
            y = Math.signum(s) * (height/2);
            x = (1.0 / Math.tan(arrowAngle)) * y;

        }

        int length = (int) Math.sqrt(dx * dx + dy * dy);

        Transform arrowTransform = Transform.translate(x1-x, y1-y);

        arrowTransform = arrowTransform.createConcatenation(Transform.rotate(Math.toDegrees(arrowAngle), 0, 0));

        gc.setTransform(new Affine(arrowTransform));

        //Draw the triangle
        gc.fillPolygon(new double[]{length, length - 8, length - 8}, new double[]{0, -8, 8},
                3);


        //Now we reset the graphics context's transform so it doesn't mess up future drawings
        gc.setTransform(new Affine(Transform.translate(0, 0)));
    }


    private void drawTransition(GraphicsContext gc, SMTransitionLink link, boolean isSelected){

        gc.strokeLine(link.x, link.y, link.transitionNodeX, link.transitionNodeY);
        gc.strokeLine(link.transitionNodeX, link.transitionNodeY, link.width, link.height);

        drawArrow(gc, link.x,link.y,  link.transitionNodeX, link.transitionNodeY, 120.0, 120.0);
        drawArrow(gc, link.transitionNodeX, link.transitionNodeY, link.width, link.height, 110.0, 60.0);

        gc.setStroke(Color.BLACK);
        //If it's selected
        if(isSelected){
            gc.setStroke(Color.RED);
        }

        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(link.transitionNodeX-60, link.transitionNodeY-60, 120, 120);


        gc.setFill(Color.BLACK);
//        gc.fillText(" -Event:\n "+link.getEvent() + "\n -Context:\n " + link.getContext() + "\n -Side Effects:\n " + link.getSideEffects(), link.x, link.y-40);
        //gc.setStroke(Color.BLACK);


        gc.strokeRect(link.transitionNodeX-60, link.transitionNodeY-60, 120, 120);

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