package com.example.assignment3.views;

import com.example.assignment3.controller.AppController;
import com.example.assignment3.models.IModelSubscriber;
import com.example.assignment3.models.InteractionModel;
import com.example.assignment3.models.SMTransitionLink;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LinkPropertiesView extends StackPane implements IModelSubscriber {

    private InteractionModel iModel;
    private Button updateButton;
    private TextField eventText;
    private TextArea contextText;
    private TextArea sideEffectsText;

    /**
     * Constructor for LinkPropertiesView
     */
    public LinkPropertiesView(){
        VBox root = new VBox();

        Label menulabel = new Label("Transition");
        menulabel.setFont(Font.font("default", FontWeight.BOLD, 22));

        HBox labelBox = new HBox(menulabel);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setStyle("-fx-background-color: #aaa9a9");
        labelBox.setPadding(new Insets(15, 0, 15, 0));

        Label eventLabel = new Label("Event:");
        eventLabel.setPadding(new Insets(6, 0, 6 ,0));

        eventText = new TextField();
        eventText.setPromptText("No Event");

        Label contextLabel = new Label("Context:");
        contextLabel.setPadding(new Insets(6, 0, 6 ,0));

        contextText = new TextArea();
        contextText.setPromptText("No Context");
        contextText.setPrefHeight(200);

        Label sideEffectsLabel = new Label("Side Effects:");
        sideEffectsLabel.setPadding(new Insets(6, 0, 6 ,0));

        sideEffectsText = new TextArea();
        sideEffectsText.setPromptText("No Side Effects");
        sideEffectsText.setPrefHeight(200);

        HBox btnContainer = new HBox();
        updateButton = new Button("Update");
        btnContainer.getChildren().add(updateButton);
        btnContainer.setPadding(new Insets(6, 0 ,6, 0));


        root.getChildren().addAll(labelBox, eventLabel, eventText, contextLabel, contextText, sideEffectsLabel, sideEffectsText, btnContainer);

        root.setPadding(new Insets(0, 12, 0, 12));
        this.getChildren().addAll(root);
        this.setPrefSize(250, 800);
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
        updateButton.setOnAction(e->{
            newController.handleUpdateLinkProperties((SMTransitionLink) iModel.getSelectedItem(), eventText.getText(), contextText.getText(), sideEffectsText.getText());
        });
    }

    /**
     * Update view based on iModel changes
     */
    @Override
    public void iModelUpdated() {
        if(iModel.getSelectedItem() != null && iModel.getSelectedItem().isTransition()){
            SMTransitionLink selectedLink = (SMTransitionLink) iModel.getSelectedItem();
            eventText.setText(selectedLink.getEvent());
            contextText.setText(selectedLink.getContext());
            sideEffectsText.setText(selectedLink.getSideEffects());
        }
    }
}
