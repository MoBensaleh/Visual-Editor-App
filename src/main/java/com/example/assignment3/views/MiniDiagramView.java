package com.example.assignment3.views;

import com.example.assignment3.models.ModelSubscriber;
import com.example.assignment3.models.SMItem;
import com.example.assignment3.models.SMStateNode;
import com.example.assignment3.models.SMTransitionLink;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class MiniDiagramView extends DiagramView implements ModelSubscriber {
    /**
     * Constructor for MiniDiagramView
     *
     * @param newDocWidth
     * @param newDocHeight
     */
    public MiniDiagramView(double newDocWidth, double newDocHeight) {
        super(newDocWidth, newDocHeight);
        this.myCanvas.setWidth(800);
        this.myCanvas.setHeight(800);
        this.setPrefSize(800, 800);
        this.setMaxSize(800, 800);
        this.setStyle("-fx-background-color: rgba(0,0,0,0);");
        gc.fillRect(0, 0, 800, 800);
        gc.strokeRect(0, 0, 800, 800);

        // re-draw canvas when application is resized
        myCanvas.widthProperty().addListener((observable, oldVal, newVal) -> {
            myCanvas.setWidth(newVal.doubleValue());
            draw();
        });
        myCanvas.heightProperty().addListener((observable, oldVal, newVal) -> {
            myCanvas.setHeight(newVal.doubleValue());
            draw();
        });
    }

    /**
     * Draws the items onto the canvas in immediate-mode graphics
     */
    @Override
    public void draw() {
        double width = myCanvas.getWidth();
        double height = myCanvas.getHeight();
        double xOffset = iModel.viewPort.x * width;
        double yOffset = iModel.viewPort.y * height;
        gc.clearRect(0, 0, width, height);
        gc.setLineWidth(2.0);

        for (SMItem item : model.getItems()) {
//
            if(item instanceof SMStateNode){
                SMStateNode stateNode = (SMStateNode) item;
                gc.setFill(Color.rgb(255,242,5, 0.3));
                gc.setStroke(Color.rgb(0,0,0,0.3));
                if (item.equals(iModel.getSelectedItem())) {
                    gc.setStroke(Color.rgb(227,10,10, 0.3));
                }

                gc.fillRect(((item.x-(item.width/2)) * width - xOffset), ((item.y-(item.height/2)) * height - yOffset),
                        item.width*width, item.height*height);
                gc.strokeRect(((item.x-(item.width/2)) * width - xOffset), ((item.y-(item.height/2)) * height - yOffset),
                        item.width*width, item.height*height);
                gc.setTextAlign(TextAlignment.CENTER);
                gc.setTextBaseline(VPos.CENTER);
                gc.setFill(Color.rgb(0,0,0,0.3));
                gc.fillText(stateNode.getState(), item.x * width - xOffset, item.y * height - yOffset);
            }
            if(item instanceof SMTransitionLink){
                if(((SMTransitionLink) item).getFinal()){
                    //Check if the transition node is selected:
                    gc.setStroke(Color.rgb(0,0,0,0.3));
                    drawTransition(gc, (SMTransitionLink) item, item.equals(iModel.getSelectedItem()), width, height, xOffset, yOffset, true);

                }
                //If it's a temporary link
                else {
                    gc.setStroke(Color.rgb(0,0,0,0.3));
                    gc.strokeLine(((item.x) * width - xOffset), ((item.y) * height - yOffset),
                            item.width*width, item.height*height);
                }
            }
        }
        gc.setStroke(Color.rgb(0,0,0,0));
         gc.setFill(Color.rgb(100, 158, 183, 0.2));
        gc.fillRect(iModel.viewPort.x*width, iModel.viewPort.y*height,
                iModel.viewPort.width*width, iModel.viewPort.height*height);
        gc.strokeRect(iModel.viewPort.x*width, iModel.viewPort.y*height,
                iModel.viewPort.width*width, iModel.viewPort.height*height);
    }

    /**
     * Update view based on model changes
     */
    public void modelChanged() {
        draw();
    }
}
