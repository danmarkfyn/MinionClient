package minioning.core;

/**
 *
 * @author Jakob
 */
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
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
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import minioning.common.services.IProcessingService;

public class Launcher extends Application {

    public static Launcher launcher = null;
    private final int height = 175;
    private final int width = 250;
    private final BooleanProperty serverToken = new SimpleBooleanProperty();

    @Override
    public void start(Stage primaryStage) throws Exception {

        serverToken.setValue(Boolean.TRUE);

        final DatagramSocket clientSocket = getDatagramSocket();
//        final InetAddress IPAddress = InetAddress.getByName("192.168.87.13");
        final InetAddress IPAddress = InetAddress.getByName("localhost");

        LauncherLogic launcher = new LauncherLogic();

        new Thread(launcher).start();
        Group root = new Group();

        //***TAB SETUP***
        //Tap pane
        TabPane tp = new TabPane();

        // Tap 1
        Tab tab1 = new Tab("Login");
        tab1.closableProperty().set(false);

        // Tap 2
        Tab tab2 = new Tab("Create Player");
        tab2.closableProperty().set(false);

        // Tap 3
        Tab tab3 = new Tab("Play Minioning");
        tab2.closableProperty().set(false);

        // Pane
        BorderPane pane = new BorderPane();

        // fonts
        Font font1 = Font.font("Serif", 40);
        Font font2 = Font.font("Time New Roman", 12);

        // Buttons
        Button loginBtn = new Button("Login");

        Button logoutBtn = new Button("Logout");

        Button createAvatarBtn = new Button("Create");
        createAvatarBtn.setFont(font2);

        Button playBtn = new Button("Play");

        // Textfields
        TextField avatarnameField = new TextField();
        TextField usernameField = new TextField();
        TextField passwordField = new TextField();

        // Labels
        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password:  ");
        Label avatarnameLabel = new Label("Avatar Name:");
        Label titelLabel = new Label("The Minioning");
        titelLabel.setFont(font1);

        //Color
        titelLabel.setTextFill(Color.CADETBLUE);

        // ***OnAction***
        // Logout button action
        logoutBtn.setOnAction((v) -> {

            serverToken.setValue(Boolean.TRUE);

        });

        // Login button action
        loginBtn.setOnAction((v) -> {

            String username = launcher.nameCheck(usernameField.getText());

            String password = launcher.nameCheck(passwordField.getText());

            if (username != null && password != null) {
                try {
                    launcher.attemptLogin(username, password, IPAddress, clientSocket);
                    serverToken.setValue(Boolean.FALSE);
                } catch (IOException e) {
                }
            }
            usernameField.clear();
            passwordField.clear();
        });

        // Play button action
        playBtn.setOnAction((v) -> {
//            LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
//            cfg.title = this.getClass().getName();
//            cfg.width = 1024;
//            cfg.height = 576;
//            cfg.useGL30 = false;
//            cfg.resizable = false;
//
//            new LwjglApplication(new Core(), cfg);

            new Thread(new Test()).start();
            
            primaryStage.close();

        });
        
        // create button action
        createAvatarBtn.setOnAction((v) -> {
            String s = avatarnameField.getText();
            String output = launcher.nameCheck(s);

            if (output != null) {
                try {
                    launcher.CreatePlayer(output, IPAddress, clientSocket);
                } catch (Exception e) {
                }
                avatarnameField.clear();
            }
        });

        // setup tab 1 content
        HBox t1_hb1 = new HBox(15);
        HBox t1_hb2 = new HBox(15);

        VBox t1_vb1 = new VBox();

        t1_hb1.getChildren().addAll(usernameLabel, usernameField);
        t1_hb2.getChildren().addAll(passwordLabel, passwordField);

        t1_vb1.getChildren().addAll(t1_hb1, t1_hb2, loginBtn, logoutBtn);

        // binds
        tab2.disableProperty().bind(serverToken);
        tab3.disableProperty().bind(serverToken);
        usernameField.disableProperty().bind(serverToken.not());
        passwordField.disableProperty().bind(serverToken.not());
        loginBtn.disableProperty().bind(serverToken.not());

        logoutBtn.disableProperty().bind(serverToken);

        // setup tab 2 content
        HBox t2_hb1 = new HBox(15);

        VBox t2_vb1 = new VBox();

        t2_hb1.getChildren().addAll(avatarnameLabel, avatarnameField);

        t2_vb1.getChildren().addAll(t2_hb1, createAvatarBtn);

        // setup tab 3 content
        tab1.setContent(t1_vb1);
        tab2.setContent(t2_vb1);
        tab3.setContent(playBtn);
        tp.getTabs().addAll(tab1, tab2, tab3);

        pane.setTop(titelLabel);
        pane.setBottom(tp);

        root.getChildren().addAll(pane);
        Scene scene = new Scene(root, width, height, Color.GAINSBORO);

        primaryStage.setOnCloseRequest((v) -> {
            System.out.println("Closing");
        });

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(this.getClass().getName());

        primaryStage.show();
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        Application.launch(args);
    }
}
