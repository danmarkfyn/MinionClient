/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.core;

/**
 *
 * @author Jakob
 */
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static Launcher launcher = null;

    private final int height = 180;
    private final int width = 250;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();

        BorderPane pane = new BorderPane();

        
        // fonts
        Font font1 = Font.font("Serif", 40);
        Font font2 = Font.font("Time New Roman", 10);
        
        // create buttons
        Button createPlayerBtn = new Button("Create Player");
        createPlayerBtn.setVisible(true);

        Button backBtn = new Button("Back");
        backBtn.setVisible(false);

        Button createBtn = new Button("Create");
        createBtn.setVisible(false);

        // create textfields
        TextField nameField = new TextField();
        nameField.setVisible(false);

        // create labels
        Label nameLabel = new Label("Name: ");
        nameLabel.setVisible(false);

        Label titelLabel = new Label("The Minioning");
        titelLabel.setFont(font1);
        titelLabel.setVisible(true);

        createPlayerBtn.setOnAction((v) -> {
            createPlayerBtn.setVisible(false);
            backBtn.setVisible(true);
            nameField.setVisible(true);
            nameLabel.setVisible(true);
            createBtn.setVisible(true);
        });

        backBtn.setOnAction((v) -> {
            createPlayerBtn.setVisible(true);
            backBtn.setVisible(false);
            nameField.setVisible(false);
            nameLabel.setVisible(false);
            createBtn.setVisible(false);
        });

        Button leftbtn = new Button("Previous");

        Label label = new Label(this.getClass().getName());
        pane.setCenter(label);

        HBox hb0 = new HBox(15);
        HBox hb1 = new HBox(15);
        HBox hb2 = new HBox(15);

        VBox vb1 = new VBox();
        VBox vb2 = new VBox();
        VBox vb3 = new VBox();

//        vb3.setPrefWidth(width - 122);
        vb1.getChildren().addAll();
        vb2.getChildren().addAll(nameLabel, nameField, createBtn,backBtn);
        vb3.getChildren().addAll();

        hb0.getChildren().addAll(titelLabel);
        hb1.getChildren().addAll(createPlayerBtn);
        hb2.getChildren().addAll(vb1, vb2, vb3);
        
        pane.setTop(hb0);
        pane.setCenter(hb1);
        pane.setBottom(hb2);

        root.getChildren().addAll(pane);
        Scene scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(this.getClass().getName());

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
