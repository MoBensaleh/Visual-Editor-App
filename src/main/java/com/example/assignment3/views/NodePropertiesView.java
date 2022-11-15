package com.example.assignment3.views;

import com.example.assignment3.controllers.AppController;
import com.example.assignment3.models.IModelSubscriber;
import com.example.assignment3.models.InteractionModel;
import com.example.assignment3.models.SMStateNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NodePropertiesView extends StackPane implements IModelSubscriber {

    private InteractionModel iModel;
    private TextField stateText;

    /**
     * Constructor for NodePropertiesView
     */
    public NodePropertiesView(){
        VBox root = new VBox();

        Label menulabel = new Label("State");
        menulabel.setFont(Font.font("default", FontWeight.BOLD, 22));

        HBox labelBox = new HBox(menulabel);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setStyle("-fx-background-color: #aaa9a9");
        labelBox.setPadding(new Insets(15, 0, 15, 0));

        Label stateLabel = new Label("State Name:");
        stateLabel.setPadding(new Insets(6, 0, 6 ,0));

        stateText = new TextField();
        stateText.setPromptText("Default");


        root.getChildren().addAll(labelBox, stateLabel, stateText);

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

        // add event handler for enter key is pressed
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER){
                newController.handleUpdateNodeProperties((SMStateNode) iModel.getSelectedItem(), stateText.getText());
            }
        });

    }

    @Override
    public void iModelUpdated() {
        if(iModel.getSelectedItem() != null && !iModel.getSelectedItem().isTransition()){
            SMStateNode selectedNode = (SMStateNode) iModel.getSelectedItem();
            stateText.setText(selectedNode.getState());
        }
    }
}
