package minioning.core;

/**
 *
 * @author Jakob
 */
import java.io.BufferedReader;
import java.io.IOException;
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
import static minioning.core.LauncherLogic.getDatagramSocket;
import org.openide.util.Exceptions;

public class Launcher extends Application {

    public static Launcher launcher = null;

    private final int height = 300;
    private final int width = 250;

    @Override
    public void start(Stage primaryStage) throws Exception {


//        final BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        final DatagramSocket clientSocket = getDatagramSocket();
        final InetAddress IPAddress = InetAddress.getByName("192.168.87.13");
        
        
        
        LauncherLogic launcher = new LauncherLogic();
        
        new Thread(launcher).start();
        Group root = new Group();
        
        
        BorderPane pane = new BorderPane();

        
        
        
        
        // fonts
        Font font1 = Font.font("Serif", 40);
        Font font2 = Font.font("Time New Roman", 12);

        // create buttons
        Button loginBtn = new Button("Login");

        HBox hb0 = new HBox(15);
        HBox hb1 = new HBox(15);
        HBox hb2 = new HBox(15);

        VBox vb1 = new VBox();
        VBox vb2 = new VBox();
        VBox vb3 = new VBox();
        VBox vb4 = new VBox();

        Button createPlayerBtn = new Button("Create Player");
        createPlayerBtn.setFont(font2);
        createPlayerBtn.setVisible(true);

        Button backBtn = new Button("Back");
        backBtn.setFont(font2);
        backBtn.setVisible(false);

        Button createBtn = new Button("Create");
        createBtn.setFont(font2);
        createBtn.setVisible(false);

        Button quitBtn = new Button("Quit");
        quitBtn.setFont(font2);

        Button helpBtn = new Button("Help");
        quitBtn.setFont(font2);
        helpBtn.setVisible(false);

        Button requestLoginBtn = new Button("Login");
        quitBtn.setFont(font2);
        requestLoginBtn.setVisible(false);

        // create textfields
        TextField nameField = new TextField();
        nameField.setVisible(false);

        TextField usernameField = new TextField();
        usernameField.setVisible(false);

        TextField passwordField = new TextField();
        passwordField.setVisible(false);

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
            loginBtn.setVisible(false);
            usernameField.setVisible(false);
            passwordField.setVisible(false);
            helpBtn.setVisible(true);
                    
        });

        // login button action
        loginBtn.setOnAction((v) -> {
            createPlayerBtn.setVisible(false);
            backBtn.setVisible(true);
            nameField.setVisible(false);
            nameLabel.setVisible(false);
            createBtn.setVisible(false);
            quitBtn.setVisible(true);
            loginBtn.setVisible(false);
            usernameField.setVisible(true);
            passwordField.setVisible(true);
            requestLoginBtn.setVisible(true);

        });

        // quit button action
        quitBtn.setOnAction((v) -> {
            primaryStage.close();
        });

        // help button action
        helpBtn.setOnAction((v) -> {
            launcher.promt("Enter a name. Your name can not contain ;, Ã¦, Ã¸, Ã¥ or spaces", "Help");
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
            loginBtn.setVisible(true);
            usernameField.setVisible(false);
            passwordField.setVisible(false);
            helpBtn.setVisible(false);

        });
        
        
                // back button action
         requestLoginBtn.setOnAction((v) -> {
            try {
                launcher.attemptLogin(usernameField.getText(),passwordField.getText(), IPAddress, clientSocket);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }

        });
        

        // create button action
        createBtn.setOnAction((v) -> {
            String s = nameField.getText();
            String output = launcher.nameCheck(s);
            try {
                launcher.CreatePlayer(output, IPAddress, clientSocket);
                feedbackLabel.setText("Player Created");
                feedbackLabel.setVisible(true);
            } catch (Exception e) {
                feedbackLabel.setText("Error");
                feedbackLabel.setVisible(true);
            }
            nameField.clear();
        });

        Label label = new Label(this.getClass().getName());
        pane.setCenter(label);

        vb1.getChildren().addAll(helpBtn,requestLoginBtn, quitBtn,  backBtn);
        vb2.getChildren().addAll(loginBtn, nameLabel, nameField, createBtn);
        vb3.getChildren().addAll(feedbackLabel);
        vb4.getChildren().addAll(usernameField, passwordField);

        hb0.getChildren().addAll(titelLabel);
        hb1.getChildren().addAll(createPlayerBtn, vb2);
        hb2.getChildren().addAll(vb1, vb3, vb4);

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
