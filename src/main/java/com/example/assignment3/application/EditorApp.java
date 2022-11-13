package com.example.assignment3.application;

import com.example.assignment3.controllers.AppController;
import com.example.assignment3.models.InteractionModel;
import com.example.assignment3.models.SMModel;
import com.example.assignment3.views.MainUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * The main application class.
 */
public class EditorApp extends Application {
    @Override
    public void start(Stage stage) {
        // Model(s)
        SMModel model = new SMModel();
        InteractionModel iModel = new InteractionModel();
        // View(s)
        MainUI mainUI = new MainUI();
        // Controller(s)
        AppController controller = new AppController();

        // Set up MVC
        mainUI.setInteractionModel(iModel);
        mainUI.setModel(model);
        mainUI.setController(controller);

        controller.setInteractionModel(iModel);
        controller.setModel(model);

        iModel.addSub(mainUI);

        // set the stage
        Scene scene = new Scene(mainUI, 1150, 800);
        // add event handler for when delete/backspace key is pressed
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE) {
//                controller.handleDeleteSelected();
            }
        });
        stage.setTitle("Editor Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}