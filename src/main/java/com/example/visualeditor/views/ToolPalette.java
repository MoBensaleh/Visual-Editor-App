package com.example.visualeditor.views;

import com.example.visualeditor.controller.AppController;
import com.example.visualeditor.models.IModelSubscriber;
import com.example.visualeditor.models.InteractionModel;
import com.example.visualeditor.models.SMModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A view that contains buttons for showing
 * and selecting a cursor
 */
public class ToolPalette extends StackPane implements IModelSubscriber {
    private ArrayList<ToggleButton> buttons;
    private ToggleGroup toggles;
    private InteractionModel iModel;
    private SMModel model;

    /**
     * Constructor for ToolPalette
     */
    public ToolPalette() {
        toggles = new ToggleGroup();

        // create cursor bar of rectangles
        VBox cursorBar = new VBox(6);
        buttons = new ArrayList<>();
        String[] cursorNames = {"pointer", "move", "link"};
        for (String name : cursorNames) {
            // create a button for each cursor
            Image img = new Image(this.getClass().getResourceAsStream("/assets/" + name + ".png"));
            ImageView iconImg = new ImageView(img);
            iconImg.setFitHeight(40);
            iconImg.setFitWidth(40);
            ToggleButton button = new ToggleButton("", iconImg);
            button.setId(name);
            VBox.setMargin(button, new Insets(0,5,0,5));
            button.setToggleGroup(toggles);
            button.setBackground(new Background(new BackgroundFill(Color.rgb(34, 159, 152), new CornerRadii(10), null)));
            button.setMaxWidth(Double.MAX_VALUE);

            cursorBar.setPadding(new Insets(5,0,0,0));
            cursorBar.getChildren().add(button);
            buttons.add(button);
        }
        cursorBar.setAlignment(Pos.TOP_LEFT);
        cursorBar.getChildren().forEach(child -> VBox.setVgrow(child, Priority.ALWAYS));


        // set the first pointer button as selection default
        buttons.get(0).setSelected(true);
        VBox.setMargin(buttons.get(0),new Insets(0,0,0,0));
        buttons.get(0).setPadding(new Insets(9,6,9,6));
        buttons.get(0).getGraphic().setScaleX(1.2);
        buttons.get(0).getGraphic().setScaleY(1.2);



        // make sure there is always a button selected
        toggles.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                oldValue.setSelected(true);
            }
        });

        // make buttons visible
        this.getChildren().addAll(cursorBar);
        this.setPrefSize(70, 800);
    }

    /**
     * Associate a model to the view
     * @param newModel The model that stores all elements of the
     * state machine defined in the editor
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
        // initialize iModel selection
        iModel.setSelectedCursor("pointer");
    }
//
    /**
     * Set a controller for the view
     * @param newController the controller
     */
    public void setController(AppController newController) {
        for (ToggleButton button : buttons) {
            button.selectedProperty().addListener((observable, oldValue, newValue) -> {
                newController.handleSelectedCursor(button.getId());
            });
        }
    }

    /**
     * Update view based on model changes
     */
    public void iModelUpdated() {
        for (ToggleButton b : buttons) {
            VBox.setMargin(b, new Insets(0,5,0,5));
            b.setPadding(new Insets(5,5,5,5));
            b.getGraphic().setScaleX(1);
            b.getGraphic().setScaleY(1);
        }

        VBox.setMargin(((ToggleButton) toggles.getSelectedToggle()), new Insets(0,0,0,0));
        ((ToggleButton) toggles.getSelectedToggle()).setPadding(new Insets(9,6,9,6));
        ((ToggleButton) toggles.getSelectedToggle()).getGraphic().setScaleX(1.2);
        ((ToggleButton) toggles.getSelectedToggle()).getGraphic().setScaleY(1.2);
    }
}


