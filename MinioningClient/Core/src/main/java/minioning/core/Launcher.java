package minioning.core;

/**
 *
 * @author Jakob
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static Launcher launcher = null;

    private final int height = 180;
    private final int width = 250;

    @Override
    public void start(Stage primaryStage) throws Exception {

        LauncherLogic launcher = new LauncherLogic();

        final BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        final DatagramSocket clientSocket = new DatagramSocket();
        final InetAddress IPAddress = InetAddress.getByName("localhost");

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

        Button quitBtn = new Button("Quit");

        // create textfields
        TextField nameField = new TextField();
        nameField.setVisible(false);

        // create labels
        Label nameLabel = new Label("Name: ");
        nameLabel.setVisible(false);

        Label titelLabel = new Label("The Minioning");
        titelLabel.setFont(font1);
        titelLabel.setVisible(true);

        Label feedbackLabel = new Label();

        // createplayer button action
        createPlayerBtn.setOnAction((v) -> {
            createPlayerBtn.setVisible(false);
            backBtn.setVisible(true);
            nameField.setVisible(true);
            nameLabel.setVisible(true);
            createBtn.setVisible(true);
            quitBtn.setVisible(true);
        });

        // quit button action
        quitBtn.setOnAction((v) -> {
            primaryStage.close();
        });

        // back button action
        backBtn.setOnAction((v) -> {
            createPlayerBtn.setVisible(true);
            backBtn.setVisible(false);
            nameField.setVisible(false);
            nameLabel.setVisible(false);
            createBtn.setVisible(false);
            feedbackLabel.setVisible(false);
            quitBtn.setVisible(true);
        });

        // create button action
        createBtn.setOnAction((v) -> {
            String s = nameField.getText();
            String output = launcher.nameCheck(s);
            try {
                launcher.CreatePlayer(output, IPAddress, clientSocket);
                feedbackLabel.setText("Player Created");
            } catch (Exception e) {
                feedbackLabel.setText("Error");
            }
            nameField.clear();
        });

        Label label = new Label(this.getClass().getName());
        pane.setCenter(label);

        HBox hb0 = new HBox(15);
        HBox hb1 = new HBox(15);
        HBox hb2 = new HBox(15);

        VBox vb1 = new VBox();
        VBox vb2 = new VBox();
        VBox vb3 = new VBox();

        vb1.getChildren().addAll();
        vb2.getChildren().addAll(nameLabel, nameField, createBtn, backBtn);
        vb3.getChildren().addAll(feedbackLabel);

        hb0.getChildren().addAll(titelLabel);
        hb1.getChildren().addAll(createPlayerBtn);
        hb2.getChildren().addAll(vb1, vb2, vb3, quitBtn);

        pane.setTop(hb0);
        pane.setCenter(hb1);
        pane.setBottom(hb2);

        root.getChildren().addAll(pane);
        Scene scene = new Scene(root, width, height, Color.GAINSBORO);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(this.getClass().getName());

        primaryStage.show();
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        Application.launch(args);
    }
}
