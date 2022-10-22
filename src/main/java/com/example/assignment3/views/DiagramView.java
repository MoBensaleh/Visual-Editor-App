package com.example.assignment3.views;

import com.example.assignment3.models.IModelSubscriber;
import com.example.assignment3.models.InteractionModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public class DiagramView extends StackPane implements IModelSubscriber {
    Canvas myCanvas;
    GraphicsContext gc;
    InteractionModel iModel;

    public DiagramView(double vWidth) {
        myCanvas = new Canvas(vWidth, 500);
        gc = myCanvas.getGraphicsContext2D();
        double[] dashPattern = {50,50};
        gc.setLineDashes(dashPattern);
        gc.setLineWidth(4);
        gc.strokeLine(0,0,500,500);

        getChildren().add(myCanvas);
    }

    public void setIModel (InteractionModel im) {
        iModel = im;
    }

    @Override
    public void iModelUpdated() {
    }
}
