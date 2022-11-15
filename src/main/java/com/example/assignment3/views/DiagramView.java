package com.example.assignment3.views;

import com.example.assignment3.controllers.AppController;
import com.example.assignment3.models.*;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.util.Pair;

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
     * Constructor for DiagramView
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
     * Draws the items onto the canvas in immediate-mode graphics
     */
    public void draw() {
        double width = docWidth;
        double height = docHeight;
        double xOffset = iModel.viewPort.x * width;
        double yOffset = iModel.viewPort.y * height;
        gc.clearRect(0, 0, width, height);
        gc.setLineWidth(2.0);

        for (SMItem item : model.getItems()) {
//
            if(item instanceof SMStateNode){
                SMStateNode stateNode = (SMStateNode) item;
                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.BLACK);
                if (item.equals(iModel.getSelectedItem())) {
                    gc.setStroke(Color.RED);
                }

                gc.fillRect(((item.x-(item.width/2)) * width - xOffset), ((item.y-(item.height/2)) * height - yOffset),
                        item.width*width, item.height*height);
                gc.strokeRect(((item.x-(item.width/2)) * width - xOffset), ((item.y-(item.height/2)) * height - yOffset),
                        item.width*width, item.height*height);
                gc.setTextAlign(TextAlignment.CENTER);
                gc.setTextBaseline(VPos.CENTER);
                gc.setFill(Color.BLACK);
                gc.fillText(stateNode.getState(), item.x * width - xOffset, item.y * height - yOffset);


            }
            if(item instanceof SMTransitionLink){
                if(((SMTransitionLink) item).getFinal()){
                    gc.setStroke(Color.BLACK);
                    drawTransition(gc, (SMTransitionLink) item, item.equals(iModel.getSelectedItem()), width, height, xOffset, yOffset, false);
                }
                //If it's a temporary link
                else {
                    gc.setStroke(Color.BLACK);
                    gc.strokeLine(((item.x) * width - xOffset), ((item.y) * height - yOffset),
                            item.width*width, item.height*height);
                }
            }
        }
    }

    /**
     * Draws arrows at the end of transitions
     * @param gc                    2D graphics context
     * @param x1                    normalized x coordinate
     * @param y1                    normalized y coordinate
     * @param x2                    normalized x2 coordinate
     * @param y2                    normalized y2 coordinate
     * @param nodeWidth             width of node that arrow will be touching
     * @param nodeHeight            height of node that arrow will be touching
     * @param width                 document width
     * @param height                document height
     * @param isMiniatureTransition flag to check if arrow is being drawn on miniDiagramView
     *
     */
    private void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2,
                           double nodeWidth, double nodeHeight, double width, double height, Boolean isMiniatureTransition){
        double dx = x2 - x1, dy = y2 - y1;
        double arrowAngle = Math.atan2(dy, dx);
        int length = (int) Math.sqrt(dx * dx + dy * dy);

        gc.setFill(Color.BLACK);
        if(isMiniatureTransition){
            gc.setFill(Color.rgb(0,0,0,0.3));

        }
        Pair<Double, Double> nodeEdgeCoordinates = getNodeEdgeCoords(arrowAngle, nodeWidth*width, nodeHeight*height);
        Transform arrowTransform = Transform.translate(x1 - nodeEdgeCoordinates.getKey(), (y1 - nodeEdgeCoordinates.getValue()));
        arrowTransform = arrowTransform.createConcatenation(Transform.rotate(Math.toDegrees(arrowAngle), 0, 0));

        gc.setTransform(new Affine(arrowTransform));

        //Draw the triangle
        gc.fillPolygon(new double[]{(length), length-15, length-15},
                new double[]{0, -6, 6},
                3);

        //Now we reset the graphics context's transform so it doesn't mess up future drawings
        gc.setTransform(new Affine(Transform.translate(0, 0)));
    }


    /**
     * Draws final transitions
     * @param gc                    2D graphics context
     * @param link                  transition link created
     * @param isSelected            flag to check if link is selected
     * @param xOffset               view port x offset
     * @param yOffset               view port y offset
     * @param width                 document width
     * @param height                document height
     * @param isMiniatureTransition flag to check if arrow is being drawn on miniDiagramView
     *
     */
    protected void drawTransition(GraphicsContext gc, SMTransitionLink link, boolean isSelected, double width,
                                  double height, double xOffset, double yOffset, Boolean isMiniatureTransition ){

        // Ensuring when transition node is selected, first transition link isn't shown on top of state node due to z-ordering
        double startDX = link.transitionNodeX - link.x, startDY = link.transitionNodeY - link.y;
        double startArrowAngle = Math.atan2(startDY, startDX);
        Pair<Double, Double> startNodeEdgeCoordinates = getNodeEdgeCoords(startArrowAngle, 0.09, 0.05);
        gc.strokeLine((link.x+startNodeEdgeCoordinates.getKey())*width-xOffset,
                (link.y+startNodeEdgeCoordinates.getValue())*height-yOffset, link.transitionNodeX*width-xOffset,
                link.transitionNodeY*height-yOffset);

        // For self-links
        if(link.getStartNode() == link.getEndNode()){
            drawSelfLink(gc, link, startArrowAngle, startNodeEdgeCoordinates.getKey(), startNodeEdgeCoordinates.getValue(),
                    width, height, xOffset, yOffset);
        }

        // Ensuring when transition node is selected, second transition link isn't shown on top of state node due to z-ordering
        double endDX = link.width - link.transitionNodeX, endDY = link.height - link.transitionNodeY;
        double endArrowAngle = Math.atan2(endDY, endDX);
        Pair<Double, Double> endNodeEdgeCoordinates = getNodeEdgeCoords(endArrowAngle, 0.09, 0.05);
        gc.strokeLine(link.transitionNodeX*width-xOffset, link.transitionNodeY*height-yOffset,
                (link.width-endNodeEdgeCoordinates.getKey())*width-xOffset, (link.height-endNodeEdgeCoordinates.getValue())*height-yOffset);


        // Drawing arrows
        drawArrow(gc, link.x*width-xOffset,link.y*height-yOffset, link.transitionNodeX*width-xOffset,
                link.transitionNodeY*height-yOffset, 0.1, 0.1, width,height, isMiniatureTransition);

        drawArrow(gc, link.transitionNodeX*width-xOffset, link.transitionNodeY*height-yOffset, link.width*width-xOffset,
                link.height*height-yOffset, 0.09, 0.05, width,height, isMiniatureTransition);

        if(!isMiniatureTransition){
            gc.setStroke(Color.BLACK);
            //If it's selected
            if(isSelected){
                gc.setStroke(Color.RED);
            }
            gc.setFill(Color.rgb(255, 255, 224));
        }

        else{
            gc.setStroke(Color.rgb(0,0,0,0.3));
            //If it's selected
            if(isSelected){
                gc.setStroke(Color.rgb(227,10,10, 0.3));
            }
            gc.setFill(Color.rgb(255, 255, 224,0.3));
        }

        // Drawing Transition Node
        gc.fillRect((link.transitionNodeX-0.05)*width-xOffset, (link.transitionNodeY-0.05)*height-yOffset, 0.1*width, 0.1*height);
        gc.setFill(Color.BLACK);
        if (isMiniatureTransition) {
            gc.setFill(Color.rgb(0,0,0,0.3));
        }
        gc.setTextAlign(TextAlignment.LEFT);
        if(isMiniatureTransition){
            gc.setFont(Font.font(8));
        }
        gc.fillText(" -Event:\n "+link.getEvent() + "\n -Context:\n " + link.getContext() + "\n -Side Effects:\n " + link.getSideEffects(),
                (link.transitionNodeX-0.04)*width-xOffset, link.transitionNodeY*height-yOffset);
        gc.strokeRect((link.transitionNodeX-0.05)*width-xOffset, (link.transitionNodeY-0.05)*height-yOffset, 0.1*width, 0.1*height);
    }


    /**
     * Draws self-links (circular link)
     * @param gc                    2D graphics context
     * @param link                  transition link created
     * @param angle                 link angle
     * @param xOffset               view port x offset
     * @param yOffset               view port y offset
     * @param startOffsetX          x coordinate of edge of state node
     * @param startOffsetY          y coordinate of edge of state node
     * @param width                 document width
     * @param height                document height
     *
     */
    private void drawSelfLink(GraphicsContext gc, SMTransitionLink link, double angle, double startOffsetX, double startOffsetY, double width, double height, double xOffset, double yOffset){
        Pair<Double, Double> transitionNodeEdgeCoords = getNodeEdgeCoords(angle, 0.1, 0.1);

        double dx = (link.transitionNodeX-transitionNodeEdgeCoords.getKey()) - (link.x + startOffsetX);
        double dy = (link.transitionNodeY-transitionNodeEdgeCoords.getValue()) - (link.y + startOffsetY);
        double length = Math.sqrt(dx * dx + dy * dy);

        gc.strokeOval(((((link.x + startOffsetX) + ((link.transitionNodeX-transitionNodeEdgeCoords.getKey())))/2)-(length/2))*width-xOffset,
                ((((link.y+ startOffsetY)+(link.transitionNodeY-transitionNodeEdgeCoords.getValue()))/2)- (length/2))*height-yOffset,
                length*width, length*height);
    }


    /**
     * Draws self-links (circular link)
     * @param width                 width of target node
     * @param height                height of target node
     * @param arrowAngle            transition link angle
     * @return coordinates of edge of node based on angle
     *
     */
    private Pair<Double, Double> getNodeEdgeCoords(double arrowAngle, double width, double height){
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
        return new Pair<>(x, y);
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
            newController.setViewPortWidth(newVal.doubleValue()/docWidth);
            draw();
        });
        this.heightProperty().addListener((observable, oldVal, newVal) -> {
            myCanvas.setHeight(newVal.doubleValue());
            newController.setViewPortHeight(newVal.doubleValue()/docHeight);
            draw();
        });
        // event handlers for interaction on canvas
        double width = docWidth;
        double height = docHeight;
        myCanvas.setOnMousePressed(e -> {
            double xOffset = iModel.viewPort.x * width;
            double yOffset = iModel.viewPort.y * width;
            newController.handlePressed((e.getX()+xOffset)/width, (e.getY()+yOffset)/height, e);
        });
        myCanvas.setOnMouseReleased(e -> {
            double xOffset = iModel.viewPort.x * width;
            double yOffset = iModel.viewPort.y * width;
            newController.handleReleased((e.getX()+xOffset)/width, (e.getY()+yOffset)/height, e);
        });
        myCanvas.setOnMouseDragged(e -> {
            double xOffset = iModel.viewPort.x * width;
            double yOffset = iModel.viewPort.y * width;
            newController.handleDragged((e.getX()+xOffset)/width, (e.getY()+yOffset)/height, e);
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