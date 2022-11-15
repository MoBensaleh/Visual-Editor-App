module com.example.sideviewdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.assignment3 to javafx.fxml;
    exports com.example.assignment3.controller;
    opens com.example.assignment3.controller to javafx.fxml;
    exports com.example.assignment3.application;
    opens com.example.assignment3.application to javafx.fxml;
    exports com.example.assignment3.models;
    opens com.example.assignment3.models to javafx.fxml;
    exports com.example.assignment3.views;
    opens com.example.assignment3.views to javafx.fxml;
}