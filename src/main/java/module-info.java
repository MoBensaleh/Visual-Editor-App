module com.example.sideviewdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.visualeditor to javafx.fxml;
    exports com.example.visualeditor.controller;
    opens com.example.visualeditor.controller to javafx.fxml;
    exports com.example.visualeditor.application;
    opens com.example.visualeditor.application to javafx.fxml;
    exports com.example.visualeditor.models;
    opens com.example.visualeditor.models to javafx.fxml;
    exports com.example.visualeditor.views;
    opens com.example.visualeditor.views to javafx.fxml;
}