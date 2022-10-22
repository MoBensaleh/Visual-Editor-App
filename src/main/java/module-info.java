module com.example.sideviewdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.assignment3 to javafx.fxml;
    exports com.example.assignment3.controllers;
    opens com.example.assignment3.controllers to javafx.fxml;
    exports com.example.assignment3.application;
    opens com.example.assignment3.application to javafx.fxml;
    exports com.example.assignment3.models;
    opens com.example.assignment3.models to javafx.fxml;
    exports com.example.assignment3.views;
    opens com.example.assignment3.views to javafx.fxml;
}