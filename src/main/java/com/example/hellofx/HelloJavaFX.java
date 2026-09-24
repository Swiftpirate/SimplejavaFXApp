
package com.example.hellofx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        Label message = new Label("Welcome to YANDE SICHULA!");
        Button button = new Button("Start");
        Button resetbutton = new Button("Reset");

        // Action for the Start button
        button.setOnAction(event -> message.setText("Great! You clicked the button."));

        // Action for the Reset button to return to the original text
        resetbutton.setOnAction(event -> message.setText("Welcome to YANDE SICHULA!"));

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Added resetbutton to the layout hierarchy
        layout.getChildren().addAll(message, button, resetbutton);

        Scene scene = new Scene(layout, 500, 300);
        stage.setTitle("202501555");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

